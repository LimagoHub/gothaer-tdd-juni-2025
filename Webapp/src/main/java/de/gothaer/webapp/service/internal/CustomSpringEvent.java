package de.gothaer.webapp.service.internal;

public class CustomSpringEvent{
    private final String message;

    public CustomSpringEvent(final  String message) {

        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}