package com.lhx.springcloud.provider.business.controller;

import com.lhx.springcloud.provider.business.Person;
import com.lhx.springcloud.provider.business.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(value = "person")
public class PerconController {

    @Autowired
    private PersonRepository personRepository;

    @PostMapping(path = "addPerson")
    public void addPerson(Person person) {
        personRepository.save(person);
    }
    @GetMapping(path = "get")
    public Optional<Person> get(Long id) {
        return  personRepository.findById(id);
    }

//    @DeleteMapping(path = "deletePerson")
//    public void deletePerson(Long id) {
//        personRepository.delete(id);
//    }
}
