package webchik.services;



import webchik.models.Model;
import webchik.services.dtos.AddModelDto;
import webchik.services.dtos.ChangeModelDto;
import webchik.services.dtos.ShowModelInfoDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModelService <I extends UUID>{
    void delete(UUID id);

    List<ShowModelInfoDto> allModels();


    Optional<ShowModelInfoDto> findModel(UUID id);

    AddModelDto add(AddModelDto model);

    ChangeModelDto update(ChangeModelDto modelDto);
    Model findByName(String name);
}
