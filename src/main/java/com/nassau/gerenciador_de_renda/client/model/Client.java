package com.nassau.gerenciador_de_renda.client.model;

import com.nassau.gerenciador_de_renda.expense.model.Expense;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import java.util.List;

@Entity
@Table(name = "tb_client")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Length(min = 3,max = 60,message = "Nome deve ter no minimo 3")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s\\-]+$", message = "Nome deve conter apenas letras")
    @NotBlank(message = "Nome não pode ser vazio")
    private String name;

    @Column(nullable = false,unique = true)
    @NotBlank(message = "Email não pode ser vazio")
    @Email(message = "Email invalido")
    private String email;

    @Column(nullable = false)
    @NotBlank(message = "Senha não pode ser vazia")
    @Length(min = 8,message = "Senha deve ter no minimo 8 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[^A-Za-z\\d]).+$",
            message = "A senha deve conter pelo menos uma letra, um número e um símbolo"
    )
    private String password;

    @Column(nullable = false,unique = true)
    @NotBlank(message = "CPF não pode ser vazio")
    @Length(min = 11,max = 11,message = "CPF deve ter 11 digitos")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter apenas numeros")
    @CPF(message = "CPF invalido")
    private String cpf;

    @OneToMany(mappedBy = "client")
    private List<Expense> expenses;

}
