package ru.kata.spring.boot_security.demo.controller;




import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class MyController {

    private final UserService userService;
    private final RoleService roleService;

    public MyController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }
    @GetMapping("/login")
    public String login() {
        return "login";
    }


    @GetMapping("/admin")
    public String adminView(Model model, Authentication authentication) {

        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String login = authentication.getName();
        User authUser = userService.findByName(login);
        model.addAttribute("authEmail", authUser.getEmail());
        String roles = authentication.getAuthorities().stream()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .collect(Collectors.joining(" "));
        model.addAttribute("authRoles", roles);
        return "admin";
    }
    @GetMapping("/user")
    public String showUser(Model model, @AuthenticationPrincipal User authUser) {
        model.addAttribute("user", authUser);
        return "user";
    }
    @PostMapping("/admin/add")
    public String addNewUser(@ModelAttribute("newUser") User user,
                             @RequestParam("roleIds") List<Long> roleIds,
                             @RequestParam("password") String password) {

        user.setRoles((Set<Role>) roleService.findByIds(roleIds));
        user.setPassword(password);
        userService.saveUser(user);

        return "redirect:/admin";

    }
    @PostMapping("/admin/edit")
    public String saveUser(
            @RequestParam int id,
            @RequestParam String name,
            @RequestParam String surname,
            @RequestParam Integer age,
            @RequestParam String email,
            @RequestParam(required = false) String password,
            @RequestParam(name = "roleIds") List<Long> roleIds
    ) {
        userService.updateUser(id, name, surname, age, email, password, roleIds);
        return "redirect:/admin";
    }
    @GetMapping("/admin/edit/{id}")
    public String editUser(@PathVariable int id, Model model) {
        fillAdminModel(model);
        model.addAttribute("editUser", userService.getUser(id));
        return "admin";
    }
    private void fillAdminModel(Model model) {
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("allUsers", userService.getAllUsers());
        model.addAttribute("newUser", new User());
    }
    @GetMapping("/admin/delete/{id}")
    public String showDeleteModal(@PathVariable int id, Model model) {
        fillAdminModel(model);
        model.addAttribute("deleteUser", userService.getUser(id));
        return "admin";
    }
    @PostMapping("/admin/delete")
    public String deleteUser(@RequestParam int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
