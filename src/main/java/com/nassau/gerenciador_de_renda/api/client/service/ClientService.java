package com.nassau.gerenciador_de_renda.api.client.service;

import com.nassau.gerenciador_de_renda.api.client.dto.ClientUpdateDTO;
import com.nassau.gerenciador_de_renda.api.client.repository.ClientRepository;
import com.nassau.gerenciador_de_renda.api.client.dto.ClientDTO;
import com.nassau.gerenciador_de_renda.api.client.model.Client;
import com.nassau.gerenciador_de_renda.api.exceptions.ResourceAlreadyRegisteredException;
import com.nassau.gerenciador_de_renda.api.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public ClientDTO getClientById(UUID id){
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com id " + id + " nao encontrado"));
        return new ClientDTO(client);
    }

    public Client getClientByEmail(String email){
        return clientRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Email não cadastrado"));
    }

    public ClientDTO createClient(Client client){
        if(clientRepository.existsByEmail(client.getEmail())){
            throw new ResourceAlreadyRegisteredException("Email ja esta em uso");
        }
        String encodedPassword = passwordEncoder.encode(client.getPassword());
        client.setPassword(encodedPassword);
        Client savedClient = clientRepository.save(client);
        return new ClientDTO(savedClient);
    }

    public ClientDTO updateClient(UUID id, ClientUpdateDTO updatedClient){
        Client existingClient = clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente com id " + id + " nao encontrado"));

        if(updatedClient.getEmail() != null){
            if(existingClient.getEmail().equals(updatedClient.getEmail())){
                throw new ResourceAlreadyRegisteredException("Email igual ao anterior");
            }if (clientRepository.existsByEmail(updatedClient.getEmail())){
                throw new ResourceAlreadyRegisteredException("Email já esta em uso");
            }
            existingClient.setEmail(updatedClient.getEmail());
        }

        if(updatedClient.getName() != null){
            if(existingClient.getName().equals(updatedClient.getName())){
                throw new ResourceAlreadyRegisteredException("Nome igual ao anterior");
            }
            existingClient.setName(updatedClient.getName());
        }

        if(updatedClient.getPassword() != null){
            if(passwordEncoder.matches(updatedClient.getPassword(), existingClient.getPassword())){
                throw new ResourceAlreadyRegisteredException("Senha igual a anterior");
            }
            String encodedPassword = passwordEncoder.encode(updatedClient.getPassword());
            existingClient.setPassword(encodedPassword);
        }

        Client savedClient = clientRepository.save(existingClient);
        return new ClientDTO(savedClient);

    }

    public void deleteClient(UUID id){

        if(!clientRepository.existsById(id)){
            throw new ResourceNotFoundException("Cliente com id " + id + " nao encontrado");
        }
        clientRepository.deleteById(id);
    }

}
