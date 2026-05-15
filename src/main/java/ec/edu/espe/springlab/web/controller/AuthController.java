package ec.edu.espe.springlab.web.controller;

import ec.edu.espe.springlab.config.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtils jwtUtils;

    public AuthController(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        //Enviar token
        String token = jwtUtils.generateToken(credentials.get("username"));
        return ResponseEntity.ok(Map.of("token", token));
    }
}