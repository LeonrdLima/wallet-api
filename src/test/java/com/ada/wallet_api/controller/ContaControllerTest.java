package com.ada.wallet_api.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.ada.wallet_api.service.ContaService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContaController.class)
public class ContaControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

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

}
