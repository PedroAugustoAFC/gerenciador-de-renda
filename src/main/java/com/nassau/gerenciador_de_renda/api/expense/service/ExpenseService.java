package com.nassau.gerenciador_de_renda.api.expense.service;

import com.nassau.gerenciador_de_renda.api.client.service.ClientService;
import com.nassau.gerenciador_de_renda.api.exceptions.ResourceAlreadyRegisteredException;
import com.nassau.gerenciador_de_renda.api.exceptions.ResourceNotFoundException;
import com.nassau.gerenciador_de_renda.api.expense.model.expenseEnum.ExpenseCategory;
import com.nassau.gerenciador_de_renda.api.expense.dto.ExpenseDTO;
import com.nassau.gerenciador_de_renda.api.expense.dto.ExpenseUpdateDTO;
import com.nassau.gerenciador_de_renda.api.expense.repository.ExpenseRepository;
import com.nassau.gerenciador_de_renda.api.expense.model.Expense;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ClientService clientService;

    @Transactional(readOnly = true)
    public List<ExpenseDTO> getFilteredExpensesByClient(UUID clientId, LocalDate startDate, LocalDate endDate, String category) {
        if (startDate == null && endDate == null && (category == null || category.trim().isEmpty())) {
            return expenseRepository.findExpensesByClientId(clientId)
                    .stream()
                    .map(ExpenseDTO::new)
                    .collect(Collectors.toList());
        }

        if (startDate == null && endDate == null) {
            throw new IllegalArgumentException("Nenhuma data fornecida" );
        }
        if (startDate != null && endDate == null) {
            throw new IllegalArgumentException("Data final não fornecida");
        }
        if (startDate == null && endDate != null) {
            throw new IllegalArgumentException("Data inicial não fornecida");
        }
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Data inicial não pode ser posterior à data final");
        }

        ExpenseCategory categoryEnum = null;
        if (category != null && !category.trim().isEmpty()) {
            try {
                categoryEnum = ExpenseCategory.fromDisplayName(category.trim());
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Categoria inválida: " + category);
            }
        }
        ExpenseCategory categoryToFilter = (categoryEnum == ExpenseCategory.NENHUM) ? null : categoryEnum;
        return expenseRepository.findFilteredExpensesByClientId(clientId, startDate, endDate, categoryEnum)
                .stream()
                .map(ExpenseDTO::new)
                .collect(Collectors.toList());
    }

    public ExpenseDTO createExpense(Expense expense){
        expense.setDateCreated(LocalDateTime.now());
        Expense savedExpense = expenseRepository.save(expense);
        return new ExpenseDTO(savedExpense);
    }

    public ExpenseDTO updateExpense(UUID id, ExpenseUpdateDTO updatedExpense){
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Despesa com id " + id + " não encontrada"));

        if(updatedExpense.getDescription() != null){
            if(existingExpense.getDescription().equals(updatedExpense.getDescription())){
                throw new ResourceAlreadyRegisteredException("Descrição igual a anterior");
            }
            existingExpense.setDescription(updatedExpense.getDescription());
        }

        if(updatedExpense.getAmount() != null && updatedExpense.getAmount().compareTo(BigDecimal.ZERO) != 0){
            if(existingExpense.getAmount().compareTo(updatedExpense.getAmount()) == 0){
                throw new ResourceAlreadyRegisteredException("Valor igual ao anterior");
            }
            existingExpense.setAmount(updatedExpense.getAmount());
        }

        if(updatedExpense.getDatePaid() != null){
            if(existingExpense.getDatePaid().equals(updatedExpense.getDatePaid())){
                throw new ResourceAlreadyRegisteredException("Data igual a anterior");
            }
            existingExpense.setDatePaid(updatedExpense.getDatePaid());
        }

        Expense savedExpense = expenseRepository.save(existingExpense);
        return new ExpenseDTO(savedExpense);

    }

    public void deleteExpense(UUID id){
        if(!expenseRepository.existsById(id)){
            throw new ResourceNotFoundException("Despesa com id " + id + " não encontrada");
        }
        expenseRepository.deleteById(id);
    }

}
