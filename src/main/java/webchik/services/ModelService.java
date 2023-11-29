package webchik.services;



import webchik.models.Model;
import webchik.services.dtos.AddModelDto;
import webchik.services.dtos.ModelDto;
import webchik.services.dtos.ShowModelInfoDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModelService <I extends UUID>{
    void delete(ModelDto model);

    void delete(UUID id);

    List<ModelDto> getAll();
    List<ShowModelInfoDto> allModels();


    Optional<ModelDto> findModel(UUID id);

    AddModelDto add(AddModelDto model);

    ModelDto update(ModelDto modelDt);
    Model findByName(String name);
}
