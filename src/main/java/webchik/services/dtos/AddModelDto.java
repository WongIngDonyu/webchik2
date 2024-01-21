package webchik.services.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import webchik.models.Model;
import webchik.validation.UniqueModelName;

import java.util.UUID;

public class AddModelDto {
    @UniqueModelName
    private String name;
    private Model.Category category;
    private String imageUrl;
    private int startYear;
    private int endYear;
    private String brandName;
    private UUID id;
    @NotEmpty(message = "Model name must not be empty!")
    @Size(min = 3, max = 30, message = "Model name must be between 3 and 30 characters!")
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    @NotNull(message = "Model category must not be empty!")
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
    @NotNull(message = "Model start year must not be empty!")
    @Min(value =  2000, message = "Start year must be greater than or equal to 2000")
    public int getStartYear() {
        return startYear;
    }
    public void setStartYear(int startYear) {
        this.startYear = startYear;
    }
    @NotNull(message = "Model start year must not be empty!")
    @Min(value =  2001, message = "End year must be greater than or equal to 2001")
    public int getEndYear() {
        return endYear;
    }
    public void setEndYear(int endYear) {
        this.endYear = endYear;
    }
    @NotEmpty(message = "Model start year must not be empty!")
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
