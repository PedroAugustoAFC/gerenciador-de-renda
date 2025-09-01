package com.nassau.gerenciador_de_renda.expense.service;

import com.nassau.gerenciador_de_renda.exceptions.ResourceAlreadyRegisteredException;
import com.nassau.gerenciador_de_renda.exceptions.ResourceNotFoundException;
import com.nassau.gerenciador_de_renda.expense.repository.ExpenseRepository;
import com.nassau.gerenciador_de_renda.expense.dto.ExpenseDTO;
import com.nassau.gerenciador_de_renda.expense.model.Expense;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public ExpenseDTO getExpenseById(Long id){
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Despesa com id " + id + " não encontrada"));
        return new ExpenseDTO(expense);
    }

    @Transactional(readOnly = true)
    public List<ExpenseDTO> getAllExpenses() {
        return expenseRepository.findAll()
                .stream()
                .map(ExpenseDTO::new)
                .collect(Collectors.toList());
    }

    public ExpenseDTO createExpense(Expense expense){
        Expense savedExpense = expenseRepository.save(expense);
        return new ExpenseDTO(savedExpense);
    }

    public ExpenseDTO updateExpense(Long id, Expense updatedExpense){
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Despesa com id " + id + " não encontrada"));

        if (existingExpense != null) {

            if(updatedExpense.getDescription() != null){
                if(existingExpense.getDescription().equals(updatedExpense.getDescription())){
                    throw new ResourceAlreadyRegisteredException("Descrição igual a anterior");
                }
                existingExpense.setDescription(updatedExpense.getDescription());
            }

            if(updatedExpense.getAmount() != 0){
                if(existingExpense.getAmount() == updatedExpense.getAmount()){
                    throw new ResourceAlreadyRegisteredException("Valor igual ao anterior");
                }
                existingExpense.setAmount(updatedExpense.getAmount());
            }

            if(updatedExpense.getDate() != null){
                if(existingExpense.getDate().equals(updatedExpense.getDate())){
                    throw new ResourceAlreadyRegisteredException("Data igual a anterior");
                }
                existingExpense.setDate(updatedExpense.getDate());
            }

            Expense savedExpense = expenseRepository.save(existingExpense);
            return new ExpenseDTO(savedExpense);
        }
        return null;
    }

    public void deleteExpense(Long id){
        if(!expenseRepository.existsById(id)){
            throw new ResourceNotFoundException("Despesa com id " + id + " não encontrada");
        }
        expenseRepository.deleteById(id);
    }

}
