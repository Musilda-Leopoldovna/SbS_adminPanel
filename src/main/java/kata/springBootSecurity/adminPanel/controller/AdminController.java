package kata.springBootSecurity.adminPanel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import kata.springBootSecurity.adminPanel.entity.User;
import kata.springBootSecurity.adminPanel.service.UserService;

@Controller
public class AdminController {

    private final UserService service;

    public AdminController(UserService userService) {
        this.service = userService;
    }

    @GetMapping("/admin")
    public String mainPage(@ModelAttribute User user, Model model) {
        model.addAttribute("userList", service.getListOfUsers());
        return "admin";
    }

    @PostMapping("/addUser")
    public String addRow(@ModelAttribute User user) {
        service.addNewUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateRow(@ModelAttribute User user){
        service.changeUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteRow(@RequestParam(value = "deleteId") Long deleteId) {
        if (deleteId != null) {
            service.removeUserByID(deleteId);
        }
        return "redirect:/admin";
    }
}
