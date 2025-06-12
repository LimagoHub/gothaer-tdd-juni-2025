package de.gothaer.webapp.presentation.v1;

import de.gothaer.webapp.presentation.dto.PersonDto;
import de.gothaer.webapp.presentation.mapper.PersonDtoMapper;
import de.gothaer.webapp.service.PersonService;
import de.gothaer.webapp.service.PersonenServiceException;
import de.gothaer.webapp.service.internal.CustomSpringEvent;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.UUID;

@RestController
@RequestMapping("/v1/personen")

@RequestScope
@RequiredArgsConstructor
@Slf4j
public class PersonQueryController {


    private final PersonService personService;
    private final PersonDtoMapper mapper;
    private final ApplicationEventPublisher eventPublisher;

    @Operation(summary = "Liefert eine Person")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Person gefunden",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PersonDto.class)) }),
            @ApiResponse(responseCode = "400", description = "ungueltige ID",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Person nicht gefunden",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "internal server error",
                    content = @Content)})


    @GetMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<PersonDto> gibMichPerson(@PathVariable UUID id) throws PersonenServiceException {

        return ResponseEntity.of(personService.findeAnhandId(id).map(mapper::convert));
    }

    @GetMapping(path = "", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Iterable<PersonDto>> gibMichAlle(
            @RequestParam(required = false, defaultValue = "") String vorname,
            @RequestParam(required = false, defaultValue = "") String name
    ) throws PersonenServiceException{

        publishCustomEvent("Hallo");
        log.warn(String.format("Vorname = '%s', Nachname = '%s'\n", vorname, name));

        return ResponseEntity.ok(mapper.convert(personService.findeAlle()));
    }

    @PostMapping(value = "/scripts/toupper", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PersonDto>  machVornameGross(@Valid @RequestBody PersonDto personDto) {
        personDto.setVorname(personDto.getVorname().toUpperCase());
        return ResponseEntity.ok(personDto);
    }
    public void publishCustomEvent(final String message) {
        System.out.println("Publishing custom event. ");
        CustomSpringEvent customSpringEvent = new CustomSpringEvent( message);
        eventPublisher.publishEvent(customSpringEvent);
    }

}
