package com.example.avto.Model.CarModels;

import androidx.annotation.NonNull;

public class CarMark {

    private Integer id;

    private String name;

    @NonNull
    @Override
    public String toString() {
        return name;
    }

    public CarMark(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}
