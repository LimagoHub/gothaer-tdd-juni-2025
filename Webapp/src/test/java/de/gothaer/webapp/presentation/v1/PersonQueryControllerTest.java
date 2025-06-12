package de.gothaer.webapp.presentation.v1;

import de.gothaer.webapp.presentation.dto.PersonDto;
import de.gothaer.webapp.service.PersonService;
import de.gothaer.webapp.service.PersonenServiceException;
import de.gothaer.webapp.service.model.Person;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
//@Sql({"/create.sql", "/insert.sql"})
class PersonQueryControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PersonService serviceMock;

    @Test
    void test1() throws Exception {
        Person person = Person.builder().id(UUID.fromString("b2e24e74-8686-43ea-baff-d9396b4202e0")).vorname("John").nachname("Doe").build();

        when(serviceMock.findeAnhandId(any(UUID.class))).thenReturn(Optional.of(person));
        var personDTO = restTemplate.getForObject("/v1/personen/b2e24e74-8686-43ea-baff-d9396b4202e0", PersonDto.class);
        assertEquals("John", personDTO.getVorname());
        assertEquals("Doe", personDTO.getNachname());
    }

    /*@Test
    void test2() {
        var personDTO = restTemplate.getForObject("/v1/personen/b2e24e74-8686-43ea-baff-d9396b4202e0", String.class);
        System.out.println(personDTO);
    }
    */
    @Test
    void test3() throws Exception {
        Person person = Person.builder().id(UUID.fromString("b2e24e74-8686-43ea-baff-d9396b4202e0")).vorname("John").nachname("Doe").build();

        when(serviceMock.findeAnhandId(any(UUID.class))).thenReturn(Optional.of(person));
        var entity = restTemplate.getForEntity("/v1/personen/b2e24e74-8686-43ea-baff-d9396b4202e0", PersonDto.class);

        var personDTO = entity.getBody();
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertEquals("John", personDTO.getVorname());
        assertEquals("Doe", personDTO.getNachname());
    }

    @Test
    void test4() throws Exception {

        PersonDto dto  = PersonDto.builder().vorname("John").nachname("Doe").build();
        HttpEntity<PersonDto> request = new HttpEntity<>(dto);

        var personen = List.of(
                Person.builder().id(UUID.fromString("86dac2d5-7edc-483a-abc6-239e5b93eb13")).vorname("John").nachname("Doe").build(),
                Person.builder().id(UUID.fromString("86dac2d5-7edc-483a-abc6-239e5b93eb13")).vorname("John").nachname("Rambo").build(),
                Person.builder().id(UUID.fromString("86dac2d5-7edc-483a-abc6-239e5b93eb13")).vorname("John").nachname("Wayne").build()
        );

        when(serviceMock.findeAlle()).thenReturn(personen);
        var entity = restTemplate.exchange("/v1/personen",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<PersonDto>>() { });

        var personDTO = entity.getBody();

    }

    @Test
    void post() throws PersonenServiceException {
        final UUID uuid = UUID.randomUUID();
        final PersonDto personDtoToSend = PersonDto.builder().id(uuid).vorname("John").nachname("Doe").build();
        final HttpEntity<PersonDto> request = new HttpEntity<>(personDtoToSend);

        final Person personToVerify = Person.builder().id(uuid).vorname("John").nachname("Doe").build();

        doNothing().when(serviceMock).speichern(any());

        var entity = restTemplate.exchange("/v1/personen", HttpMethod.POST, request, Void.class);
        verify(serviceMock).speichern(personToVerify);
        assertTrue(entity.getHeaders().getLocation().getPath().endsWith("/v1/personen/" + uuid.toString()));
        assertEquals(HttpStatus.CREATED, entity.getStatusCode() );
    }

}