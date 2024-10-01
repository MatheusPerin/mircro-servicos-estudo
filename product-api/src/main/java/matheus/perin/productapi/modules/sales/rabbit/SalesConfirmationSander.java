package matheus.perin.productapi.modules.sales.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matheus.perin.productapi.modules.sales.dto.SalesConfirmationDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class SalesConfirmationSander {

    private final RabbitTemplate template;

    @Value("${app.rabbitmq.exchange.product}")
    private String productTopic;

    @Value("${app.rabbitmq.routing-key.sales-confirmation}")
    private String salesKey;

    public void sendSalesConfirmationMessage(SalesConfirmationDTO salesConfirmation) {
        try {
            log.info("Sending: {}", new ObjectMapper().writeValueAsString(salesConfirmation));
            template.convertAndSend(productTopic, salesKey, salesConfirmation);
            log.info("Message was send");
        } catch (Exception e) {
            log.error("Error: ", e);
        }
    }

}
