package de.gothaer.webapp.presentation.v1;


import de.gothaer.webapp.presentation.dto.SchweinDTO;
import de.gothaer.webapp.presentation.mapper.SchweinDTOMapper;
import de.gothaer.webapp.service.SchweinService;
import de.gothaer.webapp.service.SchweineServiceException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import java.util.UUID;

@RestController
@RequestMapping("/v1/schweine")
@RequiredArgsConstructor
public class SchweinCommandController {
    private final SchweinService schweinService;
    private final SchweinDTOMapper schweinDTOMapper;
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) throws SchweineServiceException {

        if(schweinService.loeschen(id))
            return ResponseEntity.ok().build();

        return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> insert(@Valid @RequestBody SchweinDTO person, UriComponentsBuilder builder) throws SchweineServiceException{

        schweinService.speichern(schweinDTOMapper.convert(person));
        UriComponents uriComponents = builder.path("/v1/personen/{id}").buildAndExpand(person.getId());


        return ResponseEntity.created(uriComponents.toUri()).build();
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(@Valid  @RequestBody SchweinDTO person) throws SchweineServiceException{

        if(schweinService.aendern(schweinDTOMapper.convert(person)))
            return ResponseEntity.ok().build();

        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping(value = "/{id}/actions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> fuettern(@RequestParam(required = true) String action, @PathVariable UUID id) throws SchweineServiceException{
        if(! "fuettern".equals(action))
            return ResponseEntity.badRequest().build();
        if(schweinService.fuettern(id))
            return ResponseEntity.ok().build();
        return ResponseEntity.notFound().build();
    }
}
