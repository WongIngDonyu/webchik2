package webchik.services.dtos;


import webchik.models.Offer;

import java.util.UUID;

public class OfferDto {
    private UUID id;
    private String description;
    private Offer.Engine engine;
    private String imageUrl;
    private int mileage;
    private int price;
    private Offer.Transmission transmission;
    private int year;
    private String username;
    private String modelName;

    public OfferDto(){}

    public OfferDto(UUID id, String description, Offer.Engine engine, String imageUrl, int mileage, int price, Offer.Transmission transmission, int year, String username, String modelName) {
        this.id = id;
        this.description = description;
        this.engine = engine;
        this.imageUrl = imageUrl;
        this.mileage = mileage;
        this.price = price;
        this.transmission = transmission;
        this.year = year;
        this.username = username;
        this.modelName = modelName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Offer.Engine getEngine() {
        return engine;
    }

    public void setEngine(Offer.Engine engine) {
        this.engine = engine;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Offer.Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Offer.Transmission transmission) {
        this.transmission = transmission;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
}
