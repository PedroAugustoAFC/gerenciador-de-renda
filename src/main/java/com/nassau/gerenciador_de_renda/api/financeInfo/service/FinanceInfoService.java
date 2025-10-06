package com.nassau.gerenciador_de_renda.api.financeInfo.service;

import com.nassau.gerenciador_de_renda.api.exceptions.ResourceAlreadyRegisteredException;
import com.nassau.gerenciador_de_renda.api.financeInfo.repository.FinanceInfoRepository;
import com.nassau.gerenciador_de_renda.api.financeInfo.dto.FinanceInfoDTO;
import com.nassau.gerenciador_de_renda.api.financeInfo.dto.FinanceInfoUpdateDTO;
import com.nassau.gerenciador_de_renda.api.financeInfo.model.FinanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class FinanceInfoService {

    @Autowired
    private FinanceInfoRepository financeInfoRepository;

    public FinanceInfoDTO getFinanceInfoByClientId(UUID clientId) {
        FinanceInfo financeInfo = financeInfoRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Informações financeiras do cliente de id " + clientId + " não encontradas."));
        return new FinanceInfoDTO(financeInfo);
    }

    public FinanceInfoDTO saveFinanceInfo(FinanceInfo financeInfo) {
        FinanceInfo savedInfo = financeInfoRepository.save(financeInfo);
        return new FinanceInfoDTO(savedInfo);
    }

    public FinanceInfoDTO updateFinanceInfo(UUID clientId, FinanceInfoUpdateDTO updateFinanceInfo) {
        FinanceInfo existingInfo = financeInfoRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Informações financeiras do cliente de id " + clientId + " não encontradas."));

        if (updateFinanceInfo.getIncome() != null) {
            if(existingInfo.getIncome().compareTo(updateFinanceInfo.getIncome()) == 0){
                throw new ResourceAlreadyRegisteredException("Rendimento igual ao anterior");
            }
            existingInfo.setIncome(updateFinanceInfo.getIncome());
        }
        if (updateFinanceInfo.getProfission() != null) {
            if (existingInfo.getProfission().equals(updateFinanceInfo.getProfission())){
                throw new ResourceAlreadyRegisteredException("Profissão igual a anterior");
            }
            existingInfo.setProfission(updateFinanceInfo.getProfission());
        }
        if (updateFinanceInfo.getNetWorth() != null) {
            if(existingInfo.getNetWorth().compareTo(updateFinanceInfo.getNetWorth()) == 0){
                throw new ResourceAlreadyRegisteredException("Patrimônio líquido igual ao anterior");
            }
            existingInfo.setNetWorth(updateFinanceInfo.getNetWorth());
        }

        FinanceInfo updatedInfo = financeInfoRepository.save(existingInfo);
        return new FinanceInfoDTO(updatedInfo);
    }

    public FinanceInfoDTO updateNetWorthAfterExpense(UUID clientId, BigDecimal expenseValue) {
        FinanceInfo existingInfo = financeInfoRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Informações financeiras do cliente de id " + clientId + " não encontradas."));

        BigDecimal currentNetWorth = existingInfo.getNetWorth() != null ? existingInfo.getNetWorth() : BigDecimal.ZERO;
        BigDecimal newNetWorth = currentNetWorth.subtract(expenseValue);

        if (newNetWorth.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Patrimônio líquido não pode ficar negativo após a despesa");
        }

        existingInfo.setNetWorth(newNetWorth);

        FinanceInfo updatedInfo = financeInfoRepository.save(existingInfo);
        return new FinanceInfoDTO(updatedInfo);
    }

    public FinanceInfoDTO updateNetWorthAfterRevenue(UUID clientId, BigDecimal revenueValue) {
        FinanceInfo existingInfo = financeInfoRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Informações financeiras do cliente de id " + clientId + " não encontradas."));

        BigDecimal currentNetWorth = existingInfo.getNetWorth() != null ? existingInfo.getNetWorth() : BigDecimal.ZERO;
        BigDecimal newNetWorth = currentNetWorth.add(revenueValue);

        existingInfo.setNetWorth(newNetWorth);

        FinanceInfo updatedInfo = financeInfoRepository.save(existingInfo);
        return new FinanceInfoDTO(updatedInfo);
    }

    public void deleteFinanceInfo(UUID id) {
        if(!financeInfoRepository.existsById(id)) {
            throw new RuntimeException("Informações financeiras com id " + id + " não encontradas.");
        }
        financeInfoRepository.deleteById(id);
    }
}
