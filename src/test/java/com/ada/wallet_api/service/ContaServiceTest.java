package com.ada.wallet_api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    @Test
    @DisplayName("Deve realizar depósito com sucesso quando o valor for positivo")
    void deveDepositarComSucessoQuandoValorPositivo() {
        // Arrange
        Long contaId = 1L;
        Double valorDeposito = 50.0;
        Conta contaMock = new Conta(contaId, "Leonardo", 100.0);

        when(contaRepository.findById(contaId)).thenReturn(Optional.of(contaMock));
        when(contaRepository.save(any(Conta.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Conta contaAtualizada = contaService.depositar(contaId, valorDeposito);

        // Assert
        assertEquals(150, contaAtualizada.getSaldo());
        verify(contaRepository, times(1)).save(contaMock);
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException ao tentar depositar valor menor ou igual a zero")
    void deveLancarExcecaoQuandoDepositarValorInvalido() {
        // Arrange
        Long contaId = 1L;
        Double valorInvalido = 0.0;

        // Act and Assert
        assertThrows(IllegalArgumentException.class, () -> {
            contaService.depositar(contaId, valorInvalido);
        });

        // Verifica se o repositório nunca tentou salvr uma conta se o valor for inválido
        verify(contaRepository, never()).save(any(Conta.class));
    }
    
}
