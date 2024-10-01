package matheus.perin.productapi.modules.produto.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matheus.perin.productapi.config.exception.SuccessResponse;
import matheus.perin.productapi.config.exception.ValidationException;
import matheus.perin.productapi.modules.category.model.Category;
import matheus.perin.productapi.modules.category.service.CategoryService;
import matheus.perin.productapi.modules.produto.dto.ProductRequest;
import matheus.perin.productapi.modules.produto.dto.ProductResponse;
import matheus.perin.productapi.modules.produto.dto.ProductStockDTO;
import matheus.perin.productapi.modules.produto.model.Product;
import matheus.perin.productapi.modules.produto.repository.ProductRepository;
import matheus.perin.productapi.modules.sales.dto.SalesConfirmationDTO;
import matheus.perin.productapi.modules.sales.enums.SalesStatus;
import matheus.perin.productapi.modules.sales.rabbit.SalesConfirmationSander;
import matheus.perin.productapi.modules.supplier.dto.SupplierResponse;
import matheus.perin.productapi.modules.supplier.model.Supplier;
import matheus.perin.productapi.modules.supplier.service.SupplierService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository repository;
    private final SupplierService supplierService;
    private final CategoryService categoryService;
    private final SalesConfirmationSander salesConfirmationSander;

    public ProductResponse save(ProductRequest request) {
        validate(request);

        final Supplier supplier = supplierService.findById(request.getSupplierId());
        final Category category = categoryService.findById(request.getCategoryId());

        return ProductResponse.of(repository.save(Product.of(request, category, supplier)));
    }

    private void validate(ProductRequest request) {
        if (ObjectUtils.isEmpty(request.getName()))
            throw new ValidationException("Name must be provided");

        if (ObjectUtils.isEmpty(request.getQuantityAvailable()))
            throw new ValidationException("Quantity must by provided");

        if (ObjectUtils.isEmpty(request.getCategoryId()))
            throw new ValidationException("Category id must be provided");

        if (ObjectUtils.isEmpty(request.getQuantityAvailable()))
            throw new ValidationException("Supplier id must by provided");

        if (request.getQuantityAvailable() <= 0)
            throw new ValidationException("Quantity must be greater than 0");
    }

    private void validateIdInformed(Long id) {
        if (ObjectUtils.isEmpty(id))
            throw new ValidationException("Id must be provided");
    }

    public List<ProductResponse> findByCategoryId(Long id) {
        if (ObjectUtils.isEmpty(id))
            throw new ValidationException("Category id must be provided");

        return repository
            .findByCategoryId(id)
            .stream()
            .map(ProductResponse::of)
            .toList();
    }

    public List<ProductResponse> findBySupplierId(Long id) {
        if (ObjectUtils.isEmpty(id))
            throw new ValidationException("Supplier id must be provided");

        return repository
            .findBySupplierId(id)
            .stream()
            .map(ProductResponse::of)
            .toList();
    }

    public ProductResponse findByIdResponse(Long id) {
        return repository
            .findById(id)
            .map(ProductResponse::of)
            .orElseThrow(() -> new ValidationException("Product not found"));
    }

    public List<ProductResponse> findAll() {
        return repository
            .findAll()
            .stream()
            .map(ProductResponse::of)
            .toList();
    }

    public List<ProductResponse> findByName(String name) {
        if (ObjectUtils.isEmpty(name))
            throw new ValidationException("Name must be provided");

        return repository
            .findByNameIgnoreCaseContaining(name)
            .stream()
            .map(ProductResponse::of)
            .toList();
    }

    public Boolean existsByCategoryId(Long categoryId) {
        return repository.existsByCategoryId(categoryId);
    }

    public Boolean existsBySupplierId(Long supplierId) {
        return repository.existsBySupplierId(supplierId);
    }

    public SuccessResponse delete(Long id) {
        validateIdInformed(id);

        repository.deleteById(id);

        return SuccessResponse.create("The product was deleted");
    }

    @Transactional
    public void updateProductStock(ProductStockDTO productStock) {
        try {
            validateUpdateProductStock(productStock);

            productStock
                .getProducts()
                .forEach(pd -> {
                    Product product = findById(pd.getProductId());

                    if (pd.getQuantity() > product.getQuantityAvailable())
                        throw new ValidationException(String.format("There is not enough quantity in stock for product %d", pd.getProductId()));

                    product.removeQuantity(pd.getQuantity());

                    repository.save(product);
                });

            salesConfirmationSander.sendSalesConfirmationMessage(new SalesConfirmationDTO(productStock.getSalesId(), SalesStatus.APPROVED));
        } catch (Exception e) {
            log.error("Error while tring update stock: {}", e.getMessage(), e);
            salesConfirmationSander.sendSalesConfirmationMessage(new SalesConfirmationDTO(productStock.getSalesId(), SalesStatus.REJECTED));
        }
    }

    private Product findById(Long id) {
        return repository
            .findById(id)
            .orElseThrow(() -> new ValidationException("Product not found"));
    }

    private void validateUpdateProductStock(ProductStockDTO productStock) {
        if (ObjectUtils.isEmpty(productStock) || ObjectUtils.isEmpty(productStock.getSalesId()))
            throw new ValidationException("The product data or sales id must be provided");

        if (ObjectUtils.isEmpty(productStock.getProducts()))
            throw new ValidationException("The sales products must be provided");

        productStock
            .getProducts()
            .forEach(product -> {
                if (ObjectUtils.isEmpty(product.getProductId()) || ObjectUtils.isEmpty(product.getQuantity()))
                    throw new ValidationException("Product id and quantity must be provided");
            });
    }

}
