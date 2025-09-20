package com.nassau.gerenciador_de_renda.revenue.model;

import com.nassau.gerenciador_de_renda.client.model.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Entity
@Data
@Table(name = "tb_revenue")
@AllArgsConstructor
@NoArgsConstructor
public class Revenue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    @Length(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String description;

    @Column(nullable = false)
    @Min(value = 1, message = "Valor deve ser maior que zero")
    private double amount;

    @Column(nullable = false)
    private String dateCreated;

    @Column(nullable = false)
    @NotBlank(message = "Data não pode ser vazia")
    private String datePaid;

    @Column(name = "client_id", nullable = false)
    private Long clientId;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
    private Client client;


}
