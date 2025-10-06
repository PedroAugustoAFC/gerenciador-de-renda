package com.nassau.gerenciador_de_renda.client.dto;

import com.nassau.gerenciador_de_renda.api.client.dto.ClientUpdateDTO;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Testes do ClientUpdateDTO")
class ClientUpdateDTOTest {

    private Validator validator;
    private ClientUpdateDTO dto;

    @BeforeEach
    void setUp() {
        validator = Validation.buildDefaultValidatorFactory().getValidator();
        dto = new ClientUpdateDTO();
    }

    @Test
    @DisplayName("Deve permitir DTO vazio para update parcial")
    void shouldAllowEmptyDTOForPartialUpdate() {
        Set<ConstraintViolation<ClientUpdateDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }

    @Test
    @DisplayName("Deve validar nome quando fornecido")
    void shouldValidateNameWhenProvided() {
        dto.setName("Jo");
        Set<ConstraintViolation<ClientUpdateDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Nome deve ter no minimo 3")));
    }

    @Test
    @DisplayName("Deve validar email quando fornecido")
    void shouldValidateEmailWhenProvided() {
        dto.setEmail("email-invalido");
        Set<ConstraintViolation<ClientUpdateDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Email invalido")));
    }

    @Test
    @DisplayName("Deve validar senha quando fornecida")
    void shouldValidatePasswordWhenProvided() {
        dto.setPassword("123");
        Set<ConstraintViolation<ClientUpdateDTO>> violations = validator.validate(dto);
        assertFalse(violations.isEmpty());
        assertTrue(violations.stream().anyMatch(v -> v.getMessage().contains("Senha deve ter no minimo 8 caracteres")));
    }

    @Test
    @DisplayName("Deve aceitar dados válidos")
    void shouldAcceptValidData() {
        dto.setName("Maria Silva");
        dto.setEmail("maria@email.com");
        dto.setPassword("MinhaSenh@456");

        Set<ConstraintViolation<ClientUpdateDTO>> violations = validator.validate(dto);
        assertTrue(violations.isEmpty());
    }
}

