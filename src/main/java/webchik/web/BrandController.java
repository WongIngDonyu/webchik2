package webchik.web;


import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webchik.models.Brand;
import webchik.services.BrandService;
import webchik.services.dtos.AddBrandDto;
import webchik.services.dtos.BrandDto;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/brand")
public class BrandController {
    private BrandService brandService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping("/all")
    public String viewAllBrands(Model model, Principal principal){
        LOG.log(Level.INFO, "Show all Brands for " + principal.getName());
        List<BrandDto> brands = brandService.getAll();
        model.addAttribute("brands", brands);
        return "allBrands";
    }

    @GetMapping("/find/{id}")
    public String findBrand(Model model, @PathVariable("id") UUID uuid){
        Optional<BrandDto> dbBrand = brandService.findBrand(uuid);
        if (dbBrand.isPresent()) {
            BrandDto brand = dbBrand.get();
            model.addAttribute("brand", brand);
            return "findBrand";
        } else {
            return "brandNotFound";
        }
    }
    @PostMapping("/delete/{id}")
    public String deleteBrand(@PathVariable("id") UUID uuid){
        brandService.delete(uuid);
        return "redirect:/brand/all";
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
    public String addNewBrand(@Valid AddBrandDto addBrandDto, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("addBrandDto", addBrandDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addBrandDto", bindingResult);
            return "redirect:/brand/create";
        }

        brandService.add(addBrandDto);
        return "redirect:/brand/all";
    }


    @GetMapping("/change/{id}")
    public String changeBrand(Model model, @PathVariable("id") UUID uuid){
        Optional<BrandDto> dbBrand = brandService.findBrand(uuid);
        if (dbBrand.isPresent()) {
            BrandDto brand = dbBrand.get();
            model.addAttribute("brandDto", brand);
            return "editBrand";
        } else {
            return "brandNotFound";
        }
    }
    @PostMapping("/change/{id}")
    public String saveChangeBrand(@PathVariable("id") UUID uuid, @ModelAttribute("brandDto") BrandDto brandDto) {
        Optional<Brand> dbBrand = brandService.findBrand(uuid);
        if (dbBrand.isPresent()) {
            brandService.update(brandDto);
            return "redirect:/brand/all";
        } else {
            return "brandNotFound";
        }
    }
}
