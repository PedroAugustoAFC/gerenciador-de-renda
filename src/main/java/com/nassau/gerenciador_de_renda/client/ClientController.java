package com.nassau.gerenciador_de_renda.client;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<?> clientByID(@PathVariable("id") Long id) {
            ClientDTO client = clientService.getClientById(id);
            return ResponseEntity.ok(client);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> clientDelete(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

}
