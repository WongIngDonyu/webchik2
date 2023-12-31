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
        List<UserDto> users = userService.getAll();
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

    @PostMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") UUID uuid){
        userService.delete(uuid);
        return "redirect:/user/all";
    }

    @PostMapping("/activation/{id}")
    public String activationUser(@PathVariable("id") UUID uuid){
        userService.activation(uuid);
        return "redirect:/user/all";
    }

    @GetMapping("/create")
    public String addNewUser(Model model){
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
        return "redirect:/user/all";
    }
    @GetMapping("/change/{id}")
    public String changeUser(Model model, @PathVariable("id") UUID uuid){
        Optional<UserDto> dbUser = userService.findUser(uuid);
        if (dbUser.isPresent()) {
            UserDto userDto = dbUser.get();
            model.addAttribute("userDto", userDto);
            return "editUser";
        } else {
            return "userNotFound";
        }
    }
    @PostMapping("/change/{id}")
    public String saveChangeUser(@PathVariable("id") UUID uuid, @ModelAttribute UserDto userDto) {
        Optional<User> dbUser = userService.findUser(uuid);
        if (dbUser.isPresent()) {
            userService.update(userDto);
            return "redirect:/user/all";
        } else {
            return "userNotFound";
        }
    }

}
