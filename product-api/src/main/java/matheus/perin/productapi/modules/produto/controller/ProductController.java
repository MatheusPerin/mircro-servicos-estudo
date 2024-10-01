package matheus.perin.productapi.modules.produto.controller;

import lombok.AllArgsConstructor;
import matheus.perin.productapi.config.exception.SuccessResponse;
import matheus.perin.productapi.modules.produto.dto.ProductRequest;
import matheus.perin.productapi.modules.produto.dto.ProductResponse;
import matheus.perin.productapi.modules.produto.service.ProductService;
import matheus.perin.productapi.modules.supplier.dto.SupplierResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<ProductResponse> save(@RequestBody ProductRequest request) {
        return ResponseEntity.ok(service.save(request));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByIdResponse(id));
    }

    @GetMapping("/supplier/id/{id}")
    public ResponseEntity<List<ProductResponse>> findBySupplierId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findBySupplierId(id));
    }

    @GetMapping("/category/id/{id}")
    public ResponseEntity<List<ProductResponse>> findByCategoryId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByCategoryId(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<ProductResponse>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

}
