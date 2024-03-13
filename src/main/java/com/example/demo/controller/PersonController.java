package com.example.demo.controller;

import com.example.demo.model.request.PersonRequest;
import com.example.demo.model.response.PersonResponse;
import com.example.demo.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/persons")
@Tag(name = "Person Controller", description = "To work with person data")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @GetMapping
    @Operation(summary = "to fetch person data")
    public List<PersonResponse> getPersons(@RequestParam(required = false) String firstName) {
        return personService.getPersons(firstName);
    }

    @GetMapping(value = "/{id}")
    public PersonResponse getPersonById(@PathVariable Long id) {
        return personService.fetchPersonById(id);
    }

    @PostMapping()
    public PersonResponse savePerson(@Validated @RequestBody PersonRequest personRequest) {
        return personService.createPerson(personRequest);

    }

    @PutMapping("{id}")
    public PersonResponse updateById(@PathVariable Long id, @RequestBody PersonRequest personRequest) {
        return personService.updateById(id, personRequest);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteById(@PathVariable Long id) {
        personService.deleteById(id);
    }


}
