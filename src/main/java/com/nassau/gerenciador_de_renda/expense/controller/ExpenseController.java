package com.nassau.gerenciador_de_renda.expense.controller;

import com.nassau.gerenciador_de_renda.expense.service.ExpenseService;
import com.nassau.gerenciador_de_renda.expense.dto.ExpenseDTO;
import com.nassau.gerenciador_de_renda.expense.model.Expense;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expense")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/{id}")
    public ResponseEntity<?> expenseById(@PathVariable("id") Long id) {
        ExpenseDTO expenseDTO = expenseService.getExpenseById(id);
        return ResponseEntity.ok(expenseDTO);
    }

    @PostMapping
    public ExpenseDTO expensePost(@RequestBody Expense expense) {
        return expenseService.createExpense(expense);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ExpenseDTO> expenseUpdate(@PathVariable("id") Long id, @RequestBody Expense expense) {
        ExpenseDTO updatedExpense = expenseService.updateExpense(id, expense);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> expenseDelete(@PathVariable("id") Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }


}
