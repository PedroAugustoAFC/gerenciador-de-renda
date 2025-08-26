package com.nassau.gerenciador_de_renda.Client;

import com.nassau.gerenciador_de_renda.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    public ResponseEntity<?> clientByID(@PathVariable("id") Long id) {
            Client client = clientService.getClientById(id);
            return ResponseEntity.ok(client);
    }

    @PostMapping("/save")
    public Client clientPost(@RequestBody Client client){
        return clientService.createClient(client);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> clientDelete(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

}
