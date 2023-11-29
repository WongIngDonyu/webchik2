package webchik.services.dtos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import webchik.validation.UniqueBrandName;

public class AddBrandDto {
    @UniqueBrandName
    private String name;
    @NotEmpty(message = "Brand name must not be empty!")
    @Size(min = 5, max = 30, message = "Brand name must be between 5 and 30 characters!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
