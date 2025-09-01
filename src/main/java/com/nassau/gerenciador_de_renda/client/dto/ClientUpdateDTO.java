package com.nassau.gerenciador_de_renda.client.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ClientUpdateDTO {

    @Length(min = 3,max = 60,message = "Nome deve ter no minimo 3")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s\\-]+$", message = "Nome deve conter apenas letras")
    private String name;

    @Email(message = "Email invalido")
    private String email;

    @Length(min = 8,message = "Senha deve ter no minimo 8 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d]).+$",
            message = "A senha deve conter pelo menos uma letra, um número e um símbolo"
    )
    private String password;
}
