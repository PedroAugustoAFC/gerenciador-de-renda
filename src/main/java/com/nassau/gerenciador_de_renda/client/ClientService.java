package com.nassau.gerenciador_de_renda.client;

import com.nassau.gerenciador_de_renda.exceptions.EmailException;
import com.nassau.gerenciador_de_renda.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public ClientDTO getClientById(Long id){
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
            throw new EmailException("Email ja esta em uso");
        }
        String encodedPassword = passwordEncoder.encode(client.getPassword());
        client.setPassword(encodedPassword);
        Client savedClient = clientRepository.save(client);
        return new ClientDTO(savedClient);
    }

    public void deleteClient(Long id){

        if(!clientRepository.existsById(id)){
            throw new ResourceNotFoundException();
        }
        clientRepository.deleteById(id);
    }

}
