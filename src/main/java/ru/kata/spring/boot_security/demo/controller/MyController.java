package ru.kata.spring.boot_security.demo.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;

@Controller
public class MyController {

    private UserService userService;

    public MyController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/admin")
    public String showAllUsers(Model model) {

        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "admin";
    }
    @GetMapping("/user")
    public String showUser(Model model, Principal principal) {

        String name = principal.getName();
        User user = userService.findByName(name);
        model.addAttribute("user", user);
        return "user";
    }
    @GetMapping("/addNewUser")
    public String addNewUser(Model model) {

        User user = new User();
        model.addAttribute("user", user);

        return "edit";

    }
    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user" )  User user ) {
        userService.saveUser(user);
        return "redirect:/admin";
    }
    @GetMapping("/updateInfo")
    public String updateEmployee(@RequestParam("userId") int id, Model model) {

        User user = userService.getUser(id);
        model.addAttribute("user", user);

        return "edit";
    }
    @GetMapping("/deleteUser")
    public String deleteEmployee(@RequestParam("userId") int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
