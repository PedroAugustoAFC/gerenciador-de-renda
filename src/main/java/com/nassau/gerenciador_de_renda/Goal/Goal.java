package com.nassau.gerenciador_de_renda.Goal;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_goal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long clientId;
    private String description;
    private double targetAmount;
    private String targetDate;
    private String dateCreated;

}
