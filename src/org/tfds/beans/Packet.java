package org.tfds.beans;

public class Packet<E> {

    private boolean isSuccessful;
    private String message;
    private E entity;

    public Packet(boolean isSuccessful, String message, E entity) {
        this.isSuccessful = isSuccessful;
        this.message = message;
        this.entity = entity;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public String getMessage() {
        return message;
    }

    public E getEntity() {
        return entity;
    }
}