package de.gothaer.collections;

public class Stapel {

    private Object [] data;
    private int index;

    public Stapel() {
        data = new Object [10];
        index = 0;
    }

    public void push(Object value) throws StapelException{
        if(isFull()) throw new StapelException("Overflow");
        data[index++] = value;
    }

    public Object pop() throws StapelException{
        if(isEmpty()) throw new StapelException("Underflow");
        return data[--index];
    }

    public boolean isEmpty() {
        return index == 0;
    }

    public boolean isFull() {
        return index == data.length;
    }
}
