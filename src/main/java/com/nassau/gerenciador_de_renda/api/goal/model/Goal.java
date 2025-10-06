package com.nassau.gerenciador_de_renda.api.goal.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.nassau.gerenciador_de_renda.api.client.model.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_goal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Goal {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty(access = Access.READ_ONLY)
    private UUID id;

    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s\\-]+$", message = "Profissão deve conter apenas letras")
    @Length(min = 3, message = "Profissão deve ter no mínimo 3 caracteres")
    private String description;

    @DecimalMin(value = "0.01", inclusive = true, message = "O valor alvo deve ser maior ou igual a 0,01")
    @Digits(integer = 15, fraction = 2, message = "O valor alvo deve ter no máximo 15 dígitos inteiros e 2 decimais")
    private BigDecimal targetAmount;

    @NotNull(message = "Data alvo não pode ser vazia")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate targetDate;

    @Column(nullable = false)
    @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime dateCreated;

    @Column(name = "client_id", nullable = false)
    @JsonProperty(access = Access.READ_ONLY)
    private UUID clientId;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    @JsonIgnore
    private Client client;

}
