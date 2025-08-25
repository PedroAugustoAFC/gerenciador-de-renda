package com.nassau.gerenciador_de_renda.FinanceInfo;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "tb_finance_info")
@Data
@NoArgsConstructor
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
    private List<String> dependents;
    private Double patrimony;

}
