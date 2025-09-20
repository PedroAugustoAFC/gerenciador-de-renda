package com.nassau.gerenciador_de_renda.financeInfo.service;

import com.nassau.gerenciador_de_renda.exceptions.ResourceAlreadyRegisteredException;
import com.nassau.gerenciador_de_renda.financeInfo.repository.FinanceInfoRepository;
import com.nassau.gerenciador_de_renda.financeInfo.dto.FinanceInfoDTO;
import com.nassau.gerenciador_de_renda.financeInfo.dto.FinanceInfoUpdateDTO;
import com.nassau.gerenciador_de_renda.financeInfo.model.FinanceInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FinanceInfoService {

    @Autowired
    private FinanceInfoRepository financeInfoRepository;

    public FinanceInfoDTO getFinanceInfoByClientId(Long clientId) {
        FinanceInfo financeInfo = financeInfoRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Informações financeiras do cliente de id " + clientId + " não encontradas."));
        return new FinanceInfoDTO(financeInfo);
    }

    public FinanceInfoDTO saveFinanceInfo(FinanceInfo financeInfo) {
        FinanceInfo savedInfo = financeInfoRepository.save(financeInfo);
        return new FinanceInfoDTO(savedInfo);
    }

    public FinanceInfoDTO updateFinanceInfo(Long clientId, FinanceInfoUpdateDTO updateFinanceInfo) {
        FinanceInfo existingInfo = financeInfoRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Informações financeiras do cliente de id " + clientId + " não encontradas."));

        if (updateFinanceInfo.getIncome() != null) {
            if(existingInfo.getIncome() == updateFinanceInfo.getIncome()){
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
            if(existingInfo.getNetWorth() == updateFinanceInfo.getNetWorth()){
                throw new ResourceAlreadyRegisteredException("Patrimônio líquido igual ao anterior");
            }
            existingInfo.setNetWorth(updateFinanceInfo.getNetWorth());
        }

        FinanceInfo updatedInfo = financeInfoRepository.save(existingInfo);
        return new FinanceInfoDTO(updatedInfo);
    }

    public void deleteFinanceInfo(Long id) {
        if(!financeInfoRepository.existsById(id)) {
            throw new RuntimeException("Informações financeiras com id " + id + " não encontradas.");
        }
        financeInfoRepository.deleteById(id);
    }
}
