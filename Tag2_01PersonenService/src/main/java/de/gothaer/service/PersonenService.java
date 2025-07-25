package de.gothaer.service;

import de.gothaer.persistence.Person;

public interface PersonenService {

    void speichern(Person person) throws PersonenServiceException;
    void speichern(String vorname, String nachname) throws PersonenServiceException;
}
