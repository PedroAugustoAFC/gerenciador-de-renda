package com.nassau.gerenciador_de_renda.goal.controller;

import com.nassau.gerenciador_de_renda.client.service.ClientService;
import com.nassau.gerenciador_de_renda.goal.service.GoalService;
import com.nassau.gerenciador_de_renda.goal.dto.GoalDTO;
import com.nassau.gerenciador_de_renda.goal.model.Goal;
import com.nassau.gerenciador_de_renda.security.ValidateAccessService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clients/{clientId}/goals")
public class ClientGoalsController {

    @Autowired
    private GoalService goalService;

    @Autowired
    private ValidateAccessService validateAccessService;

    @GetMapping
    public ResponseEntity<List<GoalDTO>> listAllGoals(@PathVariable("id") UUID clientId, HttpServletRequest request) {
        validateAccessService.validateClientAccess(clientId, request);
        List<GoalDTO> goals = goalService.getAllGoalsByClient(clientId);
        return ResponseEntity.ok(goals);
    }

    @PostMapping
    public ResponseEntity<GoalDTO> goalPost(@PathVariable("id") UUID clientId, @Valid @RequestBody Goal goal,
                                                  HttpServletRequest request) {
        validateAccessService.validateClientAccess(clientId, request);
        goal.setClientId(clientId);
        GoalDTO createdGoal = goalService.saveGoal(goal);
        return ResponseEntity.ok(createdGoal);
    }
}
