package de.gothaer.service;

import de.gothaer.persistence.Person;

public interface BlacklistService {

    boolean isBlacklisted(final Person possibleBlacklistedPerson);
}
