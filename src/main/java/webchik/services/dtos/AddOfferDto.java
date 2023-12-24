package webchik.services.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import webchik.models.Offer;

import java.util.UUID;

public class AddOfferDto {
    private String description;
    private Offer.Engine engine;
    private String imageUrl;
    private int mileage;
    private int price;
    private Offer.Transmission transmission;
    private int year;
    private String username;
    private String modelName;
    private UUID id;
    @NotEmpty(message = "Description must not be empty!")
    @Size(min = 10,  message = "Description must be over 10 characters")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @NotNull(message = "Engine must not be empty!")
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
    @NotNull(message = "Mileage must not be empty!")
    @Min(value = 1, message = "Mileage must be a positive number!")
    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    @NotNull(message = "Price must not be empty!")
    @Min(value = 1, message = "Price must be a positive number!")
    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
    @NotNull(message = "Transmission must not be empty!")
    public Offer.Transmission getTransmission() {
        return transmission;
    }

    public void setTransmission(Offer.Transmission transmission) {
        this.transmission = transmission;
    }
    @NotNull(message = "Model year must not be empty!")
    @Min(value =  1, message = "Year must be greater than or equal to 1")
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    @NotNull(message = "Username must not be empty!")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    @NotNull(message = "ModelName must not be empty!")
    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
