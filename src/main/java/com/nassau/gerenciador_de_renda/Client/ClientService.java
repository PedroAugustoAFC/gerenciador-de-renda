package com.nassau.gerenciador_de_renda.Client;

import org.springframework.stereotype.Service;

@Service
public class ClientService {

    public String cliente(String name){
        return "Hello World " + name;
    }
}
