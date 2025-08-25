package com.nassau.gerenciador_de_renda.Client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping("/{id}")
    public Client clientByID(@PathVariable("id") Long id) {
        return clientService.getClientById(id);
    }

    @PostMapping("save")
    public Client clientPost(@RequestBody Client client){
        return clientService.createClient(client);
    }

    @DeleteMapping("delete/{id}")
    public void clientDelete(@PathVariable("id") Long id) {
        clientService.deleteClient(id);
    }


}
