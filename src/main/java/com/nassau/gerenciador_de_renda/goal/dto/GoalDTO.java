package com.nassau.gerenciador_de_renda.goal.dto;


import com.nassau.gerenciador_de_renda.goal.model.Goal;

public class GoalDTO {

    private Long id;
    private String description;
    private Double targetAmount;
    private String targetDate;
    private String dateCreated;
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
