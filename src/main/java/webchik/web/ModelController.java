package webchik.web;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webchik.services.BrandService;
import webchik.services.ModelService;
import webchik.services.dtos.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/model")
public class ModelController {
    private ModelService modelService;
    private BrandService brandService;
    private final ModelMapper modelMapper;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    public ModelController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
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
        return "redirect:/admin/panel";
    }

    @GetMapping("/create")
    public String addNewModel(Model model){
        model.addAttribute("allBrands", brandService.allBrands());
        return "addNewModel";
    }

    @ModelAttribute("addModelDto")
    public AddModelDto initModel(){
        return new AddModelDto();
    }

    @PostMapping("/create")
    public String addNewModel(@Valid AddModelDto addModelDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addModelDto", addModelDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addModelDto", bindingResult);
            return "redirect:/model/create";
        }
        modelService.add(addModelDto);
        return "redirect:/admin/panel";
    }
    @GetMapping("/change/{id}")
    public String changeModel(Model model, @PathVariable("id") UUID uuid){
        Optional<ShowModelInfoDto> dbModel = modelService.findModel(uuid);
        model.addAttribute("addModel", modelMapper.map(dbModel, AddModelDto.class));
        model.addAttribute("allBrands", brandService.allBrands());
        return "addNewModel2";
    }

    @PostMapping("/change/{id}")
    public String saveChangeModel(@Valid AddModelDto addModel, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addModel", addModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addModel", bindingResult);
            return "redirect:/model/change/{id}";
        }
        modelService.update(addModel);
        return "redirect:/admin/panel";
    }
}
