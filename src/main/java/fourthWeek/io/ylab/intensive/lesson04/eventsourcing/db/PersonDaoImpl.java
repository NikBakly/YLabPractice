package fourthWeek.io.ylab.intensive.lesson04.eventsourcing.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonDaoImpl implements PersonDao {
    private final DataSource dataSource;

    public PersonDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public void deletePerson(Long personId) {
        try {
            boolean isPerson = containsPersonById(personId);
            if (isPerson) {
                String deleteByPersonIdSql =
                        "DELETE FROM person " +
                                "WHERE person_id = ?;";
                try (Connection connection = dataSource.getConnection();
                     PreparedStatement preparedStatement = connection.prepareStatement(deleteByPersonIdSql)) {
                    preparedStatement.setLong(1, personId);
                    preparedStatement.executeUpdate();
                    System.out.printf("Объект Person с id=%d успешно обновлен", personId);
                } catch (SQLException e) {
                    System.out.println("Ошибка при удалении объекта Person");
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.printf("Person с id=%d не найден\n", personId);
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void savePerson(Long personId, String firstName, String lastName, String middleName) {
        try {
            boolean isPerson = containsPersonById(personId);
            String sql;
            if (isPerson) {
                sql = "UPDATE person SET first_name = ?, last_name = ?, middle_name = ? " +
                        " WHERE person_id = ?;";
            } else {
                sql = "INSERT INTO person (person_id, first_name, last_name, middle_name) VALUES (?, ?, ?, ?);";
            }
            try (Connection connection = dataSource.getConnection();
                 PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
                if (isPerson) {
                    preparedStatement.setString(1, firstName);
                    preparedStatement.setString(2, lastName);
                    preparedStatement.setString(3, middleName);
                    preparedStatement.setLong(4, personId);
                    System.out.printf("Объект Person с id=%d успешно обновлен \n", personId);
                } else {
                    preparedStatement.setLong(1, personId);
                    preparedStatement.setString(2, firstName);
                    preparedStatement.setString(3, lastName);
                    preparedStatement.setString(4, middleName);
                    System.out.printf("Объект Person с id=%d успешно добавлен \n", personId);
                }
                preparedStatement.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Ошибка во время сохранения объекта Person");
            }
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }

    private boolean containsPersonById(Long personId) throws RuntimeException {
        if (personId == null) {
            throw new RuntimeException("PersonId не может быть null");
        }
        boolean result = true;
        String selectSql =
                "SELECT * " +
                        "FROM person " +
                        "WHERE person_id = ?";
        try (Connection connection = dataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(selectSql)) {
            preparedStatement.setLong(1, personId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (!resultSet.next()) {
                    result = false;
                }
            }
        } catch (SQLException e) {
            System.out.println("Ошибка при проверки существования человека");
            System.out.println(e.getMessage());
        }
        return result;
    }

}
