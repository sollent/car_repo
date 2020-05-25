package com.example.avto.Model.CarModels;

public class CarPrice {

    private Integer id;

    private Integer byn;

    private Integer usd;

    public CarPrice(Integer id, Integer byn, Integer usd) {
        this.id = id;
        this.byn = byn;
        this.usd = usd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getByn() {
        return byn;
    }

    public void setByn(Integer byn) {
        this.byn = byn;
    }

    public Integer getUsd() {
        return usd;
    }

    public void setUsd(Integer usd) {
        this.usd = usd;
    }
}
