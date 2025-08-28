package com.nassau.gerenciador_de_renda.client;

import lombok.Data;

@Data
public class ClientDTO {

    private Long id;
    private String name;
    private String email;
    private String cpf;

    public ClientDTO() {
    }

    public ClientDTO(Client entity){
        id = entity.getId();
        name = entity.getName();
        email = entity.getEmail();
        cpf = entity.getCpf();
    }

}
