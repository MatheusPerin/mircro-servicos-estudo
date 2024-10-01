package matheus.perin.productapi.modules.category.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import matheus.perin.productapi.modules.category.dto.CategoryRequest;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "category")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "description", nullable = false)
    private String description;

    public static Category of(CategoryRequest request) {
        return Category.builder()
            .id(request.getId())
            .description(request.getDescription())
        .build();
    }

}
