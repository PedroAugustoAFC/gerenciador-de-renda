package com.nassau.gerenciador_de_renda.api.expense.controller;

import com.nassau.gerenciador_de_renda.api.expense.dto.ExpenseDTO;
import com.nassau.gerenciador_de_renda.api.expense.model.Expense;
import com.nassau.gerenciador_de_renda.api.expense.service.ExpenseService;
import com.nassau.gerenciador_de_renda.api.financeInfo.service.FinanceInfoService;
import com.nassau.gerenciador_de_renda.api.security.ValidateAccessService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client/{id}/expenses")
public class ClientExpensesController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private FinanceInfoService financeInfoService;

    @Autowired
    private ValidateAccessService validateAccessService;

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> listAllExpenses(
            @PathVariable("id") UUID clientId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate,
            @RequestParam(value = "category", required = false) String category,
            HttpServletRequest request) {

        validateAccessService.validateClientAccess(clientId, request);
        List<ExpenseDTO> expenses = expenseService.getFilteredExpensesByClient(clientId, startDate, endDate, category);
        return ResponseEntity.ok(expenses);
    }

    @PostMapping
    public ResponseEntity<ExpenseDTO> expensePost(@PathVariable("id") UUID clientId, @Valid @RequestBody Expense expense,
                                                  HttpServletRequest request) {
        validateAccessService.validateClientAccess(clientId, request);

        BigDecimal expenseValue = expense.getAmount();
        financeInfoService.updateNetWorthAfterExpense(clientId, expenseValue);

        expense.setClientId(clientId);
        ExpenseDTO createdExpense = expenseService.createExpense(expense);
        return ResponseEntity.ok(createdExpense);
    }
}
