package com.example.komputer.peopleapi.Model;

import com.google.gson.annotations.Expose;

/**
 * Created by komputer on 2017-08-23.
 */

public class Name {
    @Expose
    private String title;
    @Expose
    private String first;
    @Expose
    private String last;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst() {
        return first.substring(0, 1).toUpperCase() + first.substring(1);
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last.substring(0, 1).toUpperCase() + last.substring(1);
    }

    public void setLast(String last) {
        this.last = last;
    }

}