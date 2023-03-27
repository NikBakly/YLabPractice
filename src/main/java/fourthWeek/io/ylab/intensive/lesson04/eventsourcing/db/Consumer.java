package fourthWeek.io.ylab.intensive.lesson04.eventsourcing.db;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
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
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            while (!Thread.currentThread().isInterrupted()) {
                GetResponse message = channel.basicGet(ValueForRabbitMQ.QUEUE_NAME, true);
                if (message != null) {
                    String acceptedMessage = new String(message.getBody());
                    String[] values = acceptedMessage.split(";");
                    if (values.length == 1) {
                        personDao.deletePerson(Long.valueOf(values[0]));
                    } else {
                        personDao.savePerson(
                                Long.valueOf(values[0]),
                                values[1],
                                values[2],
                                values[3]
                        );
                    }
                }
            }
        }
    }
}
