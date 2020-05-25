package com.example.avto.Service.Model;

import com.example.avto.Model.CarModels.CarGeneration;
import com.example.avto.Model.CarModels.CarMark;
import com.example.avto.Model.CarModels.CarModel;

public class SubscriptionResponse {

    private Integer id;

    private CarMark mark;

    private CarModel model;

    private CarGeneration generation;

    private Boolean active;

    public SubscriptionResponse(Integer id, CarMark mark, CarModel model, CarGeneration generation, Boolean active) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.generation = generation;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CarMark getMark() {
        return mark;
    }

    public void setMark(CarMark mark) {
        this.mark = mark;
    }

    public CarModel getModel() {
        return model;
    }

    public void setModel(CarModel model) {
        this.model = model;
    }

    public CarGeneration getGeneration() {
        return generation;
    }

    public void setGeneration(CarGeneration generation) {
        this.generation = generation;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}

