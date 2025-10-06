package com.nassau.gerenciador_de_renda.client.dto;

import com.nassau.gerenciador_de_renda.api.client.dto.ClientDTO;
import com.nassau.gerenciador_de_renda.api.client.model.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do ClientDTO")
class ClientDTOTest {

    @Test
    @DisplayName("Deve criar DTO a partir de entidade")
    void shouldCreateDTOFromEntity() {
        Client client = new Client();
        client.setId(1L);
        client.setName("João Silva");
        client.setEmail("joao@email.com");
        client.setPassword("MinhaSenh@123");

        ClientDTO dto = new ClientDTO(client);

        assertEquals(1L, dto.getId());
        assertEquals("João Silva", dto.getName());
        assertEquals("joao@email.com", dto.getEmail());
    }

    @Test
    @DisplayName("Deve criar DTO vazio")
    void shouldCreateEmptyDTO() {
        ClientDTO dto = new ClientDTO();
        assertNull(dto.getId());
        assertNull(dto.getName());
        assertNull(dto.getEmail());
    }
}
