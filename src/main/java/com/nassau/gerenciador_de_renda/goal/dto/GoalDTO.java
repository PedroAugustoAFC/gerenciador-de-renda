package com.nassau.gerenciador_de_renda.goal.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.nassau.gerenciador_de_renda.goal.model.Goal;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class GoalDTO {

    private Long id;
    private String description;
    private Double targetAmount;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate targetDate;
    private LocalDateTime dateCreated;
    private Long clientId;


    public GoalDTO(Goal entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.targetAmount = entity.getTargetAmount();
        this.targetDate = entity.getTargetDate();
        this.dateCreated = entity.getDateCreated();
        this.clientId = entity.getClientId();
    }

    public GoalDTO() {}
}
