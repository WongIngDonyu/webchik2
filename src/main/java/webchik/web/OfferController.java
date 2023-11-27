package webchik.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import webchik.models.Offer;
import webchik.services.OfferService;
import webchik.services.dtos.OfferDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/offer")
public class OfferController {
    private OfferService offerService;
    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/all")
    public String viewAllOffers(Model model){
        List<OfferDto> offers = offerService.getAll();
        model.addAttribute("offers", offers);
        return "allOffers";
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
        return "redirect:/offer/all";
    }

    @GetMapping("/create")
    public String addNewOffer(Model model){
        model.addAttribute("offerDto", new OfferDto());
        return "addNewOffer";
    }

    @PostMapping("/create")
    public String addNewOffer(@ModelAttribute OfferDto offerDto){
        offerService.add(offerDto);
        return "redirect:/offer/all";
    }
    @GetMapping("/change/{id}")
    public String changeOffer(Model model, @PathVariable("id") UUID uuid){
        Optional<OfferDto> dbOffer = offerService.findOffer(uuid);
        if (dbOffer.isPresent()) {
            OfferDto offerDto = dbOffer.get();
            model.addAttribute("offerDto", offerDto);
            return "editOffer";
        } else {
            return "offerNotFound";
        }
    }
    @PostMapping("/change/{id}")
    public String saveChangeOffer(@PathVariable("id") UUID uuid, @ModelAttribute OfferDto offerDto) {
        Optional<Offer> dbOffer = offerService.findOffer(uuid);
        if (dbOffer.isPresent()) {
            offerService.update(offerDto);
            return "redirect:/offer/all";
        } else {
            return "offerNotFound";
        }
    }
}
