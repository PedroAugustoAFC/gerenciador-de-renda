package com.nassau.gerenciador_de_renda.Client;

import com.nassau.gerenciador_de_renda.Expense.Expense;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

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
    private String name;

    @Column(nullable = false,unique = true)
    @NotBlank(message = "Email nao pode ser vazio")
    @Email(message = "Email invalido")
    private String email;

    //criptografa a senha
    private String password;

    //@Column(unique = true)
    private String cpf;

    @OneToMany(mappedBy = "client")
    private List<Expense> espense;

}
