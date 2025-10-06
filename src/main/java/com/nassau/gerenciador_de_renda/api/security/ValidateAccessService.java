package com.nassau.gerenciador_de_renda.api.security;

import com.nassau.gerenciador_de_renda.api.client.model.Client;
import com.nassau.gerenciador_de_renda.api.client.repository.ClientRepository;
import com.nassau.gerenciador_de_renda.api.client.service.ClientService;
import com.nassau.gerenciador_de_renda.api.exceptions.ForbiddenException;
import com.nassau.gerenciador_de_renda.api.exceptions.UnauthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ValidateAccessService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private JwtAuthFilter tokenUtils;

    public void validateClientAccess(UUID clientId, HttpServletRequest request) {
        String emailFromToken = tokenUtils.getEmailFromToken(request);
        if (emailFromToken == null) {
            throw new UnauthorizedException("Token inválido ou não fornecido");
        }

        Client clientFromToken = clientService.getClientByEmail(emailFromToken);
        if (!clientFromToken.getId().equals(clientId)) {
            throw new ForbiddenException("Você só pode acessar suas próprias informações");
        }
    }
}
