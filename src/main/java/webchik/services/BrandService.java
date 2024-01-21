package webchik.services;



import webchik.models.Brand;
import webchik.services.dtos.AddBrandDto;
import webchik.services.dtos.ShowBrandInfoDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BrandService <I extends UUID>{

    void delete(UUID id);
    List<ShowBrandInfoDto> allBrands();
    Optional<ShowBrandInfoDto> findBrand(UUID id);
    AddBrandDto add(AddBrandDto brand);
    AddBrandDto update(AddBrandDto brand);
    Brand findBrandByName(String brandName);
}
