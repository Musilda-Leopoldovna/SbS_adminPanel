package kata.springBootSecurity.adminPanel.exceptionsHandlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

public class RestAccessDeniedHandler implements AccessDeniedHandler {

    private static final Logger logger = LoggerFactory.getLogger(RestAccessDeniedHandler.class);

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write("""
                                    {
                                        "error": "Forbidden",
                                        "message": "Нет прав доступа к этому ресурсу"
                                    }
                                    """);

        Authentication currentAuthUser = SecurityContextHolder.getContext().getAuthentication();
        String username = (currentAuthUser != null && currentAuthUser.isAuthenticated())
                ? currentAuthUser.getName()
                : "anonymous";

//        // второй способ (не всегда корректно работает при сложных конфигурациях прокси и фильтров)
//        Principal principal = request.getUserPrincipal();
//        String username = (principal != null) ? principal.getName() : "anonymous";

        logger.warn("Ошибка прав доступа: {} --- Доступ пользователя {} к ресурсу \"{}\" запрещён.",
                accessDeniedException.getMessage(), username, request.getRequestURI());
    }
}
