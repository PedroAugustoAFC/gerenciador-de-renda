package com.nassau.gerenciador_de_renda.financeInfo.repository;

import com.nassau.gerenciador_de_renda.financeInfo.dto.FinanceInfoDTO;
import com.nassau.gerenciador_de_renda.financeInfo.model.FinanceInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FinanceInfoRepository extends JpaRepository<FinanceInfo, Long> {

    public Optional<FinanceInfo> findByClientId(Long clientId);
}
