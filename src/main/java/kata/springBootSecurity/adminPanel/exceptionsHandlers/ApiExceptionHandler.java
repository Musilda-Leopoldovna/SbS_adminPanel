package kata.springBootSecurity.adminPanel.exceptionsHandlers;

import jakarta.servlet.http.HttpServletRequest;
import kata.springBootSecurity.adminPanel.rest.controllers.ApiRestController;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice(assignableTypes = ApiRestController.class)
public class ApiExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonParseError(HttpServletRequest request, Exception ex) {
        return errorResponse(HttpStatus.BAD_REQUEST, "Некорректный формат тела запроса", request.getRequestURI(), ex.toString());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        Map<String, Object> body = new HashMap<>();
        body.put("status", HttpStatus.BAD_REQUEST.value());
        body.put("error", "Ошибка валидации");
        body.put("message", errors);
        body.put("path", request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
//        return errorResponse(HttpStatus.BAD_REQUEST, "Ошибка валидации", request.getRequestURI(), ex.getMessage());
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
