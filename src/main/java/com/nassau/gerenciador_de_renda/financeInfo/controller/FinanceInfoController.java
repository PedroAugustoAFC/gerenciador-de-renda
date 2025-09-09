package com.nassau.gerenciador_de_renda.financeInfo.controller;

import com.nassau.gerenciador_de_renda.financeInfo.dto.FinanceInfoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("client/{clientId}/finance-info")
public class FinanceInfoController {

    @GetMapping
    public ResponseEntity<FinanceInfoDTO> getFinanceInfoByClientId(@PathVariable Long clientId) {
        return ResponseEntity.ok(new FinanceInfoDTO());
    }

}
