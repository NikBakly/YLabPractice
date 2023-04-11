package fifthWeek.io.ylab.intensive.lesson05.messagefilter.app;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MessageFilterApp {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();

        MessageController messageController = applicationContext.getBean(MessageController.class);
        messageController.startProcessing();

        applicationContext.stop();
    }
}
