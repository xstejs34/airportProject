package org.but.feec.airport.service;

import org.but.feec.airport.data.PersonRepository;

public class PersonService {
    private PersonRepository personRepository;

    public PersonService(PersonRepository personRepository){
        this.personRepository = personRepository;
    }
}
