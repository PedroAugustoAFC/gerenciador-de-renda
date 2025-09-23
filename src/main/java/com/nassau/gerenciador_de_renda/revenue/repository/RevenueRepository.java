package com.nassau.gerenciador_de_renda.revenue.repository;

import com.nassau.gerenciador_de_renda.expense.model.Expense;
import com.nassau.gerenciador_de_renda.expense.model.categoryEnum.ExpenseCategory;
import com.nassau.gerenciador_de_renda.revenue.model.Revenue;
import com.nassau.gerenciador_de_renda.revenue.model.categoryEnum.RevenueCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {

    @Query("SELECT e FROM Revenue e WHERE e.client.id = :clientId")
    List<Revenue> findRevenuesByClientId(@Param("clientId") Long clientId);

    @Query("SELECT e FROM Revenue e WHERE e.client.id = :clientId " +
            "AND (:startDate IS NULL OR e.datePaid >= :startDate) " +
            "AND (:endDate IS NULL OR e.datePaid <= :endDate) " +
            "AND (:category IS NULL OR LOWER(e.category) = LOWER(:category)) " +
            "ORDER BY e.datePaid DESC")
    List<Revenue> findFilteredRevenuesByClientId(
            @Param("clientId") Long clientId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("category") RevenueCategory category);
}
