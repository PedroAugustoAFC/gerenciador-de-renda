package com.nassau.gerenciador_de_renda.expense;

import com.nassau.gerenciador_de_renda.client.Client;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_expense")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private double amount;
    private String date;
    private String category;
    private String paymentMethod;
    private String dateCreated;
    private String familyMemberName;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
