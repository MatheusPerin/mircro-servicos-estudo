package matheus.perin.productapi.modules.produto.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import matheus.perin.productapi.modules.category.model.Category;
import matheus.perin.productapi.modules.produto.dto.ProductRequest;
import matheus.perin.productapi.modules.produto.dto.ProductResponse;
import matheus.perin.productapi.modules.supplier.model.Supplier;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "supplierid", referencedColumnName = "id", nullable = false)
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "categoryid", referencedColumnName = "id", nullable = false)
    private Category category;

    @Column(name = "quantityavailable", nullable = false)
    private Integer quantityAvailable;

    @Column(name = "created", nullable = false, updatable = false)
    private LocalDateTime created;

    @PrePersist
    public void prePersist() {
        this.created = LocalDateTime.now();
    }

    public void removeQuantity(Integer quantity) {
        this.quantityAvailable = this.quantityAvailable - quantity;
    }

    public static Product of(ProductRequest request, Category category, Supplier supplier) {
        return Product.builder()
            .id(request.getId())
            .name(request.getName())
            .quantityAvailable(request.getQuantityAvailable())
            .category(category)
            .supplier(supplier)
        .build();
    }

}
