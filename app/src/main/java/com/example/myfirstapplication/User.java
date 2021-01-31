package com.example.myfirstapplication;

public class User {
    private String firstName;
    private String lastName;
    private String carMake;
    private String carModel;
    private double carYear;
    private float experience;
    private float rating;
    double lat;
    double lng;
    Boolean init;




    public User(String firstName, String lastName, String carMake, String carModel, double carYear, float rating,
                float experience, double lat, double lng, Boolean init) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.carMake = carMake;
        this.carModel = carModel;
        this.carYear = carYear;
        this.rating = rating;
        this.experience = experience;
        this.lat = lat;
        this.lng = lng;
        this.init = init;
    }

    public User() {
        //public no-arg constructor needed
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCarMake() {
        return carMake;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getCarModel() {
        return carModel;
    }

    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public double getCarYear() {
        return carYear;
    }

    public void setCarYear(double carYear) {
        this.carYear = carYear;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public float getExperience() {
        return experience;
    }

    public void setExperience(float experience) {
        this.experience = experience;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public Boolean getInit() {
        return init;
    }

    public void setInit(Boolean init) {
        this.init = init;
    }
}
