package com.nassau.gerenciador_de_renda.Client;

import com.nassau.gerenciador_de_renda.Expense.Expense;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tb_client")
@Getter
@Setter
@AllArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String password;
    private String cpf;

    @OneToMany(mappedBy = "client")
    private List<Expense> espense;

}
