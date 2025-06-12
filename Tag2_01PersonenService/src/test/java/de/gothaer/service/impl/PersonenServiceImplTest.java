package de.gothaer.service.impl;

import de.gothaer.persistence.Person;
import de.gothaer.persistence.PersonenRepository;
import de.gothaer.service.BlacklistService;
import de.gothaer.service.PersonenServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PersonenServiceImplTest {

    @InjectMocks
    private PersonenServiceImpl objectUnderTest;

    @Mock
    private PersonenRepository repoMock;

    @Mock
    private BlacklistService blacklistServiceMock;


    @Captor
    ArgumentCaptor<Person> personCaptor;

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
        Person validPerson = Person.builder().id(UUID.randomUUID().toString()).vorname("John").nachname("Der Hunne").build();

        when(blacklistServiceMock.isBlacklisted(validPerson)).thenReturn(true);

        PersonenServiceException ex =  assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(validPerson));
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
        when(blacklistServiceMock.isBlacklisted(validPerson)).thenReturn(false);
        objectUnderTest.speichern(validPerson);
        verify(blacklistServiceMock).isBlacklisted(validPerson);
        verify(repoMock).save(validPerson);
    }

    @Test
    void overload_demo() throws PersonenServiceException {
        when(blacklistServiceMock.isBlacklisted(any(Person.class))).thenReturn(false);

        objectUnderTest.speichern("John", "Doe");

        verify(repoMock).save(personCaptor.capture());

        Person person = personCaptor.getValue();
        assertNotNull(person.getId());
        assertThat( person.getVorname()).startsWith("J");
        assertEquals("Doe", person.getNachname());
    }
    @ParameterizedTest(name = "Nr. {index} mit invalider Person {0} und Meldung {1}")
    @MethodSource("providePersonsForSpeichern")
    void speichern_simplevalidation(Person p, String message) {
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(p));
        assertEquals(message, ex.getMessage());
        verify(repoMock, never()).save(any(Person.class));
    }
    private static Stream<Arguments> providePersonsForSpeichern() {
        return Stream.of(
                Arguments.of((Person)null, "Person darf nicht null sein."),
                Arguments.of(Person.builder().id("1").vorname("John").nachname(null).build(), "Nachname zu kurz."),
                Arguments.of(Person.builder().id("1").vorname("John").nachname("").build(), "Nachname zu kurz."),
                Arguments.of(Person.builder().id("1").vorname("John").nachname("D").build(), "Nachname zu kurz."),
                Arguments.of(Person.builder().id("1").vorname(null).nachname("Doe").build(), "Vorname zu kurz."),
                Arguments.of(Person.builder().id("1").vorname("").nachname("Doe").build(), "Vorname zu kurz."),
                Arguments.of(Person.builder().id("1").vorname("J").nachname("Doe").build(), "Vorname zu kurz.")

        );
    }
}