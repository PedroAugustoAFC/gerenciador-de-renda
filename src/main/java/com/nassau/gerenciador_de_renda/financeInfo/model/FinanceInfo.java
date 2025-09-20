package com.nassau.gerenciador_de_renda.financeInfo.model;

import com.nassau.gerenciador_de_renda.client.model.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Entity
@Table(name = "tb_finance_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinanceInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "Renda deve ser maior ou igual a zero")
    private Double income;

    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s\\-]+$", message = "Profissão deve conter apenas letras")
    @Length(min = 3, message = "Profissão deve ter no mínimo 3 caracteres")
    private String profission;

    private List<String> dependents;

    @Min(value = 1, message = "O patrimônio líquido deve ser maior ou igual a zero")
    private Double netWorth;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;

}
