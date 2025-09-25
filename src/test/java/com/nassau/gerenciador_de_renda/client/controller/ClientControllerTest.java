package com.nassau.gerenciador_de_renda.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nassau.gerenciador_de_renda.client.dto.ClientDTO;
import com.nassau.gerenciador_de_renda.client.dto.ClientUpdateDTO;
import com.nassau.gerenciador_de_renda.client.service.ClientService;
import com.nassau.gerenciador_de_renda.security.ValidateAccessService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("deprecation")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ClientController.class)
@ActiveProfiles("test")
@DisplayName("Testes do ClientController")
class ClientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private ValidateAccessService validateAccessService;

    @Autowired
    private ObjectMapper objectMapper;

    private ClientDTO clientDTO;
    private ClientUpdateDTO updateDTO;

    @BeforeEach
    void setUp() {
        clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        clientDTO.setName("João Silva");
        clientDTO.setEmail("joao@email.com");

        updateDTO = new ClientUpdateDTO();
        updateDTO.setName("Maria Silva");
    }

    @Test
    @DisplayName("Deve retornar cliente por ID")
    void shouldReturnClientById() throws Exception {
        when(clientService.getClientById(1L)).thenReturn(clientDTO);

        mockMvc.perform(get("/clients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));

        verify(validateAccessService).validateClientAccess(eq(1L), any());
        verify(clientService).getClientById(1L);
    }

    @Test
    @DisplayName("Deve atualizar cliente com sucesso")
    void shouldUpdateClientSuccessfully() throws Exception {
        ClientDTO updatedClient = new ClientDTO();
        updatedClient.setId(1L);
        updatedClient.setName("Maria Silva");
        updatedClient.setEmail("joao@email.com");

        when(clientService.updateClient(eq(1L), any(ClientUpdateDTO.class))).thenReturn(updatedClient);

        mockMvc.perform(patch("/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Maria Silva"));

        verify(validateAccessService).validateClientAccess(eq(1L), any());
        verify(clientService).updateClient(eq(1L), any(ClientUpdateDTO.class));
    }

    @Test
    @DisplayName("Deve deletar cliente com sucesso")
    void shouldDeleteClientSuccessfully() throws Exception {
        doNothing().when(clientService).deleteClient(1L);

        mockMvc.perform(delete("/clients/1"))
                .andExpect(status().isNoContent());

        verify(validateAccessService).validateClientAccess(eq(1L), any());
        verify(clientService).deleteClient(1L);
    }

    @Test
    @DisplayName("Deve falhar na atualização com dados inválidos")
    void shouldFailUpdateWithInvalidData() throws Exception {
        ClientUpdateDTO invalidDTO = new ClientUpdateDTO();
        invalidDTO.setName("Jo"); // Muito curto
        invalidDTO.setEmail("email-invalido");

        mockMvc.perform(patch("/clients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidDTO)))
                .andExpect(status().isBadRequest());

        verify(clientService, never()).updateClient(any(), any());
    }
}
