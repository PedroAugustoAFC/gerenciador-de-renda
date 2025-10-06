package com.nassau.gerenciador_de_renda.api.expense.repository;

import com.nassau.gerenciador_de_renda.api.expense.model.categoryEnum.ExpenseCategory;
import com.nassau.gerenciador_de_renda.api.expense.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, UUID> {

    @Query("SELECT e FROM Expense e WHERE e.client.id = :clientId")
    List<Expense> findExpensesByClientId(@Param("clientId") UUID clientId);

    @Query("SELECT e FROM Expense e WHERE e.client.id = :clientId " +
            "AND (:startDate IS NULL OR e.datePaid >= :startDate) " +
            "AND (:endDate IS NULL OR e.datePaid <= :endDate) " +
            "AND (:category IS NULL OR LOWER(e.category) = LOWER(:category)) " +
            "ORDER BY e.datePaid DESC")
    List<Expense> findFilteredExpensesByClientId(
            @Param("clientId") UUID clientId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("category") ExpenseCategory category);
}
