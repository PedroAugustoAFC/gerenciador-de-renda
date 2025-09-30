package com.nassau.gerenciador_de_renda.revenue.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import com.nassau.gerenciador_de_renda.client.model.Client;
import com.nassau.gerenciador_de_renda.expense.model.categoryEnum.ExpenseCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "tb_revenue")
@AllArgsConstructor
@NoArgsConstructor
public class Revenue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;

    @Column(length = 255)
    @Length(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String description;

    @Column(nullable = false)
    @Min(value = 1, message = "Valor deve ser maior que zero")
    private double amount;

    @Column(nullable = false)
    private LocalDateTime dateCreated;

    @Column(nullable = false)
    @NotNull(message = "Data não pode ser vazia")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate datePaid;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Categoria não pode ser vazia")
    private ExpenseCategory category;

    @Column(name = "client_id", nullable = false)
    private UUID clientId;

    @ManyToOne(
            fetch = FetchType.LAZY,
            optional = false
    )
    @JoinColumn(name = "client_id", insertable = false, updatable = false)
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
