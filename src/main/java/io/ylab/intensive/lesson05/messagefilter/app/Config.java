package io.ylab.intensive.lesson05.messagefilter.app;

import com.rabbitmq.client.ConnectionFactory;
import io.ylab.intensive.lesson05.messagefilter.app.manage.MessageDao;
import io.ylab.intensive.lesson05.messagefilter.app.manage.MessageDaoImpl;
import io.ylab.intensive.lesson05.messagefilter.app.manage.MessageService;
import io.ylab.intensive.lesson05.messagefilter.app.manage.MessageServiceImpl;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan("io.ylab.intensive.lesson05.messagefilter.app")
public class Config {
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
    public DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setServerName("localhost");
        dataSource.setUser("postgres");
        dataSource.setPassword("postgres");
        dataSource.setDatabaseName("postgres");
        dataSource.setPortNumber(5432);
        return dataSource;
    }

    @Bean
    public MessageDao messageDao() {
        return new MessageDaoImpl(dataSource());
    }

    @Bean
    public Producer producer() {
        return new Producer(connectionFactory());
    }

    @Bean
    public MessageService messageService() {
        return new MessageServiceImpl(messageDao(), producer());
    }

    @Bean
    public Consumer consumer() {
        return new Consumer(connectionFactory(), messageService());
    }

    @Bean
    public MessageController messageController() {
        return new MessageController(consumer());
    }
}
