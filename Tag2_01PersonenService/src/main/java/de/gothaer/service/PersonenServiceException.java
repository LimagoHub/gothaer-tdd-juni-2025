package de.gothaer.service;

public class PersonenServiceException extends Exception {
    public PersonenServiceException() {
    }

    public PersonenServiceException(final String message) {
        super(message);
    }

    public PersonenServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public PersonenServiceException(final Throwable cause) {
        super(cause);
    }

    public PersonenServiceException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
