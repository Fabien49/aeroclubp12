package com.fabienit.bibliobatch.beans;

/**
 * LibraryBean
 */
public class LibraryBean {

    private int id;
    private String name;

    public LibraryBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LibraryBean [id=" + id + ", name=" + name + "]";
    }
    
    
}