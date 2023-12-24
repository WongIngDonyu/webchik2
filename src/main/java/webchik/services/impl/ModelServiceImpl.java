package webchik.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Service;
import webchik.models.Brand;
import webchik.models.Model;
import webchik.repositories.BrandRepository;
import webchik.repositories.ModelRepository;
import webchik.services.ModelService;
import webchik.services.dtos.AddModelDto;
import webchik.services.dtos.ModelDto;
import webchik.services.dtos.ShowModelInfoDto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@EnableCaching

public class ModelServiceImpl  implements ModelService<UUID> {
    private final ModelMapper modelMapper;
    private final ModelRepository modelRepository;
    private final BrandRepository brandRepository;
    private final BrandServiceImpl brandService;

    public ModelServiceImpl(ModelMapper modelMapper, ModelRepository modelRepository, BrandRepository brandRepository, BrandServiceImpl brandService) {
        this.modelMapper = modelMapper;
        this.modelRepository = modelRepository;
        this.brandRepository = brandRepository;
        this.brandService = brandService;
    }


    @Override
    public void delete(ModelDto model) {
        modelRepository.deleteById(model.getId());
    }

    @Override
    public void delete(UUID id) {
        modelRepository.deleteById(id);
    }

    @Override
    //@Cacheable("models")
    public List<ShowModelInfoDto> getAll() {
        return modelRepository.findAll().stream().map((m)->modelMapper.map(m, ShowModelInfoDto.class)).collect(Collectors.toList());
    }

    @Override
    public List<ShowModelInfoDto> allModels() {
        return modelRepository.findAll().stream().map((m)->modelMapper.map(m, ShowModelInfoDto.class)).collect(Collectors.toList());

    }

    @Override
    public Optional<ShowModelInfoDto> findModel(UUID id) {
        return Optional.ofNullable(modelMapper.map(modelRepository.findById(id), ShowModelInfoDto.class));
    }

    @Override
    public AddModelDto add(AddModelDto model) {
        Model m = modelMapper.map(model, Model.class);
        m.setBrand(brandService.findBrandByName(model.getBrandName()));
        m.setCreated(LocalDateTime.now());
        return modelMapper.map(modelRepository.saveAndFlush(m), AddModelDto.class);
    }

    @Override
    public AddModelDto update(AddModelDto modelDto) {
        Optional<Model> dbModel = modelRepository.findById(modelDto.getId());
        if (dbModel.isEmpty()) {
            throw new NoSuchElementException("Model not found");
        }
        Model model = modelMapper.map(modelDto, Model.class);
        Optional<Brand> dbBrand = brandRepository.findByName(modelDto.getBrandName());
        if (dbBrand.isEmpty()){
            throw new NoSuchElementException("Brand not found");
        }
        model.setBrand(brandService.findBrandByName(modelDto.getBrandName()));
            return modelMapper.map(modelRepository.saveAndFlush(model), AddModelDto.class);
        }

    @Override
    public Model findByName(String name) {
        return modelRepository.findByName(name).orElse(null);
    }
}

