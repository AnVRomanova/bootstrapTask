package com.example31._Spring_Security.controller;


import com.example31._Spring_Security.model.User;
import com.example31._Spring_Security.service.RoleService;
import com.example31._Spring_Security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/")
public class AdminsController {
    private final UserService service;
    private final RoleService roleService;

    @Autowired
    public AdminsController(UserService service, RoleService roleService) {
        this.service = service;
        this.roleService = roleService;

    }

    @GetMapping
    public String homePage() {
        return "home";
    }

    @GetMapping("/hello")
    public String helloUserPage() {
        return "hello";
    }

    @GetMapping("/read_profile")
    public String profilePage(Model model, Principal principal) {
        User user = service.findByName(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @GetMapping("/only_for_admins")
    public String adminsPage(Model model, Principal principal) {
        List<User> usersList = service.listUsers();
        model.addAttribute("allRoles", roleService.listRoles());
        model.addAttribute("newUser", new User());
        model.addAttribute("admin", service.findByName(principal.getName()));
        model.addAttribute("userList", usersList);
        return "adminsPage";
    }

    // Edit user
    @PatchMapping("only_for_admins/{id}")
    public String editUser(@ModelAttribute("user") User user, @PathVariable("id") long id, Model model) {
        model.addAttribute("user", service.readUser(id));
        model.addAttribute("allRoles", roleService.listRoles());
        service.edit(user);
        return "redirect:/only_for_admins";
    }

    //delete users
    @DeleteMapping("only_for_admins/{id}")
    public String deletePage(@PathVariable("id") long id) {
        service.delete(id);
        return "redirect:/only_for_admins";
    }

    // Add new user
    @PostMapping("only_for_admins/add")
    public String create(@ModelAttribute("user") User user) {
        service.add(user);
        return "redirect:/only_for_admins";
    }

}
