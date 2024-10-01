package matheus.perin.productapi.modules.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import matheus.perin.productapi.modules.category.model.Category;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    private Long id;
    private String descriprion;

    public static CategoryResponse of(Category category) {
        return CategoryResponse.builder()
            .id(category.getId())
            .descriprion(category.getDescription())
        .build();
    }

}
