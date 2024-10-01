package matheus.perin.productapi.modules.supplier.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import matheus.perin.productapi.modules.category.model.Category;
import matheus.perin.productapi.modules.supplier.model.Supplier;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SupplierResponse {

    private Long id;
    private String name;

    public static SupplierResponse of(Supplier supplier) {
        return SupplierResponse.builder()
            .id(supplier.getId())
            .name(supplier.getName())
        .build();
    }

}
