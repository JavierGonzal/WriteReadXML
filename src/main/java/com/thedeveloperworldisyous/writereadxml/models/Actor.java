package com.thedeveloperworldisyous.writereadxml.models;

/**
 * Created by javiergonzalezcabezas on 8/10/15.
 */
public class Actor {
    String name;
    String surname;

    public Actor(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
