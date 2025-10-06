package com.nassau.gerenciador_de_renda.util;

import com.nassau.gerenciador_de_renda.api.client.model.Client;
import com.nassau.gerenciador_de_renda.api.client.dto.ClientDTO;
import com.nassau.gerenciador_de_renda.api.client.dto.ClientUpdateDTO;

public class TestDataBuilder {

    public static Client createValidClient() {
        Client client = new Client();
        client.setName("João Silva");
        client.setEmail("joao@email.com");
        client.setPassword("MinhaSenh@123");
        return client;
    }

    public static Client createValidClientWithId(Long id) {
        Client client = createValidClient();
        client.setId(id);
        return client;
    }

    public static ClientDTO createValidClientDTO() {
        ClientDTO dto = new ClientDTO();
        dto.setId(1L);
        dto.setName("João Silva");
        dto.setEmail("joao@email.com");
        return dto;
    }

    public static ClientUpdateDTO createValidUpdateDTO() {
        ClientUpdateDTO dto = new ClientUpdateDTO();
        dto.setName("Maria Silva");
        dto.setEmail("maria@email.com");
        dto.setPassword("NovaSenha@456");
        return dto;
    }

    public static Client createInvalidClient() {
        Client client = new Client();
        client.setName("Jo"); // Muito curto
        client.setEmail("email-invalido");
        client.setPassword("123"); // Muito simples
        return client;
    }
}
