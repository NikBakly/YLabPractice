package fifthWeek.io.ylab.intensive.lesson05.messagefilter.app;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import fifthWeek.io.ylab.intensive.lesson05.messagefilter.RabbitInfo;

public class Producer {
    private final ConnectionFactory connectionFactory;

    public Producer(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void sendProcessedMessage(String processedMessage) {
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(RabbitInfo.EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
            channel.queueDeclare(RabbitInfo.QUEUE_NAME_OUTPUT, false, false, false, null);
            channel.queueBind(RabbitInfo.QUEUE_NAME_OUTPUT, RabbitInfo.EXCHANGE_NAME, "key_output");
            channel.basicPublish(RabbitInfo.EXCHANGE_NAME,
                    "key_output",
                    null,
                    processedMessage.getBytes());
            System.out.println("Обработанное сообщение отправлено успешно");
        } catch (Exception e) {
            System.out.print("Ошибка при отправке обработанного сообщения:");
            System.out.println(e.getMessage());
        }
    }
}
