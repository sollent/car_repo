package com.example.avto.Service.Model;

public class Subscription {

    private Integer mark;

    private Integer model;

    private Integer generation;

    public Subscription() {}

    public Subscription(Integer mark, Integer model, Integer generation) {
        this.mark = mark;
        this.model = model;
        this.generation = generation;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Integer getModel() {
        return model;
    }

    public void setModel(Integer model) {
        this.model = model;
    }

    public Integer getGeneration() {
        return generation;
    }

    public void setGeneration(Integer generation) {
        this.generation = generation;
    }
}
