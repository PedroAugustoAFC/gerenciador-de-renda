package com.nassau.gerenciador_de_renda.financeInfo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.nassau.gerenciador_de_renda.client.model.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_finance_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty(access = Access.READ_ONLY)
    private UUID id;

    @NotNull(message = "Renda não pode ser nula")
    @DecimalMin(value = "0.01", inclusive = true, message = "Renda deve ser maior ou igual a 0,01")
    private Double income;

    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s\\-]+$", message = "Profissão deve conter apenas letras")
    @Length(min = 3, message = "Profissão deve ter no mínimo 3 caracteres")
    private String profission;

    @NotNull(message = "Patrimônio líquido não pode ser nulo")
    @DecimalMin(value = "0.01", inclusive = true, message = "Patrimônio líquido deve ser maior ou igual a 0,01")
    private Double netWorth;

    @Column(name = "client_id", nullable = false)
    @JsonProperty(access = Access.READ_ONLY)
    private UUID clientId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    @JsonIgnore
    private Client client;

}
