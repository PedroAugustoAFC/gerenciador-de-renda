package com.nassau.gerenciador_de_renda.client.service;

import com.nassau.gerenciador_de_renda.client.dto.ClientDTO;
import com.nassau.gerenciador_de_renda.client.dto.ClientUpdateDTO;
import com.nassau.gerenciador_de_renda.client.model.Client;
import com.nassau.gerenciador_de_renda.client.repository.ClientRepository;
import com.nassau.gerenciador_de_renda.exceptions.ResourceAlreadyRegisteredException;
import com.nassau.gerenciador_de_renda.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ClientService")
class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ClientService clientService;

    private Client client;
    private ClientUpdateDTO updateDTO;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        client.setName("João Silva");
        client.setEmail("joao@email.com");
        client.setPassword("MinhaSenh@123");

        updateDTO = new ClientUpdateDTO();
    }

    @Test
    @DisplayName("Deve retornar cliente por ID")
    void shouldReturnClientById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        ClientDTO result = clientService.getClientById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("João Silva", result.getName());
        assertEquals("joao@email.com", result.getEmail());
        verify(clientRepository).findById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando cliente não encontrado por ID")
    void shouldThrowExceptionWhenClientNotFoundById() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> clientService.getClientById(1L)
        );

        assertEquals("Cliente com id 1 nao encontrado", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar cliente por email")
    void shouldReturnClientByEmail() {
        when(clientRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(client));

        Client result = clientService.getClientByEmail("joao@email.com");

        assertNotNull(result);
        assertEquals("João Silva", result.getName());
        verify(clientRepository).findByEmail("joao@email.com");
    }

    @Test
    @DisplayName("Deve lançar exceção quando email não cadastrado")
    void shouldThrowExceptionWhenEmailNotRegistered() {
        when(clientRepository.findByEmail("joao@email.com")).thenReturn(Optional.empty());

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> clientService.getClientByEmail("joao@email.com")
        );

        assertEquals("Email não cadastrado", exception.getMessage());
    }

    @Test
    @DisplayName("Deve criar cliente com sucesso")
    void shouldCreateClientSuccessfully() {
        when(clientRepository.existsByEmail("joao@email.com")).thenReturn(false);
        when(passwordEncoder.encode("MinhaSenh@123")).thenReturn("senhaEncriptada");
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        ClientDTO result = clientService.createClient(client);

        assertNotNull(result);
        assertEquals("João Silva", result.getName());
        assertEquals("joao@email.com", result.getEmail());
        verify(clientRepository).existsByEmail("joao@email.com");
        verify(passwordEncoder).encode("MinhaSenh@123");
        verify(clientRepository).save(client);
    }

    @Test
    @DisplayName("Deve lançar exceção quando email já existe na criação")
    void shouldThrowExceptionWhenEmailExistsOnCreate() {
        when(clientRepository.existsByEmail("joao@email.com")).thenReturn(true);

        ResourceAlreadyRegisteredException exception = assertThrows(
                ResourceAlreadyRegisteredException.class,
                () -> clientService.createClient(client)
        );

        assertEquals("Email ja esta em uso", exception.getMessage());
        verify(clientRepository, never()).save(any());
    }

    @Test
    @DisplayName("Deve atualizar nome do cliente")
    void shouldUpdateClientName() {
        updateDTO.setName("Maria Silva");
        Client updatedClient = new Client();
        updatedClient.setId(1L);
        updatedClient.setName("Maria Silva");
        updatedClient.setEmail("joao@email.com");
        updatedClient.setPassword("MinhaSenh@123");

        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.save(any(Client.class))).thenReturn(updatedClient);

        ClientDTO result = clientService.updateClient(1L, updateDTO);

        assertEquals("Maria Silva", result.getName());
        verify(clientRepository).findById(1L);
        verify(clientRepository).save(any(Client.class));
    }

    @Test
    @DisplayName("Deve atualizar email do cliente")
    void shouldUpdateClientEmail() {
        updateDTO.setEmail("novo@email.com");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientRepository.existsByEmail("novo@email.com")).thenReturn(false);
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        ClientDTO result = clientService.updateClient(1L, updateDTO);

        assertNotNull(result);
        verify(clientRepository).existsByEmail("novo@email.com");
    }

    @Test
    @DisplayName("Deve lançar exceção quando email igual ao anterior")
    void shouldThrowExceptionWhenEmailSameAsPrevious() {
        updateDTO.setEmail("joao@email.com");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        ResourceAlreadyRegisteredException exception = assertThrows(
                ResourceAlreadyRegisteredException.class,
                () -> clientService.updateClient(1L, updateDTO)
        );

        assertEquals("Email igual ao anterior", exception.getMessage());
    }

    @Test
    @DisplayName("Deve atualizar senha do cliente")
    void shouldUpdateClientPassword() {
        updateDTO.setPassword("NovaSenha@456");
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(passwordEncoder.encode("NovaSenha@456")).thenReturn("novaSenhaEncriptada");
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        ClientDTO result = clientService.updateClient(1L, updateDTO);

        assertNotNull(result);
        verify(passwordEncoder).encode("NovaSenha@456");
    }

    @Test
    @DisplayName("Deve deletar cliente com sucesso")
    void shouldDeleteClientSuccessfully() {
        when(clientRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> clientService.deleteClient(1L));

        verify(clientRepository).existsById(1L);
        verify(clientRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Deve lançar exceção quando tentar deletar cliente inexistente")
    void shouldThrowExceptionWhenTryingToDeleteNonExistentClient() {
        when(clientRepository.existsById(1L)).thenReturn(false);

        ResourceNotFoundException exception = assertThrows(
                ResourceNotFoundException.class,
                () -> clientService.deleteClient(1L)
        );

        assertEquals("Cliente com id 1 nao encontrado", exception.getMessage());
        verify(clientRepository, never()).deleteById(any());
    }
}
