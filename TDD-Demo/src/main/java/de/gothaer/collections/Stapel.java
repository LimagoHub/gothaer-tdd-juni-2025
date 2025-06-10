package de.gothaer.collections;

public class Stapel {

    private boolean empty = true;
    public boolean isEmpty() {
        return empty;
    }

    public void push(final Object o) {
        empty = false;
    }
}
