package de.gothaer.webapp.service.internal;

import de.gothaer.webapp.persistence.PersonRepository;
import de.gothaer.webapp.service.PersonService;
import de.gothaer.webapp.service.PersonenServiceException;
import de.gothaer.webapp.service.mapper.PersonMapper;
import de.gothaer.webapp.service.model.Person;
import org.springframework.beans.factory.annotation.Qualifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = {PersonenServiceException.class}, isolation = Isolation.READ_COMMITTED, propagation = Propagation.REQUIRED)
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    @Qualifier("antipathen") private final List<String> blacklist;

    /*
           Parameter null -> PSE
           vorname null -> PSE
           vorname zu kurz -> PSE
           nachname null -> PSE
           nachname zu kurz -> PSE
           Attila -> PSE
           runtimeException -> PSE
           happy day -> person wird an repo übergeben
        */
    @Override
    public void speichern(final Person person) throws PersonenServiceException {
        try {
            speichernImpl(person);
        } catch (RuntimeException e) {
           throw new PersonenServiceException("Es ist ein Fehler aufgetreten", e);
        }
    }

    private void speichernImpl(final Person person) throws PersonenServiceException {
        checkPerson(person);

        personRepository.save(personMapper.convert(person));
    }


    @Override
    public boolean aendern(final Person person) throws PersonenServiceException {
        try {
            if(! personRepository.existsById(person.getId())) {
                return false;
            }
            speichernImpl(person);
            return true;
        } catch (RuntimeException e) {
            throw new PersonenServiceException("Es ist ein Fehler aufgetreten",e);
        }
    }

    @Override
    public boolean loeschen(final UUID id) throws PersonenServiceException {
        try {
            if(! personRepository.existsById(id)) {
                return false;
            }

            personRepository.deleteById(id);
            return true;
        } catch (RuntimeException e) {
            throw new PersonenServiceException("Es ist ein Fehler aufgetreten",e);
        }
    }

    @Override
    @Transactional(rollbackFor = PersonenServiceException.class, isolation = Isolation.READ_UNCOMMITTED, propagation = Propagation.REQUIRED)
    public Optional<Person> findeAnhandId(final UUID id) throws PersonenServiceException {
        try {
            return personRepository.findById(id).map(personMapper::convert);
        }catch (Exception e) {
            throw new PersonenServiceException("Upps", e);
        }
    }

    @Override
    public Iterable<Person> findeAlle() throws PersonenServiceException {
        try {
            return personMapper.convert(personRepository.findAll());
        } catch (Exception e) {
            throw new PersonenServiceException("Upps", e);
        }
    }

    private void checkPerson(final Person person) throws PersonenServiceException {
        if(person == null)
            throw new PersonenServiceException("Parameter darf nicht null sein");

        if(person.getVorname() == null || person.getVorname().length() < 2)
            throw new PersonenServiceException("Vorname zu kurz");

        if(person.getNachname() == null || person.getNachname().length() < 2)
            throw new PersonenServiceException("Nachname zu kurz");

        if(blacklist.contains(person.getVorname()))
            throw new PersonenServiceException("Antipath");

        /*
        if(blacklistService.isBlacklisted(person))
            throw new PersonenServiceException("Antipath");

         */
    }
}
