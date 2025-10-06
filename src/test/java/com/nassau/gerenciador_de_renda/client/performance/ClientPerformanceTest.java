package com.nassau.gerenciador_de_renda.client.performance;

import com.nassau.gerenciador_de_renda.api.client.model.Client;
import com.nassau.gerenciador_de_renda.api.client.repository.ClientRepository;
import com.nassau.gerenciador_de_renda.api.client.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StopWatch;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Testes de Performance - Cliente")
class ClientPerformanceTest {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @BeforeEach
    @Transactional
    void setUp() {
        clientRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar múltiplos clientes em tempo aceitável")
    void shouldCreateMultipleClientsInAcceptableTime() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<Client> clients = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Client client = new Client();
            client.setName("Cliente ");
            client.setEmail("cliente" + i + "@email.com");
            client.setPassword("Password@" + i);
            clients.add(client);
        }

        clients.forEach(clientService::createClient);

        stopWatch.stop();

        // Deve levar menos que 5 segundos para criar 100 clientes
        assertTrue(stopWatch.getTotalTimeMillis() < 5000,
                "Criação de 100 clientes levou mais que 5 segundos: " + stopWatch.getTotalTimeMillis() + "ms");

        assertEquals(100, clientRepository.count());
    }

    @Test
    @DisplayName("Deve buscar clientes concorrentemente sem problemas")
    void shouldFindClientsConcurrentlyWithoutIssues() throws Exception {
        // Criar alguns clientes primeiro
        List<Long> clientIds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Client client = new Client();
            client.setName("Cliente ");
            client.setEmail("cliente" + i + "@email.com");
            client.setPassword("Password@" + i);
            Client saved = clientRepository.save(client);
            clientIds.add(saved.getId());
        }

        ExecutorService executor = Executors.newFixedThreadPool(10);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<CompletableFuture<Void>> futures = new ArrayList<>();

        // 100 buscas concorrentes
        for (int i = 0; i < 100; i++) {
            Long clientId = clientIds.get(i % clientIds.size());
            CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                assertDoesNotThrow(() -> clientService.getClientById(clientId));
            }, executor);
            futures.add(future);
        }

        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        stopWatch.stop();
        executor.shutdown();

        // 100 buscas concorrentes devem levar menos que 3 segundos
        assertTrue(stopWatch.getTotalTimeMillis() < 3000,
                "100 buscas concorrentes levaram mais que 3 segundos: " + stopWatch.getTotalTimeMillis() + "ms");
    }
}
