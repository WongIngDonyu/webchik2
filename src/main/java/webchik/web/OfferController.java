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
import webchik.services.ModelService;
import webchik.services.OfferService;
import webchik.services.UserService;
import webchik.services.dtos.*;

import java.security.Principal;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/offer")
public class OfferController {
    private OfferService offerService;
    private UserService userService;
    private ModelService modelService;
    private final ModelMapper modelMapper;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    public OfferController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
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
    @GetMapping("/find/{id}")
    public String viewAllOffers2(Model model, @PathVariable("id") UUID uuid, Principal principal){
        LOG.log(Level.INFO, "Show more information about Offer for " + principal.getName());
        Optional<ShowOfferInfoDto> offerOptional = offerService.findOffer(uuid);
        offerOptional.ifPresent(offer -> model.addAttribute("offer", offer));
        model.addAttribute("user", userService.findByUsername(offerOptional.get().getUsername()));
        model.addAttribute("model", modelService.findByName(offerOptional.get().getModelName()));
        return "allOffers2";
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
        Optional<AddOfferDto> dbOffer = offerService.findOffer(uuid);
        model.addAttribute("addOffer", modelMapper.map(dbOffer, AddOfferDto.class));
        model.addAttribute("allUsers", userService.allUsers());
        model.addAttribute("allModels", modelService.allModels());
            return "changeOffer";
        }
    @PostMapping("/change/{id}")
    public String saveChangeOffer(@Valid  AddOfferDto addOffer,BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addOffer", addOffer);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addOffer", bindingResult);
            return "redirect:/offer/change/{id}";
        }
        offerService.update(addOffer);
            return "redirect:/admin/panel";

    }
}
