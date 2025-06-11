package de.gothaer.service.impl;

import de.gothaer.persistence.Person;
import de.gothaer.persistence.PersonenRepository;
import de.gothaer.service.BlacklistService;
import de.gothaer.service.PersonenServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PersonenServiceImplTest {

    @InjectMocks
    private PersonenServiceImpl objectUnderTest;

    @Mock
    private PersonenRepository repoMock;

    @Mock
    private BlacklistService blacklistServiceMock;

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
    @Test
    void speichern_VornameZuKurz_throwsPersonenServiceException() throws PersonenServiceException {
        Person invalidPerson = Person.builder().id(UUID.randomUUID().toString()).vorname("J").nachname("Doe").build();
        PersonenServiceException ex =  assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(invalidPerson));
        assertEquals("Vorname zu kurz.", ex.getMessage());
    }
    @Test
    void speichern_NachnameIsNull_throwsPersonenServiceException() throws PersonenServiceException {
        Person invalidPerson = Person.builder().id(UUID.randomUUID().toString()).vorname("John").nachname(null).build();
        PersonenServiceException ex =  assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(invalidPerson));
        assertEquals("Nachname zu kurz.", ex.getMessage());
    }

    @Test
    void speichern_NachnameZuKurz_throwsPersonenServiceException() throws PersonenServiceException {
        Person invalidPerson = Person.builder().id(UUID.randomUUID().toString()).vorname("John").nachname("D").build();
        PersonenServiceException ex =  assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(invalidPerson));
        assertEquals("Nachname zu kurz.", ex.getMessage());
    }

    @Test
    void speichern_UnerwuenschtePerson_throwsPersonenServiceException() throws PersonenServiceException {
        Person invalidPerson = Person.builder().id(UUID.randomUUID().toString()).vorname("Attila").nachname("Der Hunne").build();
        PersonenServiceException ex =  assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(invalidPerson));
        assertEquals("Blacklisted Person.", ex.getMessage());
    }

    @Test
    void speichern_UnexpectedRuntimeExceptionInUnderlyingService_throwsPersonenServiceException() throws PersonenServiceException {
        Person validPerson = Person.builder().id(UUID.randomUUID().toString()).vorname("John").nachname("Doe").build();

        doThrow(new ArithmeticException("Upps")).when(repoMock).save(any(Person.class));

        PersonenServiceException ex =  assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(validPerson));
        assertEquals("Es ist ein Fehler aufgetreten.", ex.getMessage());
        assertEquals(ArithmeticException.class, ex.getCause().getClass());
    }

    @Test
    void speichern_HappyDay_PersonPassedToRepo() throws PersonenServiceException {
        Person validPerson = Person.builder().id(UUID.randomUUID().toString()).vorname("John").nachname("Doe").build();
        objectUnderTest.speichern(validPerson);
        verify(repoMock).save(validPerson);
    }


}