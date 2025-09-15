package com.nassau.gerenciador_de_renda.goal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    public Goal getGoalById(Long id){
        return goalRepository.getById(id);
    }

//    public List<Goal> getAllGoal(Long clientId){
//        return goalRepository.findAllById(clientId);
//    }

    public Goal saveGoal(Goal goal){
        Goal goalSaved = goalRepository.save(goal);
        return goalSaved;
    }

    public void deleteGoal(Long id){
        goalRepository.deleteById(id);
    }
}
