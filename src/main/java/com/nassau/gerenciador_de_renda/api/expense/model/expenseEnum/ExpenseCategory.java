package com.nassau.gerenciador_de_renda.api.expense.model.expenseEnum;

public enum ExpenseCategory {
    NENHUM("Nenhum"),
    MORADIA("Moradia"),
    SERVICOS_BASICOS("Serviços básicos"),
    ALIMENTACAO("Alimentação"),
    SAUDE("Saúde"),
    TRANSPORTE("Transporte"),
    EDUCACAO("Educação"),
    SEGUROS_ESSENCIAIS("Seguros essenciais"),
    COMUNICACAO_BASICA("Comunicação básica"),
    IMPOSTOS_TAXAS("Impostos e taxas obrigatórias"),
    LAZER("Lazer"),
    ASSINATURAS_SERVICOS("Assinaturas e serviços extras"),
    ROUPAS_ACESSORIOS("Roupas e acessórios"),
    BELEZA_CUIDADOS("Beleza e cuidados pessoais"),
    TECNOLOGIA_GADGETS("Tecnologia e gadgets"),
    HOBBIES_ESPORTES("Hobbies e esportes"),
    PRESENTES("Presentes"),
    OUTROS("Outros");

    private final String displayName;

    ExpenseCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ExpenseCategory fromDisplayName(String displayName) {
        for (ExpenseCategory category : values()) {
            if (category.displayName.equals(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Categoria inválida: " + displayName);
    }
}
