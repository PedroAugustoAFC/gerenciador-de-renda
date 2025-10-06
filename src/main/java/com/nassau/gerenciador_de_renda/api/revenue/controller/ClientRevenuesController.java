package com.nassau.gerenciador_de_renda.api.revenue.controller;

import com.nassau.gerenciador_de_renda.api.financeInfo.service.FinanceInfoService;
import com.nassau.gerenciador_de_renda.api.revenue.dto.RevenueDTO;
import com.nassau.gerenciador_de_renda.api.revenue.model.Revenue;
import com.nassau.gerenciador_de_renda.api.revenue.service.RevenueService;
import com.nassau.gerenciador_de_renda.api.security.ValidateAccessService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/client/{id}/revenues")
public class ClientRevenuesController {

    @Autowired
    private RevenueService revenueService;

    @Autowired
    private FinanceInfoService financeInfoService;

    @Autowired
    private ValidateAccessService validateAccessService;

    @GetMapping
    public ResponseEntity<List<RevenueDTO>> listAllRevenues(
            @PathVariable("id") UUID clientId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate,
            @RequestParam(value = "category", required = false) String category,
            HttpServletRequest request) {

        validateAccessService.validateClientAccess(clientId, request);
        List<RevenueDTO> revenues = revenueService.getFilteredRevenuesByClient(clientId, startDate, endDate, category);
        return ResponseEntity.ok(revenues);
    }

    @PostMapping
    public ResponseEntity<RevenueDTO> revenuePost(@Valid @RequestBody Revenue revenue, @PathVariable("id") UUID clientId, HttpServletRequest request){
        validateAccessService.validateClientAccess(clientId, request);

        BigDecimal revenueValue = revenue.getAmount();
        financeInfoService.updateNetWorthAfterRevenue(clientId, revenueValue);

        revenue.setClientId(clientId);
        RevenueDTO revenueDTO = revenueService.saveRevenue(revenue);
        return ResponseEntity.ok(revenueDTO);
    }
}
