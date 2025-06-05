package kata.springBootSecurity.adminPanel.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import kata.springBootSecurity.adminPanel.controllers.ApiRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

@RestControllerAdvice(assignableTypes = ApiRestController.class)
public class ApiExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonParseError(HttpServletRequest request, Exception ex) {
        return errorResponse(HttpStatus.BAD_REQUEST, "Некорректный формат тела запроса", request.getRequestURI(), ex.toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        return errorResponse(HttpStatus.BAD_REQUEST, "Ошибка валидации", request.getRequestURI(), ex.toString());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleOther(Exception ex, HttpServletRequest request) {
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Ошибка сервера", request.getRequestURI(), ex.toString());
    }

    private ResponseEntity<Map<String, Object>> errorResponse(HttpStatus status, String message, String path, String ex) {
        Map<String, Object> body = Map.of(
                "timestamp", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy / kk:mm:ss")),
                "status", status.value(),
                "error", status.getReasonPhrase(),
                "message", message,
                "path", path,
                "errorClass" , ex
        );
        return ResponseEntity.status(status).body(body);
    }
}
