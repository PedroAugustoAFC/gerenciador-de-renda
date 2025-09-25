package com.nassau.gerenciador_de_renda.revenue.controller;

import com.nassau.gerenciador_de_renda.financeInfo.service.FinanceInfoService;
import com.nassau.gerenciador_de_renda.revenue.dto.RevenueDTO;
import com.nassau.gerenciador_de_renda.revenue.model.Revenue;
import com.nassau.gerenciador_de_renda.revenue.service.RevenueService;
import com.nassau.gerenciador_de_renda.security.ValidateAccessService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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
            @PathVariable("id") Long clientId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate,
            @RequestParam(value = "category", required = false) String category,
            HttpServletRequest request) {

        validateAccessService.validateClientAccess(clientId, request);
        List<RevenueDTO> revenues = revenueService.getFilteredRevenuesByClient(clientId, startDate, endDate, category);
        return ResponseEntity.ok(revenues);
    }

    @PostMapping
    public ResponseEntity<RevenueDTO> revenuePost(@Valid @RequestBody Revenue revenue, @PathVariable("id") Long clientId, HttpServletRequest request){
        validateAccessService.validateClientAccess(clientId, request);

        double revenueValue = revenue.getAmount();
        financeInfoService.updateNetWorthAfterRevenue(clientId, revenueValue);

        revenue.setClientId(clientId);
        RevenueDTO revenueDTO = revenueService.saveRevenue(revenue);
        return ResponseEntity.ok(revenueDTO);
    }
}
