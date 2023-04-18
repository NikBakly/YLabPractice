package io.ylab.intensive.lesson04.eventsourcing.db;

public interface PersonDao {
    void deletePerson(Long personId);

    void savePerson(Long personId, String firstName, String lastName, String middleName);

}
