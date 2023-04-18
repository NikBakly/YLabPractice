package io.ylab.intensive.lesson04.eventsourcing.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson04.eventsourcing.Message;
import io.ylab.intensive.lesson04.eventsourcing.MessageType;
import io.ylab.intensive.lesson04.eventsourcing.Person;
import io.ylab.intensive.lesson04.eventsourcing.ValueForRabbitMQ;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Тут пишем реализацию
 */
public class PersonApiImpl implements PersonApi {
    private final ConnectionFactory connectionFactory;
    private final DataSource dataSource;

    public PersonApiImpl(ConnectionFactory connectionFactory, DataSource dataSource) {
        this.connectionFactory = connectionFactory;
        this.dataSource = dataSource;
    }

    @Override
    public void deletePerson(Long personId) {
        Message message = new Message(MessageType.DELETE, new Person(personId));
        sendMessage(message);
    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        Message message = new Message(
                MessageType.SAVE,
                new Person(personId, firstName, lastName, middleName));
        sendMessage(message);
    }

    private void sendMessage(Message message) {
        ObjectMapper objectMapper = new ObjectMapper();
        try (Connection connection = connectionFactory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(ValueForRabbitMQ.EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
            channel.queueDeclare(ValueForRabbitMQ.QUEUE_NAME, false, false, false, null);
            channel.queueBind(ValueForRabbitMQ.QUEUE_NAME, ValueForRabbitMQ.EXCHANGE_NAME, "*");
            channel.basicPublish(ValueForRabbitMQ.EXCHANGE_NAME,
                    "*",
                    false,
                    null,
                    objectMapper.writeValueAsBytes(message));
            System.out.println("Сообщение отправлено успешно");
        } catch (Exception e) {
            System.out.println("Ошибка при отправке сообщения");
        }
    }

    @Override
    public Person findPerson(Long personId) {
        Person foundPerson = null;
        String findPersonByIdSql =
                "SELECT * " +
                        "FROM person " +
                        "WHERE person_id = ?";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findPersonByIdSql)) {
            preparedStatement.setLong(1, personId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    foundPerson = new Person(
                            resultSet.getLong("person_id"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("middle_name")
                    );
                }
                System.out.printf("Объект Person с id=%d успешно найден \n", personId);
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при поиске объекта Person");
        }
        return foundPerson;
    }

    @Override
    public List<Person> findAll() {
        List<Person> result = new ArrayList<>();
        String findPersonByIdSql =
                "SELECT * " +
                        "FROM person;";
        try (java.sql.Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(findPersonByIdSql);
             ResultSet resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next()) {
                Person foundPerson = new Person(
                        resultSet.getLong("person_id"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name"),
                        resultSet.getString("middle_name")
                );
                result.add(foundPerson);
            }
            System.out.println("Все объект Person найдены");
        } catch (SQLException e) {
            System.out.println("Ошибка при поиске всех объектов Person");
        }
        return result;
    }
}
