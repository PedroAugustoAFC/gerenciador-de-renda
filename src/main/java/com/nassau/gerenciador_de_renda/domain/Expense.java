package com.nassau.gerenciador_de_renda.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Expense {

    private String id;
    private String description;
    private double value;
    private String date;
    private String category;
    private String paymentMethod;
    private String clientId;
    private String dateCreated;
    private String familyMemberName;
}
