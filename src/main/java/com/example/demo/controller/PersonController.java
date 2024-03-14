package com.example.demo.controller;

import com.example.demo.model.request.PersonRequest;
import com.example.demo.model.response.PersonResponse;
import com.example.demo.service.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/persons")
@Tag(name = "Person Controller", description = "To work with person data")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping
    @Operation(summary = "to fetch person data")
    @ResponseStatus(HttpStatus.OK)
    public List<PersonResponse> getPersons(@RequestParam(required = false) String firstName) {
        return personService.getPersons(firstName);
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonResponse getPersonById(@PathVariable Long id) {
        return personService.fetchPersonById(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public PersonResponse savePerson(@Validated @RequestBody PersonRequest personRequest) {
        return personService.createPerson(personRequest);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonResponse updateById(@PathVariable Long id, @Validated @RequestBody PersonRequest personRequest) {
        return personService.updateById(id, personRequest);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) {
        personService.deleteById(id);
    }
}
