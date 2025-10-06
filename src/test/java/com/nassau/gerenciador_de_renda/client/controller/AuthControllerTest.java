package com.nassau.gerenciador_de_renda.client.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nassau.gerenciador_de_renda.api.client.controller.AuthController;
import com.nassau.gerenciador_de_renda.api.client.dto.ClientDTO;
import com.nassau.gerenciador_de_renda.api.client.model.Client;
import com.nassau.gerenciador_de_renda.api.client.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Map;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SuppressWarnings("deprecation")
@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(AuthController.class)
@ActiveProfiles("test")
@DisplayName("Testes do AuthController")
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClientService clientService;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    private Client client;
    private ClientDTO clientDTO;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        client.setName("João Silva");
        client.setEmail("joao@email.com");
        client.setPassword("MinhaSenh@123");

        clientDTO = new ClientDTO();
        clientDTO.setId(1L);
        clientDTO.setName("João Silva");
        clientDTO.setEmail("joao@email.com");
    }

    @Test
    @DisplayName("Deve registrar cliente com sucesso")
    void shouldRegisterClientSuccessfully() throws Exception {
        when(clientService.createClient(any(Client.class))).thenReturn(clientDTO);

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));

        verify(clientService).createClient(any(Client.class));
    }

    @Test
    @DisplayName("Deve falhar no registro com dados inválidos")
    void shouldFailRegisterWithInvalidData() throws Exception {
        Client invalidClient = new Client();
        invalidClient.setName("Jo"); // Muito curto
        invalidClient.setEmail("email-invalido");
        invalidClient.setPassword("123"); // Muito curta

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidClient)))
                .andExpect(status().isBadRequest());

        verify(clientService, never()).createClient(any());
    }

    @Test
    @DisplayName("Deve fazer login com sucesso")
    void shouldLoginSuccessfully() throws Exception {
        Map<String, String> loginRequest = Map.of(
                "email", "joao@email.com",
                "password", "MinhaSenh@123"
        );

        when(clientService.getClientByEmail("joao@email.com")).thenReturn(client);
        when(passwordEncoder.matches("MinhaSenh@123", client.getPassword())).thenReturn(true);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());

        verify(clientService).getClientByEmail("joao@email.com");
        verify(passwordEncoder).matches("MinhaSenh@123", client.getPassword());
    }

    @Test
    @DisplayName("Deve falhar no login com credenciais inválidas")
    void shouldFailLoginWithInvalidCredentials() throws Exception {
        Map<String, String> loginRequest = Map.of(
                "email", "joao@email.com",
                "password", "senhaErrada"
        );

        when(clientService.getClientByEmail("joao@email.com")).thenReturn(client);
        when(passwordEncoder.matches("senhaErrada", client.getPassword())).thenReturn(false);

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Credenciais inválidas"));
    }
}


