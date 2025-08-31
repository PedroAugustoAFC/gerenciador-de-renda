package com.nassau.gerenciador_de_renda.client.controller;

import com.nassau.gerenciador_de_renda.client.dto.ClientUpdateDTO;
import com.nassau.gerenciador_de_renda.client.model.Client;
import com.nassau.gerenciador_de_renda.client.service.ClientService;
import com.nassau.gerenciador_de_renda.client.dto.ClientDTO;
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

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDTO> clientUpdate(@PathVariable("id") Long id,@Valid @RequestBody ClientUpdateDTO clientUpdateDTO) {
        ClientDTO updatedClient = clientService.updateClient(id, clientUpdateDTO);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> clientDelete(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

}
