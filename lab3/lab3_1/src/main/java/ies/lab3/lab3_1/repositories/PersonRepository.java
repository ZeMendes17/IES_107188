package ies.lab3.lab3_1.repositories;

import ies.lab3.lab3_1.entities.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {
    List<Person> findByName(String name);
}
