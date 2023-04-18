package io.ylab.intensive.lesson05.messagefilter.api;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.messagefilter.RabbitInfo;

public class ConsumerApi {
    private final ConnectionFactory connectionFactory;

    public ConsumerApi(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public String getProcessedMessage() {
        String result = null;
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            GetResponse response = channel.basicGet(RabbitInfo.QUEUE_NAME_OUTPUT, true);
            while (response == null) {
                response = channel.basicGet(RabbitInfo.QUEUE_NAME_OUTPUT, true);
            }
            result = new String(response.getBody());
        } catch (Exception e) {
            System.out.print("Ошибка при получении обработанного сообщения:");
            System.out.println(e.getMessage());
        }
        return result;
    }

}
