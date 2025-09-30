package com.nassau.gerenciador_de_renda.goal.repository;

import com.nassau.gerenciador_de_renda.goal.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface GoalRepository extends JpaRepository<Goal,UUID> {

    @Query("SELECT e FROM Goal e WHERE e.client.id = :clientId")
    List<Goal> findGoalsByClientId(@Param("clientId") UUID clientId);
}
