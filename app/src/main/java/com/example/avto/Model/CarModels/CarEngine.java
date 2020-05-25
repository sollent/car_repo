package com.example.avto.Model.CarModels;

import com.example.avto.Model.SimpleCarType;

public class CarEngine {

    private Integer id;

    private SimpleCarType type;

    private Integer engineCapacity;

    private String engineCapacityHint;

    private Boolean hybrid;

    private Boolean gasEquipment;

    private SimpleCarType gasEquipmentType;

    private Integer powerReserve;

    private String powerReserveHint;

    public CarEngine(Integer id, SimpleCarType type, Integer engineCapacity, String engineCapacityHint, Boolean hybrid, Boolean gasEquipment, SimpleCarType gasEquipmentType, Integer powerReserve, String powerReserveHint) {
        this.id = id;
        this.type = type;
        this.engineCapacity = engineCapacity;
        this.engineCapacityHint = engineCapacityHint;
        this.hybrid = hybrid;
        this.gasEquipment = gasEquipment;
        this.gasEquipmentType = gasEquipmentType;
        this.powerReserve = powerReserve;
        this.powerReserveHint = powerReserveHint;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public SimpleCarType getType() {
        return type;
    }

    public void setType(SimpleCarType type) {
        this.type = type;
    }

    public Integer getEngineCapacity() {
        return engineCapacity;
    }

    public void setEngineCapacity(Integer engineCapacity) {
        this.engineCapacity = engineCapacity;
    }

    public String getEngineCapacityHint() {
        return engineCapacityHint;
    }

    public void setEngineCapacityHint(String engineCapacityHint) {
        this.engineCapacityHint = engineCapacityHint;
    }

    public Boolean getHybrid() {
        return hybrid;
    }

    public void setHybrid(Boolean hybrid) {
        this.hybrid = hybrid;
    }

    public Boolean getGasEquipment() {
        return gasEquipment;
    }

    public void setGasEquipment(Boolean gasEquipment) {
        this.gasEquipment = gasEquipment;
    }

    public SimpleCarType getGasEquipmentType() {
        return gasEquipmentType;
    }

    public void setGasEquipmentType(SimpleCarType gasEquipmentType) {
        this.gasEquipmentType = gasEquipmentType;
    }

    public Integer getPowerReserve() {
        return powerReserve;
    }

    public void setPowerReserve(Integer powerReserve) {
        this.powerReserve = powerReserve;
    }

    public String getPowerReserveHint() {
        return powerReserveHint;
    }

    public void setPowerReserveHint(String powerReserveHint) {
        this.powerReserveHint = powerReserveHint;
    }
}
