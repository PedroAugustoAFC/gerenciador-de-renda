package com.nassau.gerenciador_de_renda.client.repository;

import com.nassau.gerenciador_de_renda.api.client.model.Client;
import com.nassau.gerenciador_de_renda.api.client.repository.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DisplayName("Testes do ClientRepository")
class ClientRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setName("João Silva");
        client.setEmail("joao@email.com");
        client.setPassword("MinhaSenh@123");
    }

    @Test
    @DisplayName("Deve salvar e recuperar cliente")
    void shouldSaveAndRetrieveClient() {
        Client savedClient = entityManager.persistAndFlush(client);
        Optional<Client> found = clientRepository.findById(savedClient.getId());

        assertTrue(found.isPresent());
        assertEquals("João Silva", found.get().getName());
        assertEquals("joao@email.com", found.get().getEmail());
    }

    @Test
    @DisplayName("Deve encontrar cliente por email")
    void shouldFindClientByEmail() {
        entityManager.persistAndFlush(client);
        Optional<Client> found = clientRepository.findByEmail("joao@email.com");

        assertTrue(found.isPresent());
        assertEquals("João Silva", found.get().getName());
    }

    @Test
    @DisplayName("Deve retornar vazio quando email não existe")
    void shouldReturnEmptyWhenEmailNotExists() {
        Optional<Client> found = clientRepository.findByEmail("naoexiste@email.com");
        assertFalse(found.isPresent());
    }

    @Test
    @DisplayName("Deve verificar se email existe")
    void shouldCheckIfEmailExists() {
        entityManager.persistAndFlush(client);

        assertTrue(clientRepository.existsByEmail("joao@email.com"));
        assertFalse(clientRepository.existsByEmail("naoexiste@email.com"));
    }

    @Test
    @DisplayName("Deve deletar cliente")
    void shouldDeleteClient() {
        Client savedClient = entityManager.persistAndFlush(client);
        Long clientId = savedClient.getId();

        clientRepository.deleteById(clientId);
        entityManager.flush();

        Optional<Client> found = clientRepository.findById(clientId);
        assertFalse(found.isPresent());
    }
}
