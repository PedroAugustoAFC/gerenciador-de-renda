package com.nassau.gerenciador_de_renda.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "tb_finance_info")
@Getter
@Setter
@AllArgsConstructor
public class FinanceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clientId;
    private String income;
    private String profission;
    private Double averageOfExpenses;
    private String civilStatus;
    private String dependents;
    private Double patrimony;

}
