package com.nassau.gerenciador_de_renda.expense.repository;

import com.nassau.gerenciador_de_renda.expense.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

//    @Query("")

}
