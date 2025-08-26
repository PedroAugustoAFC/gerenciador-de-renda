package com.nassau.gerenciador_de_renda.Client;

import com.nassau.gerenciador_de_renda.Expense.Expense;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    private String name;

    @Column(unique = true)
    private String email;

    //criptografa a senha
    private String password;

    //@Column(unique = true)
    private String cpf;

    @OneToMany(mappedBy = "client")
    private List<Expense> espense;

}
