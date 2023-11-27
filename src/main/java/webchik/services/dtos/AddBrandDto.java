package webchik.services.dtos;

import jakarta.validation.constraints.NotEmpty;
import webchik.validation.UniqueBrandName;

public class AddBrandDto {
    @UniqueBrandName
    private String name;
    @NotEmpty(message = "Brand name must not be empty!")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
