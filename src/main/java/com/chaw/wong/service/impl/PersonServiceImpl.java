package com.chaw.wong.service.impl;

import com.chaw.wong.repository.PersonRepository;
import com.chaw.wong.entity.Person;
import com.chaw.wong.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by XRom
 * On 11/16/2017.11:58 PM
 */
@Service
public class PersonServiceImpl implements PersonService {

    @Autowired(required = true)
    private PersonRepository personRepository;

    @Override
    public Long savePerson() {
        Person person = new Person();
        person.setUsername("XRog");
        person.setPhone("18381005946");
        person.setAddress("chenDu");
        person.setRemark("this is XRog");
        return personRepository.save(person);
    }
}
