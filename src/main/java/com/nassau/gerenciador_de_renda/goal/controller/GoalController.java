package com.nassau.gerenciador_de_renda.goal.controller;

import com.nassau.gerenciador_de_renda.goal.dto.GoalDTO;
import com.nassau.gerenciador_de_renda.goal.dto.GoalUpdateDTO;
import com.nassau.gerenciador_de_renda.goal.service.GoalService;
import com.nassau.gerenciador_de_renda.goal.model.Goal;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goals")
public class GoalController {

    @Autowired
    private GoalService goalService;

    @GetMapping("/{id}")
    public ResponseEntity<?> expenseById(@PathVariable("id") Long id) {
        Goal goal = goalService.getGoalById(id);
        return ResponseEntity.ok(goal);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GoalDTO> expenseUpdate(@PathVariable("id") Long id, @Valid @RequestBody GoalUpdateDTO goalUpdateDTO) {
        GoalDTO goalDTO = goalService.updateGoal(id, goalUpdateDTO);
        return ResponseEntity.ok(goalDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> expenseDelete(@PathVariable("id") Long id) {
        goalService.deleteGoal(id);
        return ResponseEntity.noContent().build();
    }
}
