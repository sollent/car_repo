package com.example.avto.Model;

import com.example.avto.Model.CarModels.CarEngine;
import com.example.avto.Model.CarModels.CarGeneration;
import com.example.avto.Model.CarModels.CarMark;
import com.example.avto.Model.CarModels.CarModel;
import com.example.avto.Model.CarModels.CarPrice;

public class CarInfo {

    private Integer id;

    private CarMark mark;

    private CarModel model;

    private CarGeneration generation;

    private Integer year;

    private SimpleCarType bodyType;

    private SimpleCarType shape;

    private CarPrice price;

    private Integer mileage;

    private SimpleCarType mileageMeasure;

    private CarEngine engine;

    private SimpleCarType transmission;

    private SimpleCarType driveType;

    private SimpleCarType color;

    public CarInfo(Integer id, CarMark mark, CarModel model, CarGeneration generation, Integer year, SimpleCarType bodyType, SimpleCarType shape, CarPrice price, Integer mileage, SimpleCarType mileageMeasure, CarEngine engine, SimpleCarType transmission, SimpleCarType driveType, SimpleCarType color) {
        this.id = id;
        this.mark = mark;
        this.model = model;
        this.generation = generation;
        this.year = year;
        this.bodyType = bodyType;
        this.shape = shape;
        this.price = price;
        this.mileage = mileage;
        this.mileageMeasure = mileageMeasure;
        this.engine = engine;
        this.transmission = transmission;
        this.driveType = driveType;
        this.color = color;
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

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public SimpleCarType getBodyType() {
        return bodyType;
    }

    public void setBodyType(SimpleCarType bodyType) {
        this.bodyType = bodyType;
    }

    public SimpleCarType getShape() {
        return shape;
    }

    public void setShape(SimpleCarType shape) {
        this.shape = shape;
    }

    public CarPrice getPrice() {
        return price;
    }

    public void setPrice(CarPrice price) {
        this.price = price;
    }

    public Integer getMileage() {
        return mileage;
    }

    public void setMileage(Integer mileage) {
        this.mileage = mileage;
    }

    public SimpleCarType getMileageMeasure() {
        return mileageMeasure;
    }

    public void setMileageMeasure(SimpleCarType mileageMeasure) {
        this.mileageMeasure = mileageMeasure;
    }

    public CarEngine getEngine() {
        return engine;
    }

    public void setEngine(CarEngine engine) {
        this.engine = engine;
    }

    public SimpleCarType getTransmission() {
        return transmission;
    }

    public void setTransmission(SimpleCarType transmission) {
        this.transmission = transmission;
    }

    public SimpleCarType getDriveType() {
        return driveType;
    }

    public void setDriveType(SimpleCarType driveType) {
        this.driveType = driveType;
    }

    public SimpleCarType getColor() {
        return color;
    }

    public void setColor(SimpleCarType color) {
        this.color = color;
    }
}
