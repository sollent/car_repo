package com.example.avto.Model.CarModels;

import androidx.annotation.NonNull;

public class CarGeneration {

    private Integer id;

    private String name;

    private String fromYear;

    private String toYear;

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public CarGeneration(Integer id, String name, String fromYear, String toYear) {
        this.id = id;
        this.name = name;
        this.fromYear = fromYear;
        this.toYear = toYear;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFromYear() {
        return fromYear;
    }

    public void setFromYear(String fromYear) {
        this.fromYear = fromYear;
    }

    public String getToYear() {
        return toYear;
    }

    public void setToYear(String toYear) {
        this.toYear = toYear;
    }
}
