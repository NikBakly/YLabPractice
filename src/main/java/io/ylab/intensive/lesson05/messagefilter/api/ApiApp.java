package io.ylab.intensive.lesson05.messagefilter.api;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ApiApp {
    public static void main(String[] args) throws InterruptedException {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigApi.class);
        applicationContext.start();
        ApiController apiController = applicationContext.getBean(ApiController.class);
        System.out.println(apiController.messageProcessing("Да-да пошел я в:жОПу!"));
        System.out.println(apiController.messageProcessing("ЗАеБ. !ЕбуЧий; дождь!"));
        System.out.println(apiController.messageProcessing("!ЗаЕбИстЫЕ. выходные!"));
        applicationContext.stop();
    }
}
