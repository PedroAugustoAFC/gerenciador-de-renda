package com.nassau.gerenciador_de_renda.financeInfo.repository;

import com.nassau.gerenciador_de_renda.financeInfo.dto.FinanceInfoDTO;
import com.nassau.gerenciador_de_renda.financeInfo.model.FinanceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FinanceInfoRepository extends JpaRepository<FinanceInfo, UUID> {

    public Optional<FinanceInfo> findByClientId(UUID clientId);
}
