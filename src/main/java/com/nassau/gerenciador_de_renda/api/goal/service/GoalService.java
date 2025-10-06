package com.nassau.gerenciador_de_renda.api.goal.service;

import com.nassau.gerenciador_de_renda.api.exceptions.ResourceAlreadyRegisteredException;
import com.nassau.gerenciador_de_renda.api.exceptions.ResourceNotFoundException;
import com.nassau.gerenciador_de_renda.api.goal.dto.GoalDTO;
import com.nassau.gerenciador_de_renda.api.goal.dto.GoalUpdateDTO;
import com.nassau.gerenciador_de_renda.api.goal.model.Goal;
import com.nassau.gerenciador_de_renda.api.goal.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    public Goal getGoalById(UUID id){
        return goalRepository.getById(id);
    }

    @Transactional(readOnly = true)
    public List<GoalDTO> getAllGoalsByClient(UUID clientId) {
        return goalRepository.findGoalsByClientId(clientId)
                .stream()
                .map(GoalDTO::new)
                .collect(Collectors.toList());
    }

    public GoalDTO saveGoal(Goal goal){
        goal.setDateCreated(LocalDateTime.now());
        Goal goalSaved = goalRepository.save(goal);
        return new GoalDTO(goalSaved);
    }

    public GoalDTO updateGoal(UUID id, GoalUpdateDTO updatedGoal){
        Goal existingGoal = goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meta com id " + id + " não encontrada"));

        if(updatedGoal.getDescription() != null){
            if(existingGoal.getDescription().equals(updatedGoal.getDescription())){
                throw new ResourceAlreadyRegisteredException("Descrição igual a anterior");
            }
            existingGoal.setDescription(updatedGoal.getDescription());
        }

        if(updatedGoal.getTargetAmount() != null && updatedGoal.getTargetAmount().compareTo(BigDecimal.ZERO) != 0){
            if(existingGoal.getTargetAmount().compareTo(updatedGoal.getTargetAmount()) == 0){
                throw new ResourceAlreadyRegisteredException("Valor igual ao anterior");
            }
            existingGoal.setTargetAmount(updatedGoal.getTargetAmount());
        }

        if(updatedGoal.getTargetDate() != null){
            if(existingGoal.getTargetDate().equals(updatedGoal.getTargetDate())){
                throw new ResourceAlreadyRegisteredException("Data igual a anterior");
            }
            existingGoal.setTargetDate(updatedGoal.getTargetDate());
        }

        Goal savedGoal = goalRepository.save(existingGoal);
        return new GoalDTO(savedGoal);
    }

    public void deleteGoal(UUID id){
        if(!goalRepository.existsById(id)) {
            throw new RuntimeException("Meta com id " + id + " não encontrada.");
        }
        goalRepository.deleteById(id);
    }
}
