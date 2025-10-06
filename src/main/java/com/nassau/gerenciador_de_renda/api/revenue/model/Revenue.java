package com.nassau.gerenciador_de_renda.api.revenue.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.nassau.gerenciador_de_renda.api.client.model.Client;
import com.nassau.gerenciador_de_renda.api.revenue.model.categoryEnum.RevenueCategory;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
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
    @GeneratedValue(strategy = GenerationType.UUID)
    @JsonProperty(access = Access.READ_ONLY)
    private UUID id;

    @Column(length = 255)
    @Length(max = 255, message = "Descrição deve ter no máximo 255 caracteres")
    private String description;

    @Column(nullable = false)
    @DecimalMin(value = "0.01", inclusive = true, message = "O valor deve ser maior ou igual a 0,01")
    @Digits(integer = 15, fraction = 2, message = "O valor deve ter no máximo 15 dígitos inteiros e 2 decimais")
    private BigDecimal amount;

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
    private RevenueCategory category;

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
        this.category = RevenueCategory.fromDisplayName(categoryName);
    }

    @JsonGetter("category")
    public String getCategoryDisplayName() {
        return this.category != null ? this.category.getDisplayName() : null;
    }

}
