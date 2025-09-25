package com.nassau.gerenciador_de_renda.client.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nassau.gerenciador_de_renda.client.model.Client;
import com.nassau.gerenciador_de_renda.client.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:h2:mem:testdb",
        "spring.jpa.hibernate.ddl-auto=create-drop"
})
@Transactional
@DisplayName("Testes de Integração - Cliente")
class ClientIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Client client;

    @BeforeEach
    void setUp() {
        clientRepository.deleteAll();

        client = new Client();
        client.setName("João Silva");
        client.setEmail("joao@email.com");
        client.setPassword("MinhaSenh@123");
    }

    @Test
    @DisplayName("Deve realizar fluxo completo: registro, login e operações CRUD")
    void shouldPerformCompleteUserFlow() throws Exception {
        // 1. Registro
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("João Silva"))
                .andExpect(jsonPath("$.email").value("joao@email.com"));

        // 2. Login
        Map<String, String> loginRequest = Map.of(
                "email", "joao@email.com",
                "password", "MinhaSenh@123"
        );

        String response = mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        // Extrair token da resposta
        String token = objectMapper.readTree(response).get("token").asText();

        // 3. Buscar cliente
        Client savedClient = clientRepository.findByEmail("joao@email.com").orElseThrow();

        mockMvc.perform(get("/clients/" + savedClient.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("João Silva"));

        // 4. Atualizar cliente
        Map<String, String> updateRequest = Map.of("name", "João Santos");

        mockMvc.perform(patch("/clients/" + savedClient.getId())
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("João Santos"));

        // 5. Deletar cliente
        mockMvc.perform(delete("/clients/" + savedClient.getId())
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Deve falhar ao tentar registrar com email duplicado")
    void shouldFailToRegisterWithDuplicateEmail() throws Exception {
        // Primeiro registro
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk());

        // Segundo registro com mesmo email
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Deve falhar no login com credenciais incorretas")
    void shouldFailLoginWithWrongCredentials() throws Exception {
        // Registrar cliente
        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(client)))
                .andExpect(status().isOk());

        // Tentar login com senha incorreta
        Map<String, String> loginRequest = Map.of(
                "email", "joao@email.com",
                "password", "SenhaErrada@123"
        );

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("Deve falhar ao acessar endpoint protegido sem token")
    void shouldFailToAccessProtectedEndpointWithoutToken() throws Exception {
        Client savedClient = clientRepository.save(client);

        mockMvc.perform(get("/clients/" + savedClient.getId()))
                .andExpect(status().isForbidden());
    }
}

