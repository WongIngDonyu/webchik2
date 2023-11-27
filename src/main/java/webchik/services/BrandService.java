package webchik.services;



import webchik.models.Brand;
import webchik.services.dtos.AddBrandDto;
import webchik.services.dtos.BrandDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BrandService <I extends UUID>{
    void delete(BrandDto brand);

    void delete(UUID id);

    List<BrandDto> getAll();

    Optional<BrandDto> findBrand(UUID id);

    AddBrandDto add(AddBrandDto brand);
    BrandDto update(BrandDto brand);
    Brand findBrandByName(String brandName);
}
