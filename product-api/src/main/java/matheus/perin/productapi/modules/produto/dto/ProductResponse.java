package matheus.perin.productapi.modules.produto.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import matheus.perin.productapi.modules.category.dto.CategoryResponse;
import matheus.perin.productapi.modules.category.model.Category;
import matheus.perin.productapi.modules.produto.model.Product;
import matheus.perin.productapi.modules.supplier.dto.SupplierResponse;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long id;
    private String name;
    private Integer quantityAvailable;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime created;

    private SupplierResponse supplier;
    private CategoryResponse ctegory;

    public static ProductResponse of(Product product) {
        return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .created(product.getCreated())
            .quantityAvailable(product.getQuantityAvailable())
            .supplier(SupplierResponse.of(product.getSupplier()))
            .ctegory(CategoryResponse.of(product.getCategory()))
        .build();
    }

}
