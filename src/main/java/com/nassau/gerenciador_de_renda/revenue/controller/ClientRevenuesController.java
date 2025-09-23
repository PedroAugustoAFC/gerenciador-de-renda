package com.nassau.gerenciador_de_renda.revenue.controller;

import com.nassau.gerenciador_de_renda.client.service.ClientService;
import com.nassau.gerenciador_de_renda.expense.dto.ExpenseFullDTO;
import com.nassau.gerenciador_de_renda.revenue.dto.RevenueDTO;
import com.nassau.gerenciador_de_renda.revenue.dto.RevenueFullDTO;
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
    private ValidateAccessService validateAccessService;

    @GetMapping
    public ResponseEntity<List<RevenueFullDTO>> listAllExpenses(
            @PathVariable("id") Long clientId,
            @RequestParam(value = "startDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate startDate,
            @RequestParam(value = "endDate", required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate endDate,
            @RequestParam(value = "category", required = false) String category,
            HttpServletRequest request) {

        validateAccessService.validateClientAccess(clientId, request);
        List<RevenueFullDTO> expenses = revenueService.getFilteredRevenuesByClient(clientId, startDate, endDate, category);
        return ResponseEntity.ok(expenses);
    }

    @PostMapping
    public ResponseEntity<RevenueFullDTO> revenuePost(@Valid @RequestBody Revenue revenue, @PathVariable("id") Long clientId, HttpServletRequest request){
        validateAccessService.validateClientAccess(clientId, request);
        revenue.setClientId(clientId);
        RevenueFullDTO revenueFullDTO = revenueService.saveRevenue(revenue);
        return ResponseEntity.ok(revenueFullDTO);
    }
}
