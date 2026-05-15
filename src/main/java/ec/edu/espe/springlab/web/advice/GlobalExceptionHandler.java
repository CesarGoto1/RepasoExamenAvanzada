package ec.edu.espe.springlab.web.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import java.time.LocalDateTime;
import java.time.LocalDate;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
@ControllerAdvice
public class GlobalExceptionHandler {
    //404 de negocio
    @ExceptionHandler (NotFoundException.class)
    public ResponseEntity<?> handleNotFound(NotFoundException ex) {
        return error(HttpStatus.NOT_FOUND, ex.getMessage());
    }
    //409 (duplicados, etc.)
    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<?> handleConflict(ConflictException ex){
        return error(HttpStatus.CONFLICT, ex.getMessage());
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException ex){
        Map<String, String> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now().toString());
        body.put("status", String.valueOf(HttpStatus.BAD_REQUEST.value()));

        Map<String, String> errors = new HashMap<>();
        for(FieldError fe : ex.getBindingResult().getFieldErrors()){
            errors.put(fe.getField(),fe.getDefaultMessage());
        }
        body.put("errors", errors.toString());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
    }
    //Construir un mensaje estandar de error  tipo JSON
    private ResponseEntity<Map<String,Object>> error(HttpStatus status, String message){
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("status", status.value());
        body.put("error", status.getReasonPhrase());
        body.put("message", message);
        return ResponseEntity.status(status).body(body);
    }
}
