package com.nassau.gerenciador_de_renda.revenue.controller;

import com.nassau.gerenciador_de_renda.client.service.ClientService;
import com.nassau.gerenciador_de_renda.revenue.dto.RevenueDTO;
import com.nassau.gerenciador_de_renda.revenue.dto.RevenueFullDTO;
import com.nassau.gerenciador_de_renda.revenue.model.Revenue;
import com.nassau.gerenciador_de_renda.revenue.service.RevenueService;
import com.nassau.gerenciador_de_renda.security.ValidateAccessService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/client/{id}/revenues")
public class ClientRevenuesController {

    @Autowired
    private RevenueService revenueService;

    @Autowired
    private ValidateAccessService validateAccessService;

    @GetMapping
    public ResponseEntity<List<RevenueDTO>> ListAllRevenues(@PathVariable("id") Long clientId, HttpServletRequest request){
        validateAccessService.validateClientAccess(clientId, request);
        List<RevenueDTO> revenues = revenueService.getRevenuesByClientId(clientId);
        return ResponseEntity.ok(revenues);
    }

    @PostMapping
    public ResponseEntity<RevenueFullDTO> revenuePost(@Valid @RequestBody Revenue revenue, @PathVariable("id") Long clientId, HttpServletRequest request){
        validateAccessService.validateClientAccess(clientId, request);
        revenue.setClientId(clientId);
        RevenueFullDTO revenueFullDTO = revenueService.saveRevenue(revenue);
        return ResponseEntity.ok(revenueFullDTO);
    }
}
