package com.ada.wallet_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.ada.wallet_api.model.Conta;
import com.ada.wallet_api.repository.ContaRepository;

@ExtendWith(MockitoExtension.class)
public class ContaServiceTest {
    
    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ContaService contaService;

    @Test
    @DisplayName("Deve retornar o saldo correto ao consultar saldo de uma conta existente")
    void deveRetornarSaldoQuandoContaExistir() {
    // Arrange
    Long contaId = 1L;
    Conta contaMock = new Conta(contaId, "Leonardo", 150.0);
    when(contaRepository.findById(contaId)).thenReturn(Optional.of(contaMock));

    // Act
    Double saldo = contaService.consultarSaldo(contaId);

    // Assert
    assertEquals(150, saldo);
    }
    
}
