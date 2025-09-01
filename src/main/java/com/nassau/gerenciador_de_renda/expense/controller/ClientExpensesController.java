package com.nassau.gerenciador_de_renda.expense.controller;

import com.nassau.gerenciador_de_renda.expense.service.ExpenseService;
import com.nassau.gerenciador_de_renda.expense.dto.ExpenseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/client/expenses")
public class ClientExpensesController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> listAllExpenses() {
        List<ExpenseDTO> expenses = expenseService.getAllExpenses();
        return ResponseEntity.ok(expenses);
    }
}
