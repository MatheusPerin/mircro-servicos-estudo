package matheus.perin.productapi.modules.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import matheus.perin.productapi.modules.sales.enums.SalesStatus;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalesConfirmationDTO {

    private String salesId;
    private SalesStatus status;

}
