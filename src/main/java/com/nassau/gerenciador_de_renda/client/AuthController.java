package com.nassau.gerenciador_de_renda.client;

import com.nassau.gerenciador_de_renda.security.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/register")
    public ClientDTO clientPost(@Valid @RequestBody Client client){
        return clientService.createClient(client);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        Client client = clientService.getClientByEmail(request.get("email"));
        if(client != null && client.getPassword().equals(request.get("password"))){
            String token = JwtUtil.generateToken((client.getEmail()));
            return ResponseEntity.ok(Map.of("token", token));
        }
        return ResponseEntity.status(401).body("credenciais invalidas");
    }
}
