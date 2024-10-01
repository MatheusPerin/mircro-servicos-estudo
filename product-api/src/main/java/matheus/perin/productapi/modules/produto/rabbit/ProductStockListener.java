package matheus.perin.productapi.modules.produto.rabbit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import matheus.perin.productapi.modules.produto.dto.ProductStockDTO;
import matheus.perin.productapi.modules.produto.service.ProductService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class ProductStockListener {

    private final ProductService service;

    @RabbitListener(queues = "${app.rabbitmq.queue.product-stock}")
    public void reciveProductStockMessage(ProductStockDTO productStock) throws JsonProcessingException {
        log.info("Recebido: {}", new ObjectMapper().writeValueAsString(productStock));
        service.updateProductStock(productStock);
    }

}
