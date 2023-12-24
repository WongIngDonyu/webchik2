package webchik.web;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webchik.services.UserRoleService;
import webchik.services.UserService;
import webchik.services.dtos.*;

import java.util.Optional;
import java.util.UUID;
@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private UserRoleService userRoleService;
    private final ModelMapper modelMapper;
    private static final Logger LOG = LogManager.getLogger(Controller.class);

    public UserController(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }

    @PostMapping("/deActivation/{id}")
    public String deleteUser(@PathVariable("id") UUID id){
        userService.deActivation(id);
        return "redirect:/admin/panel";
    }

    @PostMapping("/activation/{id}")
    public String activationUser(@PathVariable("id") UUID id){
        userService.activation(id);
        return "redirect:/admin/panel";
    }

    @GetMapping("/create")
    public String addNewUser(Model model){
        model.addAttribute("roles", userRoleService.getAll());
        return "addNewUser";
    }

    @ModelAttribute("addUserDto")
    public AddUserDto initUser(){
        return new AddUserDto();
    }

    @PostMapping("/create")
    public String addNewUser(@Valid AddUserDto addUserDto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addUserDto", addUserDto);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addUserDto", bindingResult);
            return "redirect:/user/create";
        }
        userService.add(addUserDto);
        return "redirect:/admin/panel";
    }
    @GetMapping("/change/{id}")
    public String changeUser(Model model, @PathVariable("id") UUID id){
        Optional<ShowUserInfoDto> dbUser = userService.findUser(id);
        model.addAttribute("addUser", modelMapper.map(dbUser, AddUserDto.class));
        model.addAttribute("roles", userRoleService.getAll());
        return "changeUser";

    }
    @PostMapping("/change/{id}")
    public String saveChangeUser(@Valid  AddUserDto addUser, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("addUser", addUser);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.addUser", bindingResult);
            return "redirect:/user/change/{id}";
        }
        userService.update(addUser);
        return "redirect:/admin/panel";
    }
}
