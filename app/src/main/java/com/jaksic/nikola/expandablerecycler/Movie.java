package com.jaksic.nikola.expandablerecycler;

public class Movie {

    private String title;
    private String genre;
    private int year;
    private String desciption;
    // State of the item
    private boolean expanded = false;

    public Movie(String title, String genre, int year, String desciption) {
        this.title = title;
        this.genre = genre;
        this.year = year;
        this.desciption = desciption;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public int getYear() {
        return year;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setDesciption(String desciption) {
        this.desciption = desciption;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDesciption() {
        return desciption;
    }
}