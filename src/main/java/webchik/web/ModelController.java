package webchik.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webchik.services.ModelService;
import webchik.services.dtos.ModelDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/model")
public class ModelController {
    private ModelService modelService;
    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/all")
    public String viewAllModels(Model model){
        List<ModelDto> models = modelService.getAll();
        model.addAttribute("models",models);
        return "allModels";
    }

    @GetMapping("/find/{id}")
    public String findModel(Model model, @PathVariable("id") UUID uuid){
        Optional<ModelDto> dbModel = modelService.findModel(uuid);
        if(dbModel.isPresent()){
            ModelDto modelDto = dbModel.get();
            model.addAttribute("modelDto",modelDto);
            return "findModel";
        }
        else{
            return "modelNotFound";
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteModel(@PathVariable("id") UUID uuid){
        modelService.delete(uuid);
        return "redirect:/model/all";
    }

    @GetMapping("/create")
    public String addNewModel(Model model){
        model.addAttribute("modelDto", new ModelDto());
        return "addNewModel";
    }

    @PostMapping("/create")
    public String addNewModel(@ModelAttribute("modelDto") ModelDto modelDto){
        modelService.add(modelDto);
        return "redirect:/model/all";
    }
    @GetMapping("/change/{id}")
    public String changeModel(Model model, @PathVariable("id") UUID uuid){
        Optional<ModelDto> dbModel = modelService.findModel(uuid);
        if (dbModel.isPresent()) {
            ModelDto modelDto = dbModel.get();
            model.addAttribute("modelDto", modelDto);
            return "editModel";
        } else {
            return "modelNotFound";
        }
    }
    @PostMapping("/change/{id}")
    public String saveChangeModel(@PathVariable("id") UUID uuid, @ModelAttribute ModelDto modelDto) {
        Optional<Model> dbModel = modelService.findModel(uuid);
        if (dbModel.isPresent()) {
            modelService.update(modelDto);
            return "redirect:/model/all";
        } else {
            return "modelNotFound";
        }
    }
}
