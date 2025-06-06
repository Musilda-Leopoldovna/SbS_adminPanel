package kata.springBootSecurity.adminPanel.mvc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import kata.springBootSecurity.adminPanel.database.entity.User;
import kata.springBootSecurity.adminPanel.mvc.services.UserService;

@Controller
public class AdminController {

    private final UserService service;

    public AdminController(UserService userService) {
        this.service = userService;
    }

    @GetMapping("/admin")
    public String mainPage(@ModelAttribute User user, Model model) {
        model.addAttribute("userList", service.getListOfUsers());
        model.addAttribute("roles", service.getListOfRoles());
        return "admin";
    }

    @PostMapping("/addUser")
    public String addRow(@ModelAttribute User user,
                         @RequestParam("roleIds") Long roleIds) {
        service.addNewUser(user, roleIds);
        return "redirect:/admin";
    }

    @PostMapping("/update")
    public String updateRow(@ModelAttribute User user,
                            @RequestParam(value = "updId") Long updId,
                            @RequestParam(name = "updRole", required = false) Long updRole){
        service.changeUser(user, updId, updRole);
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
