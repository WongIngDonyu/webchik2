package webchik.web;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import webchik.models.Offer;
import webchik.services.BrandService;
import webchik.services.OfferService;
import webchik.services.impl.OfferServiceImpl;

import java.security.Principal;

@Controller
public class HomeController {

    private OfferService offerService;
    private static final Logger LOG = LogManager.getLogger(Controller.class);
    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }

    @GetMapping("/")
    public String homePage(Model model, Principal principal) {
        LOG.log(Level.INFO, "Show home for " + principal.getName());
        model.addAttribute("offers", offerService.getAll());
        model.addAttribute("averagePrice", offerService.averagePrice());
        model.addAttribute("manualCount", offerService.getCountByTransmission(Offer.Transmission.Manual));
        model.addAttribute("automaticCount", offerService.getCountByTransmission(Offer.Transmission.Automatic));
        return "homePage";
    }
}
