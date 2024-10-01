package matheus.perin.productapi.config.rabbit;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Value("${app.rabbitmq.exchange.product}")
    private String productTopic;

    @Value("${app.rabbitmq.routing-key.product-stock}")
    private String stockKey;

    @Value("${app.rabbitmq.routing-key.sales-confirmation}")
    private String salesKey;

    @Value("${app.rabbitmq.queue.product-stock}")
    private String stockQueue;

    @Value("${app.rabbitmq.queue.sales-confirmation}")
    private String salesConfirmationQueue;

    @Bean
    public TopicExchange productTopicExchange() {
        return new TopicExchange(productTopic);
    }

    @Bean
    public Queue stockQueue() {
        return new Queue(stockQueue, true);
    }

    @Bean
    public Queue salesConfirmationQueue() {
        return new Queue(salesConfirmationQueue, true);
    }

    @Bean
    public Binding stockBiding(TopicExchange topicExchange) {
        return BindingBuilder
            .bind(stockQueue())
            .to(topicExchange)
            .with(stockKey);
    }

    @Bean
    public Binding salesBiding(TopicExchange topicExchange) {
        return BindingBuilder
            .bind(salesConfirmationQueue())
            .to(topicExchange)
            .with(salesKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}
