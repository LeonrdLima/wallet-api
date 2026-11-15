package com.ada.wallet_api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ada.wallet_api.dto.DepositoRequestDTO;
import com.ada.wallet_api.dto.TransferenciaRequestDTO;
import com.ada.wallet_api.exception.SaldoInsuficienteException;
import com.ada.wallet_api.model.Conta;
import com.ada.wallet_api.service.ContaService;

import tools.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ContaController.class)
public class ContaControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ContaService contaService;
    
    @Test
    @DisplayName("Deve retornar status 200 e o saldo quando consultar saldo de uma conta existente")
    void deveRetornarSaldoComSucesso() throws Exception {
        Long contaId = 1L;
        Double saldoSimulado = 500.0;

        Mockito.when(contaService.consultarSaldo(contaId)).thenReturn(saldoSimulado);

        mockMvc.perform(get("/contas/{id}/saldo", contaId))
               .andExpect(status().isOk())
               .andExpect(content().string("500.0"));
    }

    @Test
    @DisplayName("Deve retornar status 200 e conta atualizada ao realizar depósito")
    void deveRealizarDepositoComSucesso() throws Exception {
        Long contaId = 1L;
        DepositoRequestDTO dto = new DepositoRequestDTO(100.0);
        Conta contaAtualizada = new Conta(contaId, "Leonardo", 500.0);

        Mockito.when(contaService.depositar(contaId, 100.0)).thenReturn(contaAtualizada);

        mockMvc.perform(post("/contas/{id}/depositar", contaId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(contaId))
               .andExpect(jsonPath("$.saldo").value(500.0));
    }

    @Test
    @DisplayName("Deve retornar status 200 ao realizar transferência")
    void deveRealizarTransferenciaComSucesso() throws Exception {
        Long contaOrigemId = 1L;
        Long contaDestinoId = 2L;
        TransferenciaRequestDTO dto = new TransferenciaRequestDTO(contaDestinoId, 150.0);

        Mockito.doNothing().when(contaService).transferir(contaOrigemId, contaDestinoId, 150.0);

        mockMvc.perform(post("/contas/{id}/transferir", contaOrigemId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
               .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar status 400 ao tentar transferir com saldo insuficiente")
    void deveRetornar400QuandoSaldoInsuficiente() throws Exception {
        Long contaOrigemId = 1L;
        Long contaDestinoId = 2L;
        TransferenciaRequestDTO dto = new TransferenciaRequestDTO(contaDestinoId, 500.0);

        Mockito.doThrow(new SaldoInsuficienteException("Saldo insuficiente para transferência"))
               .when(contaService).transferir(contaOrigemId, contaDestinoId, 500.0);

        mockMvc.perform(post("/contas/{id}/transferir", contaOrigemId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
               .andExpect(status().isBadRequest());
    }

}
