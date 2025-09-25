package com.nassau.gerenciador_de_renda.expense.controller;

import com.nassau.gerenciador_de_renda.expense.dto.ExpenseDTO;
import com.nassau.gerenciador_de_renda.expense.dto.ExpenseUpdateDTO;
import com.nassau.gerenciador_de_renda.expense.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @PatchMapping("/{id}")
    public ResponseEntity<ExpenseDTO> expenseUpdate(@PathVariable("id") Long id, @Valid @RequestBody ExpenseUpdateDTO expenseUpdateDTO) {
        ExpenseDTO updatedExpense = expenseService.updateExpense(id, expenseUpdateDTO);
        return ResponseEntity.ok(updatedExpense);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> expenseDelete(@PathVariable("id") Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }


}
