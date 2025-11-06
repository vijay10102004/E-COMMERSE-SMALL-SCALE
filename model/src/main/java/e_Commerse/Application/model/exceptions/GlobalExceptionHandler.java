package e_Commerse.Application.model.exceptions;



import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 🔹 Handle resource not found (custom)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleResourceNotFound(ResourceNotFoundException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request.getDescription(false));
    }

    // 🔹 Handle unauthorized or forbidden access
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex, WebRequest request) {
        return buildResponse("Access Denied: " + ex.getMessage(), HttpStatus.FORBIDDEN, request.getDescription(false));
    }

    // 🔹 Handle invalid arguments (like bad request DTOs)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex, WebRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation failed");
        return buildResponse(message, HttpStatus.BAD_REQUEST, request.getDescription(false));
    }

    // 🔹 Handle generic runtime exceptions (fallback)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntime(RuntimeException ex, WebRequest request) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getDescription(false));
    }

    // 🔹 Handle all other unexpected exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobal(Exception ex, WebRequest request) {
        return buildResponse("Internal Server Error: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, request.getDescription(false));
    }

    // 🧱 Utility to build consistent error responses
    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status, String path) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        body.put("path", path);
        return new ResponseEntity<>(body, status);
    }
}

