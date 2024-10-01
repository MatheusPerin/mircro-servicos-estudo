package matheus.perin.productapi.modules.category.controller;

import lombok.AllArgsConstructor;
import matheus.perin.productapi.config.exception.SuccessResponse;
import matheus.perin.productapi.modules.category.dto.CategoryRequest;
import matheus.perin.productapi.modules.category.dto.CategoryResponse;
import matheus.perin.productapi.modules.category.service.CategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category")
@AllArgsConstructor
public class CategoryController {

    private final CategoryService service;

    @PostMapping
    public ResponseEntity<CategoryResponse> save(@RequestBody CategoryRequest request) {
        return ResponseEntity.ok(service.save(request));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByIdResponse(id));
    }

    @GetMapping("/description/{description}")
    public ResponseEntity<List<CategoryResponse>> findByDescription(@PathVariable String description) {
        return ResponseEntity.ok(service.findByDescription(description));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

}
