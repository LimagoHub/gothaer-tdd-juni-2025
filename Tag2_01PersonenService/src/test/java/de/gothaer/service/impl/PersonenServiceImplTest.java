package de.gothaer.service.impl;

import de.gothaer.persistence.Person;
import de.gothaer.persistence.PersonenRepository;
import de.gothaer.service.PersonenServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PersonenServiceImplTest {

    @InjectMocks
    private PersonenServiceImpl objectUnderTest;

    @Mock
    private PersonenRepository repoMock;

    @Test
    void speichern_PersonIsNull_throwsPersonenServiceException() throws PersonenServiceException {
        Person invalidPerson = null;
        PersonenServiceException ex =  assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(invalidPerson));
        assertEquals("Person darf nicht null sein.", ex.getMessage());
    }
    @Test
    void speichern_VornameIsNull_throwsPersonenServiceException() throws PersonenServiceException {
        Person invalidPerson = Person.builder().id(UUID.randomUUID().toString()).vorname(null).nachname("Doe").build();
        PersonenServiceException ex =  assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(invalidPerson));
        assertEquals("Vorname zu kurz.", ex.getMessage());
    }


}