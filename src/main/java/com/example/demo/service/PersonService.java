package com.example.demo.service;

import com.example.demo.model.domain.Person;
import com.example.demo.model.request.PersonRequest;
import com.example.demo.model.response.PersonResponse;
import com.example.demo.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {
    private final PersonRepository personRepository;

    @Autowired
    public PersonService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<PersonResponse> getPersons(String firstName){
        if(firstName != null && !firstName.isBlank()) {
            return getPersonsByFirstName(firstName);
        }
        return fetchPersons();
    }

    private List<PersonResponse> getPersonsByFirstName(String firstName) {
        return convertPersonListToPersonResponseList(personRepository.findAllByFirstName(firstName));
    }

    private List<PersonResponse> fetchPersons(){
        return convertPersonListToPersonResponseList(personRepository.findAll());
    }

    public PersonResponse fetchPersonById(Long id){
        Optional<Person> person = personRepository.findById(id);
        return convertPersonToPersonResponse(person.get());
    }

    public PersonResponse createPerson(PersonRequest request) {
        Person person = convertPersonRequestToPerson(request);
        personRepository.save(person);
        return convertPersonToPersonResponse(person);
    }



    private PersonResponse convertPersonToPersonResponse(Person person){
        return person == null
                ? null
                : new PersonResponse(person.getFirstName(), person.getLastName(), person.getEmail(), person.getPhone());
    }

    private List<PersonResponse> convertPersonListToPersonResponseList(List<Person> personList) {
        if (personList == null) {
            return null;
        }

        List<PersonResponse> responseList = new ArrayList<>();
        for (Person person : personList) {
            responseList.add(convertPersonToPersonResponse(person));
        }
        return responseList;
    }

    private Person convertPersonRequestToPerson(PersonRequest request){
        return request == null
                ? null
                : Person.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phone(request.phone())
                .build();
    }

    public void deleteById(Long id) {
        Optional<Person> optionalPerson = personRepository.findById(id);
        optionalPerson.ifPresent(person -> personRepository.deleteById(id));
    }

    public PersonResponse updateById(Long id, PersonRequest request) {
        Optional<Person> optionalPerson = personRepository.findById(id);

        if (optionalPerson.isEmpty()) {
            return null;
        }

        Person existing = optionalPerson.get();

        Person updatedPerson = new Person(
                existing.getId(),
                Optional.ofNullable(request.firstName()).orElse(existing.getFirstName()),
                Optional.ofNullable(request.lastName()).orElse(existing.getLastName()),
                Optional.ofNullable(request.email()).orElse(existing.getEmail()),
                Optional.ofNullable(request.phone()).orElse(existing.getPhone())
        );

        return convertPersonToPersonResponse(personRepository.save(updatedPerson));
    }

}
