package com.nassau.gerenciador_de_renda.expense.service;

import com.nassau.gerenciador_de_renda.client.dto.ClientDTO;
import com.nassau.gerenciador_de_renda.client.model.Client;
import com.nassau.gerenciador_de_renda.client.service.ClientService;
import com.nassau.gerenciador_de_renda.exceptions.ForbiddenException;
import com.nassau.gerenciador_de_renda.exceptions.ResourceAlreadyRegisteredException;
import com.nassau.gerenciador_de_renda.exceptions.ResourceNotFoundException;
import com.nassau.gerenciador_de_renda.exceptions.UnauthorizedException;
import com.nassau.gerenciador_de_renda.expense.dto.ExpenseFullDTO;
import com.nassau.gerenciador_de_renda.expense.dto.ExpenseUpdateDTO;
import com.nassau.gerenciador_de_renda.expense.repository.ExpenseRepository;
import com.nassau.gerenciador_de_renda.expense.dto.ExpenseDTO;
import com.nassau.gerenciador_de_renda.expense.model.Expense;
import com.nassau.gerenciador_de_renda.security.JwtAuthFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ClientService clientService;

//    public Expense getExpenseRelatory(String initialDate,String endDate, Long clientId){
//        ClientDTO client = clientService.getClientById(clientId);
//        return expenseRepository.//função do query no repo(initialDate, endDate, clientId);
//    }

    public ExpenseFullDTO getExpenseById(Long id){
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Despesa com id " + id + " não encontrada"));
        return new ExpenseFullDTO(expense);
    }

    @Transactional(readOnly = true)
    public List<ExpenseDTO> getAllExpensesByClient(Long clientId) {
        return expenseRepository.findExpensesByClientId(clientId)
                .stream()
                .map(ExpenseDTO::new)
                .collect(Collectors.toList());
    }

    public ExpenseFullDTO createExpense(Expense expense){
        expense.setDateCreated(LocalDateTime.now().toString());
        Expense savedExpense = expenseRepository.save(expense);
        return new ExpenseFullDTO(savedExpense);
    }

    public ExpenseFullDTO updateExpense(Long id, ExpenseUpdateDTO updatedExpense){
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Despesa com id " + id + " não encontrada"));

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

        if(updatedExpense.getDatePaid() != null){
            if(existingExpense.getDatePaid().equals(updatedExpense.getDatePaid())){
                throw new ResourceAlreadyRegisteredException("Data igual a anterior");
            }
            existingExpense.setDatePaid(updatedExpense.getDatePaid());
        }

        Expense savedExpense = expenseRepository.save(existingExpense);
        return new ExpenseFullDTO(savedExpense);

    }

    public void deleteExpense(Long id){
        if(!expenseRepository.existsById(id)){
            throw new ResourceNotFoundException("Despesa com id " + id + " não encontrada");
        }
        expenseRepository.deleteById(id);
    }

}
