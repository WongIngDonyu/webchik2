package webchik.web;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import webchik.models.User;
import webchik.services.UserRoleService;
import webchik.services.UserService;
import webchik.services.dtos.AddUserDto;
import webchik.services.dtos.ShowOfferInfoDto;
import webchik.services.dtos.ShowUserInfoDto;
import webchik.services.dtos.UserDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private UserRoleService userRoleService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    @Autowired
    public void setUserRoleService(UserRoleService userRoleService) {
        this.userRoleService = userRoleService;
    }
    @GetMapping("/all")
    public String viewAllUsers(Model model){
        List<ShowUserInfoDto> users = userService.getAll();
        model.addAttribute("users", users);
        return "allUsers";
    }

    @GetMapping("/find/{id}")
    public String findUser(Model model, @PathVariable("id") UUID uuid){
        Optional<UserDto> dbUser = userService.findUser(uuid);
        if(dbUser.isPresent()){
            UserDto userDto = dbUser.get();
            model.addAttribute("userDto", userDto);
            return "findUser";
        }
        else {
            return "userNotFound";
        }
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
        dbUser.ifPresent(user -> model.addAttribute("user", user));
        model.addAttribute("roles", userRoleService.getAll());
        return "addNewUser2";

    }
    @PostMapping("/change/{id}")
    public String saveChangeUser(@PathVariable("id") UUID id, @ModelAttribute  ShowUserInfoDto user) {
        userService.update(user);
        return "redirect:/admin/panel";
    }
}
