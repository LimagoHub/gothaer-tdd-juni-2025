package de.gothaer.service.impl;

import de.gothaer.persistence.Person;
import de.gothaer.persistence.PersonenRepository;
import de.gothaer.service.PersonenService;
import de.gothaer.service.PersonenServiceException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PersonenServiceImpl implements PersonenService {

    private final PersonenRepository repository;



     /*
       1.) wenn person = null => PSE
       2.) wenn vorname null oder weniger als 2 Zeichen => PSE
       3.) wenn nachname null oder weniger als 2 Zeichen => PSE
       4.) wenn Vorname = Attila => PSE
       5.) wenn Laufzeitfehler => PSE

       Happy day => person via repo speichern
    */

    @Override
    public void speichern(Person person) throws PersonenServiceException {
        if (person == null) throw new PersonenServiceException("Person darf nicht null sein.");
        throw new PersonenServiceException("Vorname zu kurz.");
    }
}
