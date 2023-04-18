package io.ylab.intensive.lesson05.messagefilter.api;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.messagefilter.RabbitInfo;

public class ProducerApi {
    private final ConnectionFactory connectionFactory;

    public ProducerApi(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void sendMessage(String message) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(RabbitInfo.QUEUE_NAME_INPUT, false, false, false, null);
            channel.queueDeclare(RabbitInfo.QUEUE_NAME_OUTPUT, false, false, false, null);
            channel.exchangeDeclare(RabbitInfo.EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            channel.queueBind(RabbitInfo.QUEUE_NAME_INPUT, RabbitInfo.EXCHANGE_NAME, "key_input");
            channel.basicPublish(RabbitInfo.EXCHANGE_NAME,
                    "key_input",
                    null,
                    message.getBytes());
            System.out.println("Сообщение отправлено успешно на обработку");
        } catch (Exception e) {
            System.out.println("Ошибка при отправке сообщения на обработку");
        }
    }
}
