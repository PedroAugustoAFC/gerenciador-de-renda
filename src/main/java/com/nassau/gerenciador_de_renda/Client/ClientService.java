package com.nassau.gerenciador_de_renda.Client;

import com.nassau.gerenciador_de_renda.exceptions.EmailException;
import com.nassau.gerenciador_de_renda.exceptions.ResourceNotFoundException;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public Client getClientById(Long id){
        return clientRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException());
    }

    public Client createClient(Client client){
        if(clientRepository.existsByEmail(client.getEmail())){
            throw new EmailException();
        }
            return clientRepository.save(client);
    }

    public void deleteClient(Long id){

        if(!clientRepository.existsById(id)){
            throw new ResourceNotFoundException();
        }
        clientRepository.deleteById(id);
    }

}
