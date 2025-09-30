package com.nassau.gerenciador_de_renda.financeInfo.controller;

import com.nassau.gerenciador_de_renda.financeInfo.dto.FinanceInfoDTO;
import com.nassau.gerenciador_de_renda.financeInfo.dto.FinanceInfoUpdateDTO;
import com.nassau.gerenciador_de_renda.financeInfo.model.FinanceInfo;
import com.nassau.gerenciador_de_renda.financeInfo.repository.FinanceInfoRepository;
import com.nassau.gerenciador_de_renda.financeInfo.service.FinanceInfoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("clients/{clientId}/finance-infos")
public class FinanceInfoController {

    @Autowired
    private FinanceInfoService financeInfoService;

    @GetMapping
    public ResponseEntity<FinanceInfoDTO> getFinanceInfoByClientId(@PathVariable("clientId") UUID clientId) {
        FinanceInfoDTO financeInfoDTO = financeInfoService.getFinanceInfoByClientId(clientId);
        return ResponseEntity.ok(financeInfoDTO);
    }

    @PostMapping
    public ResponseEntity<FinanceInfoDTO> createFinanceInfo(@PathVariable("clientId") UUID clientId,@Valid @RequestBody FinanceInfo financeInfo) {
        financeInfo.setClientId(clientId);
        FinanceInfoDTO financeInfoDTO = financeInfoService.saveFinanceInfo(financeInfo);
        return ResponseEntity.ok(financeInfoDTO);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<FinanceInfoDTO> updateFinanceInfo(@PathVariable("id") UUID id,@Valid @RequestBody FinanceInfoUpdateDTO financeInfoUpdateDTO){
        FinanceInfoDTO updatedFinanceInfo = financeInfoService.updateFinanceInfo(id, financeInfoUpdateDTO);
        return ResponseEntity.ok(updatedFinanceInfo);
    }

    @DeleteMapping("/{id}")
    public void deleteFinanceInfo(@PathVariable("id") UUID id){
        financeInfoService.deleteFinanceInfo(id);
    }



}
