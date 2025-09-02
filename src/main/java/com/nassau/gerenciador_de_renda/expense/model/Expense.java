package com.nassau.gerenciador_de_renda.expense.model;

import com.nassau.gerenciador_de_renda.client.model.Client;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "tb_expense")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

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
    @NotBlank(message = "Data não pode ser vazia")
    private String date;

    @Column(nullable = false)
    @NotBlank(message = "Categoria não pode ser vazia")
    private String category;

    @Column(nullable = false)
    @NotBlank(message = "Método de pagamento não pode ser vazio")
    private String paymentMethod;

    @Column(nullable = false)
    @NotBlank(message = "Nome do membro da família não pode ser vazio")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s\\-]+$", message = "Nome deve conter apenas letras")
    @Length(min = 3, max = 25, message = "Nome deve ter no minimo 3")
    private String familyMemberName;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client;

}
