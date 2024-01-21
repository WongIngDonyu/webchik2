package webchik.web;


import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webchik.services.BrandService;
import webchik.services.dtos.*;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/brand")
public class BrandController {
    private BrandService brandService;
    private final ModelMapper modelMapper;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    public BrandController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }


    @PostMapping("/delete/{id}")
    public String deleteBrand(@PathVariable("id") UUID uuid, Principal principal){
        brandService.delete(uuid);
        LOG.info("Delete Brand ("+uuid+") by  "+principal.getName());
        return "redirect:/admin/panel";
    }

    @GetMapping("/create")
    public String addNewBrand(){

        return "addNewBrand";
    }

    @ModelAttribute("addBrandDto")
    public AddBrandDto initBrand(){
        return new AddBrandDto();
    }

    @PostMapping("/create")
    public String addNewBrand(@Valid AddBrandDto addBrandDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addBrandDto", addBrandDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addBrandDto", bindingResult);
            return "redirect:/brand/create";
        }
        brandService.add(addBrandDto);
        LOG.info("Create new Brand ("+addBrandDto.getName()+") by "+principal.getName());
        return "redirect:/admin/panel";
    }


    @GetMapping("/change/{id}")
    public String changeBrand(Model model, @PathVariable("id") UUID uuid){
        Optional<ShowBrandInfoDto> dbBrand = brandService.findBrand(uuid);
        model.addAttribute("brandDto", modelMapper.map(dbBrand, AddBrandDto.class));
        return "changeBrand";
    }
    @PostMapping("/change/{id}")
    public String saveChangeBrand(@Valid AddBrandDto brandDto,
                                  BindingResult bindingResult,
                                  Model model,
                                  Principal principal) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("brandDto", brandDto);
            model.addAttribute("org.springframework.validation.BindingResult.brandDto", bindingResult);
            return "changeBrand";
        }
        brandService.update(brandDto);
        LOG.info("Change Brand (" + brandDto.getName() + ") by " + principal.getName());
        return "redirect:/admin/panel";
    }
}
