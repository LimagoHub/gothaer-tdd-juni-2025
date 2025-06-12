package de.gothaer.webapp.service;

import de.gothaer.webapp.service.model.Person;

public interface BlacklistService {

    boolean isBlacklisted(final Person possibleBlacklistedPerson);
}
