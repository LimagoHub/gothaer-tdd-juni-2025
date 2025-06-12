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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class PersonenCommandControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private PersonService serviceMock;

    @Test
    void delete_success() throws Exception {
        when(serviceMock.loeschen(any(UUID.class))).thenReturn(true);
        var entity = restTemplate.exchange("/v1/personen/b2e24e74-8686-43ea-baff-d9396b4202e0",
                HttpMethod.DELETE,
                null,
                Void.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
    }

    @Test
    void delete_notFound() throws Exception {
        when(serviceMock.loeschen(any(UUID.class))).thenReturn(false);
        var entity = restTemplate.exchange("/v1/personen/b2e24e74-8686-43ea-baff-d9396b4202e0",
                HttpMethod.DELETE,
                null,
                Void.class);
        assertEquals(HttpStatus.NOT_FOUND, entity.getStatusCode());
    }


}