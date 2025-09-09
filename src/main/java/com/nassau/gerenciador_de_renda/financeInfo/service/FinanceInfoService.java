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

    public FinanceInfoUpdateDTO updateFinanceInfo(Long clientId, FinanceInfoUpdateDTO updateDTO) {
        FinanceInfo existingInfo = financeInfoRepository.findByClientId(clientId)
                .orElseThrow(() -> new RuntimeException("Informações financeiras do cliente de id " + clientId + " não encontradas."));

        if (updateDTO.getIncome() != null) {
            if(existingInfo.getIncome() == updateDTO.getIncome()){
                throw new ResourceAlreadyRegisteredException("Rendimento igual ao anterior");
            }
            existingInfo.setIncome(updateDTO.getIncome());
        }
        if (updateDTO.getProfission() != null) {
            if (existingInfo.getProfission().equals(updateDTO.getProfission())){
                throw new ResourceAlreadyRegisteredException("Profissão igual a anterior");
            }
            existingInfo.setProfission(updateDTO.getProfission());
        }
//        if (updateDTO.getDependents() != null) {
//            existingInfo.setDependents(updateDTO.getDependents());
//        }
        if (updateDTO.getNetWorth() != null) {
            if(existingInfo.getNetWorth() == updateDTO.getNetWorth()){
                throw new ResourceAlreadyRegisteredException("Patrimônio líquido igual ao anterior");
            }
            existingInfo.setNetWorth(updateDTO.getNetWorth());
        }

        FinanceInfo updatedInfo = financeInfoRepository.save(existingInfo);
        return new FinanceInfoUpdateDTO(updatedInfo);
    }

    public void deleteFinanceInfo(Long id) {
        financeInfoRepository.deleteById(id);
    }
}
