package de.gothaer.model;

public class ExchangeServiceException extends RuntimeException{
    public ExchangeServiceException() {
    }

    public ExchangeServiceException(final String message) {
        super(message);
    }

    public ExchangeServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ExchangeServiceException(final Throwable cause) {
        super(cause);
    }

    public ExchangeServiceException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
