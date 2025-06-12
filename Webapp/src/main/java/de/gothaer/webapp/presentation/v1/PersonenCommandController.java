package de.gothaer.webapp.presentation.v1;


import de.gothaer.webapp.presentation.dto.PersonDto;
import de.gothaer.webapp.presentation.mapper.PersonDtoMapper;
import de.gothaer.webapp.service.PersonService;
import de.gothaer.webapp.service.PersonenServiceException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@RestController
@RequestMapping("/v1/personen")
@RequiredArgsConstructor
public class PersonenCommandController {

    private final PersonService personService;
    private final PersonDtoMapper mapper;


    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws PersonenServiceException {
        if(personService.loeschen(id)) return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

    @PostMapping(path="", consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Void>  anlegen(@Valid @RequestBody PersonDto personDto, UriComponentsBuilder builder) throws PersonenServiceException {

        personService.speichern(mapper.convert(personDto));
        UriComponents uriComponents = builder.path("/v1/personen/{id}").buildAndExpand(personDto.getId());
        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Void> aendern(@PathVariable UUID id, @Valid @RequestBody PersonDto personDto) throws PersonenServiceException {

        if(! id.equals( personDto.getId())) throw new IllegalArgumentException("Upps");
        // Person update
        if (personService.aendern(mapper.convert(personDto)))  return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }

}
