package com.nassau.gerenciador_de_renda.api.goal.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.nassau.gerenciador_de_renda.api.goal.model.Goal;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class GoalDTO {

    private UUID id;
    private String description;
    private BigDecimal targetAmount;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate targetDate;
    private LocalDateTime dateCreated;
    private UUID clientId;

    public GoalDTO() {}

    public GoalDTO(Goal entity) {
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.targetAmount = entity.getTargetAmount();
        this.targetDate = entity.getTargetDate();
        this.dateCreated = entity.getDateCreated();
        this.clientId = entity.getClientId();
    }

}
