package com.nassau.gerenciador_de_renda.client.controller;

import com.nassau.gerenciador_de_renda.client.dto.ClientUpdateDTO;
import com.nassau.gerenciador_de_renda.client.model.Client;
import com.nassau.gerenciador_de_renda.client.service.ClientService;
import com.nassau.gerenciador_de_renda.client.dto.ClientDTO;
import com.nassau.gerenciador_de_renda.security.ValidateAccessService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ValidateAccessService validateAccessService;

    @GetMapping("/{id}")
    public ResponseEntity<?> clientByID(@PathVariable("id") Long id, HttpServletRequest request) {
        validateAccessService.validateClientAccess(id, request);
            ClientDTO client = clientService.getClientById(id);
            return ResponseEntity.ok(client);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ClientDTO> clientUpdate(@PathVariable("id") Long id,
                                                  @Valid @RequestBody ClientUpdateDTO clientUpdateDTO,
                                                  HttpServletRequest request) {
        validateAccessService.validateClientAccess(id, request);
        ClientDTO updatedClient = clientService.updateClient(id, clientUpdateDTO);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> clientDelete(@PathVariable("id") Long id, HttpServletRequest request) {
        validateAccessService.validateClientAccess(id, request);
        clientService.deleteClient(id);
        return ResponseEntity.noContent().build();
    }

}
