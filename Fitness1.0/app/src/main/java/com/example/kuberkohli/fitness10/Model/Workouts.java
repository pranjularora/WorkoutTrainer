package com.example.kuberkohli.fitness10.Model;


import java.io.Serializable;

// this is same as movie
public class Workouts implements Serializable{

    String name, description, director, id , primaryID, gif_url, stars, url, year, length, url_ex1, url_ex2;
    String rating;
    String exercise1_url, exercise2_url, exercise3_url, exercise4_url, exercise5_url;
    String exercise1, exercise2, exercise3, exercise4,exercise5;
    String exercise1_reps, exercise2_reps, exercise3_reps, exercise4_reps, exercise5_reps;

    public Workouts() {

    }

    public String getExercise1() {
        return exercise1;
    }
    public String getExercise1_reps() {
        return exercise1_reps;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrimaryID() {
        return primaryID;
    }

    public void setPrimaryID(String primaryID) {
        this.primaryID = primaryID;
    }

    public String getUrl() {
        return url;
    }


    public String getUrlExercise1() {
        return url_ex1;
    }

    public String getUrlExercise2() {
        return url_ex2;
    }

    public String getGif_url(){
        return gif_url;
    }

}
