package com.example.avto.Model;

public class CarPost {

    private Integer id;

    private CarInfo carInfo;

    private String title;

    private String description;

    private String sellerName;

    private String[] sellerPhones;

    private String previewImage;

    private String[] images;

    public CarPost(Integer id, CarInfo carInfo, String title, String description, String sellerName, String[] sellerPhones, String previewImage) {
        this.id = id;
        this.carInfo = carInfo;
        this.title = title;
        this.description = description;
        this.sellerName = sellerName;
        this.sellerPhones = sellerPhones;
        this.previewImage = previewImage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CarInfo getCarInfo() {
        return carInfo;
    }

    public void setCarInfo(CarInfo carInfo) {
        this.carInfo = carInfo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String[] getSellerPhones() {
        return sellerPhones;
    }

    public void setSellerPhones(String[] sellerPhones) {
        this.sellerPhones = sellerPhones;
    }

    public String getPreviewImage() {
        return previewImage;
    }

    public void setPreviewImage(String previewImage) {
        this.previewImage = previewImage;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
