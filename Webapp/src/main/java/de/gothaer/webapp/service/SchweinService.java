package de.gothaer.webapp.service;



import de.gothaer.webapp.service.model.Schwein;

import java.util.Optional;
import java.util.UUID;

public interface SchweinService {

    void speichern(Schwein schwein) throws SchweineServiceException;
    boolean aendern(Schwein schwein) throws SchweineServiceException;

    boolean loeschen(UUID id) throws SchweineServiceException;

    Optional<Schwein> findeAnhandId(UUID id) throws SchweineServiceException;

    Iterable<Schwein> findeAlle() throws SchweineServiceException;
    boolean fuettern(UUID id) throws SchweineServiceException;
}
