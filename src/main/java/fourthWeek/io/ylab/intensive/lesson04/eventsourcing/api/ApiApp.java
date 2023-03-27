package fourthWeek.io.ylab.intensive.lesson04.eventsourcing.api;

import com.rabbitmq.client.ConnectionFactory;
import fourthWeek.io.ylab.intensive.lesson04.DbUtil;
import fourthWeek.io.ylab.intensive.lesson04.RabbitMQUtil;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        ConnectionFactory connectionFactory = initMQ();
        DataSource dataSource = initDb();
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        PersonApi personApi = new PersonApiImpl(connectionFactory, dataSource);
        personApi.savePerson(1L, "Petya", "Gavhosh", "Cod");
        personApi.savePerson(2L, "Cat", "May", "Mur");
        personApi.savePerson(3L, "Anya", "Hishek", "Bonchok");
        personApi.savePerson(1L, "Petya", "Gamakov", "Cod");
        personApi.deletePerson(5L);
        TimeUnit.SECONDS.sleep(3);
        System.out.println(personApi.findPerson(8L));
        System.out.println(personApi.findPerson(1L));
        System.out.println(personApi.findPerson(3L));
        personApi.deletePerson(3L);
        TimeUnit.SECONDS.sleep(3);
        System.out.println(personApi.findPerson(3L));
        System.out.println(personApi.findAll());
        personApi.deletePerson(1L);
        TimeUnit.SECONDS.sleep(3);
        System.out.println(personApi.findAll());
    }

    private static ConnectionFactory initMQ() throws Exception {
        return RabbitMQUtil.buildConnectionFactory();
    }

    private static DataSource initDb() throws SQLException {
        String ddl = ""
                + "drop table if exists person;"
                + "create table if not exists person (\n"
                + "person_id bigint primary key,\n"
                + "first_name varchar,\n"
                + "last_name varchar,\n"
                + "middle_name varchar\n"
                + ")";
        DataSource dataSource = DbUtil.buildDataSource();
        DbUtil.applyDdl(ddl, dataSource);
        return dataSource;
    }

}
