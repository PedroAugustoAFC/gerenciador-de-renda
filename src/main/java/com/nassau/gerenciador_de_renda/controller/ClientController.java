package com.nassau.gerenciador_de_renda.controller;

import com.nassau.gerenciador_de_renda.domain.Client;
import com.nassau.gerenciador_de_renda.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @GetMapping
    public String cliente() {
        return clientService.cliente("Pedro");
    }

    @GetMapping("/{id}")
    public String clienteByID(@PathVariable("id") String id) {
        return clientService.cliente("Pedro");
    }

    @PostMapping
    public String clientePost(@RequestBody Client body){
        return "Hello World " + body.getName();
    }


}
