package com.nassau.gerenciador_de_renda.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Goal {

    private Long id;
    private String description;
    private double targetAmount;
    private String targetDate;
    private String dateCreated;
    private String clientId;

}
