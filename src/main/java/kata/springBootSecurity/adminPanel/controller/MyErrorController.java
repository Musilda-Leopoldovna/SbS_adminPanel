package ru.kata.spring.boot_security.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        if (request.getAttribute("javax.servlet.error.status_code") != null) {
            return "forward:/error";
        }
        return "errors/404";
    }
}
