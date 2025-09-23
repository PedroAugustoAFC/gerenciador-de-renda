package com.nassau.gerenciador_de_renda.expense.controller;

import com.nassau.gerenciador_de_renda.client.model.Client;
import com.nassau.gerenciador_de_renda.client.service.ClientService;
import com.nassau.gerenciador_de_renda.expense.dto.ExpenseFullDTO;
import com.nassau.gerenciador_de_renda.expense.model.Expense;
import com.nassau.gerenciador_de_renda.expense.service.ExpenseService;
import com.nassau.gerenciador_de_renda.expense.dto.ExpenseDTO;
import com.nassau.gerenciador_de_renda.security.JwtAuthFilter;
import com.nassau.gerenciador_de_renda.security.ValidateAccessService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/client/{id}/expenses")
public class ClientExpensesController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ValidateAccessService validateAccessService;

    @GetMapping
    public ResponseEntity<List<ExpenseFullDTO>> listAllExpenses(
            @PathVariable("id") Long clientId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate,
            @RequestParam(value = "category", required = false) String category,
            HttpServletRequest request) {

        validateAccessService.validateClientAccess(clientId, request);
        List<ExpenseFullDTO> expenses = expenseService.getFilteredExpensesByClient(clientId, startDate, endDate, category);
        return ResponseEntity.ok(expenses);
    }

    @PostMapping
    public ResponseEntity<ExpenseFullDTO> expensePost(@PathVariable("id") Long clientId, @Valid @RequestBody Expense expense,
                                                      HttpServletRequest request) {
        validateAccessService.validateClientAccess(clientId, request);
        expense.setClientId(clientId);
        ExpenseFullDTO createdExpense = expenseService.createExpense(expense);
        return ResponseEntity.ok(createdExpense);
    }
}
