package com.nassau.gerenciador_de_renda.api.expense.model.expenseEnum;

public enum ExpensePaymentMethod {
    DINHEIRO("Dinheiro"),
    DEBITO("Débito"),
    CREDITO_VISTA("Crédito à vista"),
    CREDITO_PARCELADO("Crédito parcelado"),
    PIX("PIX"),
    BOLETO("Boleto"),
    VALE_REFEICAO("Vale refeição"),
    VALE_ALIMENTACAO("Vale alimentação"),
    CHEQUE("Cheque"),
    DEBITO_AUTOMATICO("Débito automático"),
    TRANSFERENCIA_BANCARIA("Transferência bancária"),
    CRIPTOMOEDA("Criptomoeda"),
    OUTROS("Outros");

    private final String displayName;

    ExpensePaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static ExpensePaymentMethod fromDisplayName(String displayName) {
        for (ExpensePaymentMethod method : values()) {
            if (method.displayName.equals(displayName)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Método de pagamento inválido: " + displayName);
    }
}