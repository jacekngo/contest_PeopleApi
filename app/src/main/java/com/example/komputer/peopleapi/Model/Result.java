package com.example.komputer.peopleapi.Model;

import com.google.gson.annotations.Expose;

/**
 * Created by komputer on 2017-08-23.
 */

public class Result{
    @Expose
    private Name name;
    @Expose
    private String email;
    @Expose
    private Picture picture;

    private boolean favorite;

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
    //TODO sprawdzić czy z api moze wygenerować identyfikator, napisać metody equals i hashCode na potzeby porównań w sortedlist


}
