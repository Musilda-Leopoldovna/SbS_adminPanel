package ru.kata.spring.boot_security.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.entity.User;
import ru.kata.spring.boot_security.service.UserService;

@Controller
public class AdminController {

    private final UserService service;
    public AdminController(UserService userService) {
        this.service = userService;
    }

    @GetMapping("/admin")
    public String mainPage(@ModelAttribute User user, Model model,
                           @RequestParam(value = "editId", required = false) Long editId) {
        if (editId != null) {
            User editUser = service.getUserByID(editId);
            model.addAttribute("editUser", editUser);
        }
        model.addAttribute("userList", service.getListOfUsers());
        return "admin";
    }

    @PostMapping("/admin/addUser")
    public String addRow(@ModelAttribute User user) {
        service.addNewUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/update")
    public String updateRow(@ModelAttribute User user) {
        service.changeUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/admin/delete")
    public String deleteRow(@RequestParam(value = "deleteId") Long deleteId) {
        if (deleteId != null) {
            service.removeUserByID(deleteId);
        }
        return "redirect:/admin";
    }
}
