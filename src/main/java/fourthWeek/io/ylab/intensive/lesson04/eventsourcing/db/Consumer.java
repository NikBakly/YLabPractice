package fourthWeek.io.ylab.intensive.lesson04.eventsourcing.db;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import fourthWeek.io.ylab.intensive.lesson04.eventsourcing.Message;
import fourthWeek.io.ylab.intensive.lesson04.eventsourcing.MessageType;
import fourthWeek.io.ylab.intensive.lesson04.eventsourcing.ValueForRabbitMQ;

import javax.sql.DataSource;

public class Consumer {
    private final ConnectionFactory connectionFactory;
    private final PersonDao personDao;

    public Consumer(ConnectionFactory connectionFactory, DataSource dataSource) {
        this.connectionFactory = connectionFactory;
        this.personDao = new PersonDaoImpl(dataSource);
    }

    public void listeningMessages() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse response = channel.basicGet(ValueForRabbitMQ.QUEUE_NAME, true);
                if (response != null) {
                    String acceptedMessage = new String(response.getBody());
                    Message message = objectMapper.readValue(acceptedMessage, Message.class);
                    if (message.getMessageType().equals(MessageType.DELETE)) {
                        personDao.deletePerson(message.getPerson().getId());
                    } else {
                        personDao.savePerson(
                                message.getPerson().getId(),
                                message.getPerson().getFirstName(),
                                message.getPerson().getLastName(),
                                message.getPerson().getMiddleName()
                        );
                    }
                }
            }
        }
    }
}
