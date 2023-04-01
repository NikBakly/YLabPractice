package fifthWeek.io.ylab.intensive.lesson05.eventsourcing.api;


import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.concurrent.TimeUnit;

public class ApiApp {
    public static void main(String[] args) throws Exception {
        // Тут пишем создание PersonApi, запуск и демонстрацию работы
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(Config.class);
        applicationContext.start();
        PersonApi personApi = applicationContext.getBean(PersonApi.class);
        // пишем взаимодействие с PersonApi
        personApi.savePerson(1L, "Petya", "Gavhosh", "Cod");
        personApi.savePerson(2L, "Cat", "May", "Mur");
        personApi.savePerson(null, "Z3yQ", "Q5Pzpw42", "gTg");
        personApi.savePerson(3L, "Anya", "Hishek", "Bonchok");
        personApi.savePerson(1L, "Petya", "Gamakov", "Cod");
        TimeUnit.SECONDS.sleep(3);
        System.out.println(personApi.findAll());
        personApi.deletePerson(5L);
        personApi.deletePerson(null);
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

        applicationContext.stop();
    }
}
