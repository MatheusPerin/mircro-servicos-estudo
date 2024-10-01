package matheus.perin.productapi.modules.supplier.controller;

import lombok.AllArgsConstructor;
import matheus.perin.productapi.config.exception.SuccessResponse;
import matheus.perin.productapi.modules.category.dto.CategoryResponse;
import matheus.perin.productapi.modules.supplier.dto.SupplierRequest;
import matheus.perin.productapi.modules.supplier.dto.SupplierResponse;
import matheus.perin.productapi.modules.supplier.service.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/supplier")
@AllArgsConstructor
public class SupplierController {

    private final SupplierService service;

    @PostMapping
    public ResponseEntity<SupplierResponse> save(@RequestBody SupplierRequest request) {
        return ResponseEntity.ok(service.save(request));
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponse>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByIdResponse(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<List<SupplierResponse>> findByName(@PathVariable String name) {
        return ResponseEntity.ok(service.findByName(name));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> delete(@PathVariable Long id) {
        return ResponseEntity.ok(service.delete(id));
    }

}
