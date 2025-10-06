package com.nassau.gerenciador_de_renda.client.model;

import com.nassau.gerenciador_de_renda.api.client.model.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do Modelo Client")
class ClientTest {

    private Validator validator;
    private Client client;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        client = new Client();
        client.setName("João Silva");
        client.setEmail("joao@email.com");
        client.setPassword("MinhaSenh@123");
    }

    @Test
    @DisplayName("Deve criar cliente com dados válidos")
    void shouldCreateValidClient() {
        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Deve falhar com nome muito curto")
    void shouldFailWithShortName() {
        client.setName("Jo");
        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Nome deve ter no minimo 3")));
    }

    @Test
    @DisplayName("Deve falhar com nome contendo números")
    void shouldFailWithInvalidNamePattern() {
        client.setName("João123");
        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Nome deve conter apenas letras")));
    }

    @Test
    @DisplayName("Deve falhar com email inválido")
    void shouldFailWithInvalidEmail() {
        client.setEmail("email-invalido");
        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Email invalido")));
    }

    @Test
    @DisplayName("Deve falhar com senha muito curta")
    void shouldFailWithShortPassword() {
        client.setPassword("123");
        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Senha deve ter no minimo 8 caracteres")));
    }

    @Test
    @DisplayName("Deve falhar com senha sem requisitos")
    void shouldFailWithWeakPassword() {
        client.setPassword("12345678");
        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("A senha deve conter pelo menos uma letra, um número e um símbolo")));
    }

    @Test
    @DisplayName("Deve aceitar senha forte")
    void shouldAcceptStrongPassword() {
        client.setPassword("MinhaSenh@123");
        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        assertTrue(violations.isEmpty());
    }
}
