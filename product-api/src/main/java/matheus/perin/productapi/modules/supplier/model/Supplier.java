package matheus.perin.productapi.modules.supplier.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import matheus.perin.productapi.modules.supplier.dto.SupplierRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public static Supplier of(SupplierRequest request) {
        return Supplier.builder()
            .id(request.getId())
            .name(request.getName())
        .build();
    }

}
