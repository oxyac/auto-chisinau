package oxyac.shopping.data.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import oxyac.shopping.data.entity.Person;

public interface PersonRepository extends JpaRepository<Person, String> {
    Person findByUsername(String username);
}