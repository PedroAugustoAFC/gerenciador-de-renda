package com.nassau.gerenciador_de_renda.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FinanceInfo {

    private Long id;
    private String clientId;
    private String income;
    private String profission;
    private String averageOfExpenses;
    private String civilStatus;
    private String dependents;
    private Double heritage;


}
