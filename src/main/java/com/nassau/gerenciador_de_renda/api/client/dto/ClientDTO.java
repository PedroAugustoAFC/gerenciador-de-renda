package com.nassau.gerenciador_de_renda.api.client.dto;

import com.nassau.gerenciador_de_renda.api.client.model.Client;
import lombok.Data;

import java.util.UUID;

@Data
public class ClientDTO {

    private UUID id;
    private String name;
    private String email;

    public ClientDTO() {
    }

    public ClientDTO(Client entity){
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
    }

}
