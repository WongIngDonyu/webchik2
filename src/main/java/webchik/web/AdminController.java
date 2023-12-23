package webchik.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import webchik.services.BrandService;
import webchik.services.ModelService;
import webchik.services.OfferService;
import webchik.services.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private OfferService offerService;
    private BrandService brandService;
    private ModelService modelService;
    private UserService userService;
    @Autowired
    public void setOfferService(OfferService offerService) {
        this.offerService = offerService;
    }
    @Autowired
    public void setBrandService(BrandService brandService) {
        this.brandService = brandService;
    }
    @Autowired
    public void setModelService(ModelService modelService) {
        this.modelService = modelService;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/panel")
    public String homePage(Model model) {
        model.addAttribute("offers", offerService.getAll());
        model.addAttribute("models", modelService.getAll());
        model.addAttribute("users", userService.getAll());
        model.addAttribute("brands", brandService.getAll());
        return "adminPanel";
    }
}
