package com.nassau.gerenciador_de_renda.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_expense")
@Getter
@Setter
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clientId;
    private String description;
    private double value;
    private String date;
    private String category;
    private String paymentMethod;
    private String dateCreated;
    private String familyMemberName;

}
