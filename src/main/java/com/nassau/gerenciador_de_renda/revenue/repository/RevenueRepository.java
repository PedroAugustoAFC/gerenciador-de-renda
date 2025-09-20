package com.nassau.gerenciador_de_renda.revenue.repository;

import com.nassau.gerenciador_de_renda.revenue.model.Revenue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RevenueRepository extends JpaRepository<Revenue, Long> {

    @Query("SELECT e FROM Revenue e WHERE e.client.id = :clientId")
    List<Revenue> findRevenuesByClientId(@Param("clientId") Long clientId);

}
