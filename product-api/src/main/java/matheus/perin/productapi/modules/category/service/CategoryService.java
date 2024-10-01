package matheus.perin.productapi.modules.category.service;

import lombok.AllArgsConstructor;
import matheus.perin.productapi.config.exception.SuccessResponse;
import matheus.perin.productapi.config.exception.ValidationException;
import matheus.perin.productapi.modules.category.dto.CategoryRequest;
import matheus.perin.productapi.modules.category.dto.CategoryResponse;
import matheus.perin.productapi.modules.category.model.Category;
import matheus.perin.productapi.modules.category.repository.CategoryRepository;
import matheus.perin.productapi.modules.produto.service.ProductService;
import matheus.perin.productapi.modules.supplier.dto.SupplierResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class CategoryService {

    private final CategoryRepository repository;
    private final ProductService productService;

    @Autowired
    public CategoryService(CategoryRepository repository, @Lazy ProductService productService) {
        this.repository = repository;
        this.productService = productService;
    }

    public CategoryResponse save(CategoryRequest request) {
        validate(request);

        return CategoryResponse.of(repository.save(Category.of(request)));
    }

    private void validate(CategoryRequest request) {
        validateDescriptionInformed(request.getDescription());
    }

    private void validateDescriptionInformed(String description) {
        if (ObjectUtils.isEmpty(description))
            throw new ValidationException("Description must be entered");
    }

    private void validateIdInformed(Long id) {
        if (ObjectUtils.isEmpty(id))
            throw new ValidationException("Id must be provided");
    }

    public Category findById(Long id) {
        return repository
            .findById(id)
            .orElseThrow(() -> new ValidationException("Category not found"));
    }

    public List<CategoryResponse> findByDescription(String description) {
        validateDescriptionInformed(description);

        return repository
            .findByDescriptionIgnoreCaseContaining(description)
            .stream()
            .map(CategoryResponse::of)
            .toList();
    }

    public List<CategoryResponse> findAll() {
        return repository
            .findAll()
            .stream()
            .map(CategoryResponse::of)
            .toList();
    }

    public CategoryResponse findByIdResponse(Long id) {
        return repository
            .findById(id)
            .map(CategoryResponse::of)
            .orElseThrow(() -> new ValidationException("Category not found"));
    }

    public SuccessResponse delete(Long id) {
        validateIdInformed(id);

        if (productService.existsByCategoryId(id))
            throw new ValidationException("It is not possible to delete, as there is a linked product");

        repository.deleteById(id);

        return SuccessResponse.create("Was category as deleted");
    }

}
