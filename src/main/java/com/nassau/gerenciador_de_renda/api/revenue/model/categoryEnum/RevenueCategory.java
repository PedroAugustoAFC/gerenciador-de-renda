package com.nassau.gerenciador_de_renda.api.revenue.model.categoryEnum;

public enum RevenueCategory {
    SALARIO("Salário"),
    AUTONOMO("Autônomo"),
    MEI("MEI"),
    COMISSOES_BONUS("Comissões / Bônus"),
    HORAS_EXTRAS("Horas extras"),
    ALUGUEIS_IMOVEIS("Aluguéis de imóveis"),
    DIVIDENDOS_ACOES("Dividendos de ações"),
    JUROS_RENDA_FIXA("Juros de renda fixa"),
    ROYALTIES("Royalties / direitos autorais"),
    LICENCIAMENTO("Licenciamento de marcas ou produtos"),
    DECIMO_TERCEIRO("13º salário"),
    FERIAS("Férias"),
    RESTITUICAO_IR("Restituição de imposto de renda"),
    HERANCAS("Heranças"),
    INDENIZACOES("Indenizações"),
    VENDA_BENS("Venda de bens"),
    OUTROS("Outros");

    private final String displayName;

    RevenueCategory(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static RevenueCategory fromDisplayName(String displayName) {
        for (RevenueCategory category : values()) {
            if (category.displayName.equals(displayName)) {
                return category;
            }
        }
        throw new IllegalArgumentException("Categoria inválida: " + displayName);
    }
}
