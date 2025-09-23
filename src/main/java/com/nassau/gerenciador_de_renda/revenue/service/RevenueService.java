package com.nassau.gerenciador_de_renda.revenue.service;

import com.nassau.gerenciador_de_renda.exceptions.ResourceAlreadyRegisteredException;
import com.nassau.gerenciador_de_renda.exceptions.ResourceNotFoundException;
import com.nassau.gerenciador_de_renda.revenue.dto.RevenueDTO;
import com.nassau.gerenciador_de_renda.revenue.dto.RevenueFullDTO;
import com.nassau.gerenciador_de_renda.revenue.dto.RevenueUpdateDTO;
import com.nassau.gerenciador_de_renda.revenue.model.Revenue;
import com.nassau.gerenciador_de_renda.revenue.repository.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RevenueService {

    @Autowired
    private RevenueRepository revenueRepository;

    public RevenueFullDTO getRevenueById(Long id) {
        Revenue revenue = revenueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receita com id " + id + " não encontrada"));
        return new RevenueFullDTO(revenue);
    }

    @Transactional(readOnly = true)
    public List<RevenueDTO> getRevenuesByClientId(Long clientId) {
        return revenueRepository.findRevenuesByClientId(clientId)
                .stream()
                .map(RevenueDTO::new)
                .collect(Collectors.toList());
    }

    public RevenueFullDTO saveRevenue(Revenue revenue) {
        revenue.setDateCreated(LocalDateTime.now());
        Revenue savedRevenue = revenueRepository.save(revenue);
        return new RevenueFullDTO(savedRevenue);
    }

    public RevenueFullDTO updateRevenue(Long id, RevenueUpdateDTO updatedRevenue){
        Revenue existingRevenue = revenueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Despesa com id " + id + " não encontrada"));

        if(updatedRevenue.getDescription() != null){
            if(existingRevenue.getDescription().equals(updatedRevenue.getDescription())){
                throw new ResourceAlreadyRegisteredException("Descrição igual a anterior");
            }
            existingRevenue.setDescription(updatedRevenue.getDescription());
        }

        if(updatedRevenue.getAmount() != 0){
            if(existingRevenue.getAmount() == updatedRevenue.getAmount()){
                throw new ResourceAlreadyRegisteredException("Valor igual ao anterior");
            }
            existingRevenue.setAmount(updatedRevenue.getAmount());
        }

        if(updatedRevenue.getDatePaid() != null){
            if(existingRevenue.getDatePaid().equals(updatedRevenue.getDatePaid())){
                throw new ResourceAlreadyRegisteredException("Data igual a anterior");
            }
            existingRevenue.setDatePaid(updatedRevenue.getDatePaid());
        }

        Revenue savedRevenue = revenueRepository.save(existingRevenue);
        return new RevenueFullDTO(savedRevenue);

    }

    public void deleteRevenue(Long id) {
        if (!revenueRepository.existsById(id)) {
            throw new ResourceNotFoundException("Receita com id " + id + " não encontrada");
        }
        revenueRepository.deleteById(id);
    }


}
