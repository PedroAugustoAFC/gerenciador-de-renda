package com.nassau.gerenciador_de_renda.goal.service;

import com.nassau.gerenciador_de_renda.exceptions.ResourceAlreadyRegisteredException;
import com.nassau.gerenciador_de_renda.exceptions.ResourceNotFoundException;
import com.nassau.gerenciador_de_renda.goal.dto.GoalDTO;
import com.nassau.gerenciador_de_renda.goal.dto.GoalUpdateDTO;
import com.nassau.gerenciador_de_renda.goal.model.Goal;
import com.nassau.gerenciador_de_renda.goal.repository.GoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    public Goal getGoalById(Long id){
        return goalRepository.getById(id);
    }

    @Transactional(readOnly = true)
    public List<GoalDTO> getAllGoalsByClient(Long clientId) {
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

    public GoalDTO updateGoal(Long id, GoalUpdateDTO updatedGoal){
        Goal existingGoal = goalRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Meta com id " + id + " não encontrada"));

        if(updatedGoal.getDescription() != null){
            if(existingGoal.getDescription().equals(updatedGoal.getDescription())){
                throw new ResourceAlreadyRegisteredException("Descrição igual a anterior");
            }
            existingGoal.setDescription(updatedGoal.getDescription());
        }

        if(updatedGoal.getTargetAmount() != 0){
            if(existingGoal.getTargetAmount() == updatedGoal.getTargetAmount()){
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

    public void deleteGoal(Long id){
        if(!goalRepository.existsById(id)) {
            throw new RuntimeException("Meta com id " + id + " não encontrada.");
        }
        goalRepository.deleteById(id);
    }
}
