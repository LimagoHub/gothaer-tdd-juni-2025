package de.gothaer.persistence;

import java.util.List;
import java.util.Optional;

public interface PersonenRepository {
    void save(Person person);
    void update(Person person);

    void delete(Person person);

    Optional<Person> findById(String id);

    List<Person> findAlL();
}
