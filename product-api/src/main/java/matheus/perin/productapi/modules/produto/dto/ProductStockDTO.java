package matheus.perin.productapi.modules.produto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductStockDTO {

    private String salesId;
    private List<ProductQuantityDTO> products;

}
