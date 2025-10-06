package com.nassau.gerenciador_de_renda.client.service;

import com.nassau.gerenciador_de_renda.api.client.model.Client;
import com.nassau.gerenciador_de_renda.api.client.repository.ClientRepository;
import com.nassau.gerenciador_de_renda.api.client.service.ClientDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes do ClientDetailsService")
class ClientDetailsServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientDetailsService clientDetailsService;

    private Client client;

    @BeforeEach
    void setUp() {
        client = new Client();
        client.setId(1L);
        client.setName("João Silva");
        client.setEmail("joao@email.com");
        client.setPassword("senhaEncriptada");
    }

    @Test
    @DisplayName("Deve carregar usuário por email")
    void shouldLoadUserByUsername() {
        when(clientRepository.findByEmail("joao@email.com")).thenReturn(Optional.of(client));

        UserDetails userDetails = clientDetailsService.loadUserByUsername("joao@email.com");

        assertNotNull(userDetails);
        assertEquals("joao@email.com", userDetails.getUsername());
        assertEquals("senhaEncriptada", userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream()
                .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")));
        verify(clientRepository).findByEmail("joao@email.com");
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não encontrado")
    void shouldThrowExceptionWhenUserNotFound() {
        when(clientRepository.findByEmail("joao@email.com")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(
                UsernameNotFoundException.class,
                () -> clientDetailsService.loadUserByUsername("joao@email.com")
        );

        assertEquals("Usuário não encontrado", exception.getMessage());
    }
}
