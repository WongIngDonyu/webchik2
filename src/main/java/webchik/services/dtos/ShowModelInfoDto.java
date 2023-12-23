package webchik.services.dtos;

import webchik.models.Model;

import java.util.UUID;

public class ShowModelInfoDto {
    private String name;
    private Model.Category category;
    private String imageUrl;
    private int startYear;
    private int endYear;
    private String brandName;
    private UUID id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Model.Category getCategory() {
        return category;
    }

    public void setCategory(Model.Category category) {
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getStartYear() {
        return startYear;
    }

    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }

    public int getEndYear() {
        return endYear;
    }

    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
