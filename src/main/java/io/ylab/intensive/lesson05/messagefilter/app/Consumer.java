package io.ylab.intensive.lesson05.messagefilter.app;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import io.ylab.intensive.lesson05.messagefilter.RabbitInfo;
import io.ylab.intensive.lesson05.messagefilter.app.manage.MessageService;

public class Consumer {
    private final ConnectionFactory connectionFactory;
    private final MessageService messageService;

    public Consumer(ConnectionFactory connectionFactory, MessageService messageService) {
        this.connectionFactory = connectionFactory;
        this.messageService = messageService;
    }

    public void listenMessage() {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.queueDeclare(RabbitInfo.QUEUE_NAME_INPUT, false, false, false, null);
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse response = channel.basicGet(RabbitInfo.QUEUE_NAME_INPUT, true);
                if (response != null) {
                    messageService.processMessage(new String(response.getBody()));
                }
            }
        } catch (Exception e) {
            System.out.print("Ошибка при чтение не обработанного сообщения:");
            System.out.println(e.getMessage());
        }
    }
}
