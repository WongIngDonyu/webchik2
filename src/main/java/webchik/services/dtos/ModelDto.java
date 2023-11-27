package webchik.services.dtos;


import webchik.models.Model;

import java.util.UUID;

public class ModelDto {
    private UUID id;
    private String name;
    private Model.Category category;
    private String imageUrl;
    private int startYear;
    private int endYear;
    private String brandName;
    public ModelDto(){}

    public ModelDto(UUID id, String name, Model.Category category, String imageUrl, int startYear, int endYear, String brandName) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.imageUrl = imageUrl;
        this.startYear = startYear;
        this.endYear = endYear;
        this.brandName = brandName;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

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
}
