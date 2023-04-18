package io.ylab.intensive.lesson05.messagefilter.api;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("io.ylab.intensive.lesson05.messagefilter.api")
public class ConfigApi {
    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("localhost");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");
        return connectionFactory;
    }

    @Bean
    public ConsumerApi consumerApi() {
        return new ConsumerApi(connectionFactory());
    }

    @Bean
    public ProducerApi producerApi() {
        return new ProducerApi(connectionFactory());
    }

    @Bean
    public ApiController apiController() {
        return new ApiController(consumerApi(), producerApi());
    }
}
