package com.nassau.gerenciador_de_renda.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Client {

    private String id;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String cpf;

}
