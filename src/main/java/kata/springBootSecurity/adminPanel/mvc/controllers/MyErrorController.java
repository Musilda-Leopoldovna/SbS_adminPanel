package kata.springBootSecurity.adminPanel.mvc.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyErrorController implements ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(MyErrorController.class);

    @RequestMapping("/error")
    public String handleAllError(HttpServletRequest request, Model model) {
        Object statusObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object uriObj = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);

        int statusCode = (statusObj instanceof Integer) ? (Integer) statusObj : 500;
        String uri = (uriObj != null) ? uriObj.toString() : "неизвестно";

        logger.error("Ошибка {} при обращении к ресурсу: {}", statusCode, uri);

        model.addAttribute("status", statusCode);
        model.addAttribute("uri", uri);

        if (statusCode == HttpStatus.NOT_FOUND.value()) {
            return "errors/404";
        } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
            return "errors/404";
        } else {
            return "errors/404";
        }
    }
}
