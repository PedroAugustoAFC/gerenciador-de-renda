package com.nassau.gerenciador_de_renda.api.expense.controller;

import com.nassau.gerenciador_de_renda.api.expense.dto.ExpenseDTO;
import com.nassau.gerenciador_de_renda.api.expense.dto.ExpenseUpdateDTO;
import com.nassau.gerenciador_de_renda.api.expense.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PatchMapping("/{id}")
    public ResponseEntity<ExpenseDTO> expenseUpdate(@PathVariable("id") UUID id, @Valid @RequestBody ExpenseUpdateDTO expenseUpdateDTO) {
        ExpenseDTO updatedExpense = expenseService.updateExpense(id, expenseUpdateDTO);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> expenseDelete(@PathVariable("id") UUID id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }


}
