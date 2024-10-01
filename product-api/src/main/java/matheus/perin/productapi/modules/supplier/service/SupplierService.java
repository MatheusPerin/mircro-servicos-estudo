package matheus.perin.productapi.modules.supplier.service;

import lombok.AllArgsConstructor;
import matheus.perin.productapi.config.exception.SuccessResponse;
import matheus.perin.productapi.config.exception.ValidationException;
import matheus.perin.productapi.modules.produto.service.ProductService;
import matheus.perin.productapi.modules.supplier.dto.SupplierRequest;
import matheus.perin.productapi.modules.supplier.dto.SupplierResponse;
import matheus.perin.productapi.modules.supplier.model.Supplier;
import matheus.perin.productapi.modules.supplier.repository.SupplierRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class SupplierService {

    private final SupplierRepository repository;
    private final ProductService productService;

    public SupplierService(SupplierRepository repository, @Lazy ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    public SupplierResponse save(SupplierRequest request) {
        validate(request);

        return SupplierResponse.of(repository.save(Supplier.of(request)));
    }

    private void validate(SupplierRequest request) {
        validateNameInformed(request.getName());
    }

    private void validateNameInformed(String name) {
        if (ObjectUtils.isEmpty(name))
            throw new ValidationException("Name must be provided");
    }

    private void validateIdInformed(Long id) {
        if (ObjectUtils.isEmpty(id))
            throw new ValidationException("Id must be provided");
    }

    public Supplier findById(Long id) {
        return repository
            .findById(id)
            .orElseThrow(() -> new ValidationException("Supplier not found"));
    }

    public List<SupplierResponse> findAll() {
        return repository
            .findAll()
            .stream()
            .map(SupplierResponse::of)
            .toList();
    }

    public SupplierResponse findByIdResponse(Long id) {
        return repository
            .findById(id)
            .map(SupplierResponse::of)
            .orElseThrow(() -> new ValidationException("Supplier not found"));
    }

    public List<SupplierResponse> findByName(String name) {
        validateNameInformed(name);

        return repository
            .findByNameIgnoreCaseContaining(name)
            .stream()
            .map(SupplierResponse::of)
            .toList();
    }

    public SuccessResponse delete(Long id) {
        validateIdInformed(id);

        if (productService.existsBySupplierId(id))
            throw new ValidationException("It is not possible to delete, as there is a linked product");

        repository.deleteById(id);

        return SuccessResponse.create("Was supplier as deleted");
    }
}
