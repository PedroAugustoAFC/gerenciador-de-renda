package com.nassau.gerenciador_de_renda.goal.model;

import com.nassau.gerenciador_de_renda.client.model.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "tb_goal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s\\-]+$", message = "Profissão deve conter apenas letras")
    @Length(min = 3, message = "Profissão deve ter no mínimo 3 caracteres")
    private String description;

    @Min(value = 1, message = "O patrimônio líquido deve ser maior ou igual a zero")
    @Pattern(regexp = "^[0-9]+(\\.[0-9]{1,2})?$", message = "O valor alvo deve ser um número válido com até duas casas decimais")
    private double targetAmount;

    @NotBlank(message = "Data alvo não pode ser vazia")
    private String targetDate;

    @NotBlank(message = "Data alvo não pode ser vazia")
    private String dateCreated;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;

}
