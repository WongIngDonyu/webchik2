package webchik.web;

import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webchik.models.User;
import webchik.models.UserRole;
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
    private UserDetailsService userDetailsService;
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
    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
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
    public String deleteOffer(@PathVariable("id") UUID uuid, Principal principal){
        offerService.delete(uuid);
        LOG.info("Delete Offer ("+uuid+") by  "+principal.getName());
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
    public String addNewOffer(@Valid AddOfferDto addOfferDto, BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addOfferDto", addOfferDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addOfferDto", bindingResult);
            return "redirect:/offer/create";
        }
        offerService.add(addOfferDto);
        LOG.info("Create new Offer ("+addOfferDto.getId()+") by "+principal.getName());
        User user = modelMapper.map(userDetailsService.loadUserByUsername(principal.getName()), User.class);
        boolean isAdmin = user.getUserRoles().stream().anyMatch(userRole -> userRole.getRole() == UserRole.Role.ADMIN);
        return isAdmin ? "redirect:/admin/panel" : "redirect:/";
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
    public String saveChangeOffer(@Valid  AddOfferDto addOffer,BindingResult bindingResult, RedirectAttributes redirectAttributes, Principal principal) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addOffer", addOffer);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addOffer", bindingResult);
            return "redirect:/offer/change/{id}";
        }
        offerService.update(addOffer);
        LOG.info("Change Offer ("+addOffer.getId()+") by "+principal.getName());
        return "redirect:/admin/panel";

    }
}
