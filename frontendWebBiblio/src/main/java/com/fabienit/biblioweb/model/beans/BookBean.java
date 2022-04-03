package com.fabienit.biblioweb.model.beans;

import java.time.LocalDate;

/**
 * BookBean
 */
public class BookBean {

    private int id;
    private String title;
    private String authorFirstName;
    private String authorLastName;
    private LocalDate publicationDate;
    private String synopsis;
    private String pictureURL;

    public BookBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorFirstName() {
        return authorFirstName;
    }

    public void setAuthorFirstName(String authorFirstName) {
        this.authorFirstName = authorFirstName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getPictureURL() {
        return pictureURL;
    }

    public void setPictureURL(String pictureURL) {
        this.pictureURL = pictureURL;
    }

    @Override
    public String toString() {
        return "BookBean [authorFirstName=" + authorFirstName + ", authorLastName=" + authorLastName + ", id=" + id
                + ", pictureURL=" + pictureURL + ", publicationDate=" + publicationDate + ", synopsis=" + synopsis
                + ", title=" + title + "]";
    }

    
}