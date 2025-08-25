package com.nassau.gerenciador_de_renda.Client;

import org.springframework.stereotype.Service;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    public Client getClientById(Long id){
        return clientRepository.findById(id).orElse(null);
    }

    public Client createClient(Client client){
        return clientRepository.save(client);
    }

    public void deleteClient(Long id){
        clientRepository.deleteById(id);
    }

}
