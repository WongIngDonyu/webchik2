package webchik.services.impl;


import jdk.jfr.Enabled;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import webchik.models.Brand;
import webchik.repositories.BrandRepository;
import webchik.services.BrandService;
import webchik.services.dtos.AddBrandDto;
import webchik.services.dtos.BrandDto;
import webchik.services.dtos.ShowBrandInfoDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@EnableCaching
public class BrandServiceImpl implements BrandService<UUID> {
    private final ModelMapper modelMapper;
    private final BrandRepository brandRepository;

    public BrandServiceImpl(ModelMapper modelMapper, BrandRepository brandRepository) {
        this.modelMapper = modelMapper;
        this.brandRepository = brandRepository;
    }

    @Override
   // @Cacheable("brands")
    public List<ShowBrandInfoDto> getAll() {
        return brandRepository.findAll().stream().map((b) -> modelMapper.map(b, ShowBrandInfoDto.class)).collect(Collectors.toList());
    }

    @Override
    public void delete(BrandDto brand) {
        brandRepository.deleteById(brand.getId());
    }

    @Override
    public void delete(UUID id) {
        brandRepository.deleteById(id);
    }

    @Override
    public List<ShowBrandInfoDto> allBrands() {
        return brandRepository.findAll().stream().map((b) -> modelMapper.map(b, ShowBrandInfoDto.class)).collect(Collectors.toList());

    }

    @Override
    public Optional<ShowBrandInfoDto> findBrand(UUID id) {
        return Optional.ofNullable(modelMapper.map(brandRepository.findById(id), ShowBrandInfoDto.class));
    }

    @Override
    public AddBrandDto add(AddBrandDto brand) {
        Brand b = modelMapper.map(brand, Brand.class);
        b.setCreated(LocalDateTime.now());
        return modelMapper.map(brandRepository.saveAndFlush(b), AddBrandDto.class);
    }

    @Override
    public ShowBrandInfoDto update(ShowBrandInfoDto brandDto) {
        Optional<Brand> dbBrand = brandRepository.findById(brandDto.getId());
        if(dbBrand.isEmpty()){
            throw new NoSuchElementException("Brand not found");
        }
        else {
            Brand brand1 = modelMapper.map(brandDto, Brand.class);
            brand1.setCreated(dbBrand.get().getCreated());
            brand1.setModified(LocalDateTime.now());
            return modelMapper.map(brandRepository.saveAndFlush(brand1), ShowBrandInfoDto.class);
        }
    }

    @Override
    public Brand findBrandByName(String brandName) {
        return brandRepository.findByName(brandName).orElse(null);
    }
}
