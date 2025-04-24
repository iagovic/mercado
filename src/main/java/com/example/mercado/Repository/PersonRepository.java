package com.example.mercado.Repository;

import com.example.mercado.Model.Person;
import com.example.mercado.Model.PersonGender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByNameContainingIgnoreCase(String name);
    List<Person> findByGender(PersonGender gender);
    List<Person> findByNivel(Integer nivel);
    List<Person> findByMoedasGreaterThanEqual(Double moedas);
}
