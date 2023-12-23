package webchik.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webchik.models.Offer;
import webchik.models.User;
import webchik.services.ModelService;
import webchik.services.OfferService;
import webchik.services.UserService;
import webchik.services.dtos.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/offer")
public class OfferController {
    private OfferService offerService;
    private UserService userService;
    private ModelService modelService;
    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }

    @GetMapping("/all")
    public String viewAllOffers(Model model){
        model.addAttribute("offers", offerService.getAll());
        return "allOffers";
    }
    @GetMapping("/find2/{id}")
    public String viewAllOffers2(Model model, @PathVariable("id") UUID uuid){
        Optional<ShowOfferInfoDto> offerOptional = offerService.findOffer(uuid);
        offerOptional.ifPresent(offer -> model.addAttribute("offer", offer));
        model.addAttribute("user", userService.findByUsername(offerOptional.get().getUsername()));
        model.addAttribute("model", modelService.findByName(offerOptional.get().getModelName()));
        return "allOffers2";
    }

    @GetMapping("/find/{id}")
    public String findOffer(Model model, @PathVariable("id") UUID uuid){
        Optional<OfferDto> dbOffer = offerService.findOffer(uuid);
        if(dbOffer.isPresent()){
            OfferDto offerDto = dbOffer.get();
            model.addAttribute("offerDto", offerDto);
            return "findOffer";
        }
        else {
            return "offerNotFound";
        }

    }

    @PostMapping("/delete/{id}")
    public String deleteOffer(@PathVariable("id") UUID uuid){
        offerService.delete(uuid);
        return "redirect:/admin/panel";
    }

    @GetMapping("/create")
    public String addNewOffer(Model model){
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("allModels", modelService.allModels());
        return "addNewOffer";
    }

    @ModelAttribute("addOfferDto")
    public AddOfferDto initOffer(){
        return new AddOfferDto();
    }

    @PostMapping("/create")
    public String addNewOffer(@Valid AddOfferDto addOfferDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addOfferDto", addOfferDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addOfferDto", bindingResult);
            return "redirect:/offer/create";
        }
        offerService.add(addOfferDto);
        return "redirect:/admin/panel";
    }
    @GetMapping("/change/{id}")
    public String changeOffer(Model model, @PathVariable("id") UUID uuid){
        Optional<ShowOfferInfoDto> dbOffer = offerService.findOffer(uuid);
        dbOffer.ifPresent(offer -> model.addAttribute("offer", offer));
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("allModels", modelService.allModels());
            return "addNewOffer2";
        }
    @PostMapping("/change/{id}")
    public String saveChangeOffer(@PathVariable("id") UUID uuid, @ModelAttribute  ShowOfferInfoDto offerDto) {
            offerService.update(offerDto);
            return "redirect:/admin/panel";

    }
}
