package com.nassau.gerenciador_de_renda.expense.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.nassau.gerenciador_de_renda.client.model.Client;
import com.nassau.gerenciador_de_renda.expense.model.categoryEnum.ExpenseCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "tb_expense")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty(access = Access.READ_ONLY)
    private UUID id;

    @Column(length = 255)
    @Length(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String description;

    @Column(nullable = false)
    @Min(value = 1, message = "Valor deve ser maior que zero")
    private double amount;

    @Column(nullable = false)
    @JsonProperty(access = Access.READ_ONLY)
    private LocalDateTime dateCreated;

    @Column(nullable = false)
    @NotNull(message = "Data não pode ser vazia")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate datePaid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Categoria não pode ser vazia")
    private ExpenseCategory category;

    @Column(nullable = false)
    @NotBlank(message = "Método de pagamento não pode ser vazio")
    private String paymentMethod;

    @Column(nullable = false)
    @NotBlank(message = "Nome do membro da família não pode ser vazio")
    @Pattern(regexp = "^[A-Za-zÀ-ÿ\\s\\-]+$", message = "Nome deve conter apenas letras")
    @Length(min = 3, max = 25, message = "Nome deve ter no minimo 3")
    private String payer;

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

    @JsonSetter("category")
    public void setCategoryFromString(String categoryName) {
        this.category = ExpenseCategory.fromDisplayName(categoryName);
    }

    @JsonGetter("category")
    public String getCategoryDisplayName() {
        return this.category != null ? this.category.getDisplayName() : null;
    }

}
