package com.ada.wallet_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ada.wallet_api.dto.DepositoRequestDTO;
import com.ada.wallet_api.model.Conta;
import com.ada.wallet_api.service.ContaService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/contas")
public class ContaController {
    
    private final ContaService contaService;
    
    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @GetMapping("/{id}/saldo")
    public ResponseEntity<Double> consultarSaldo(@PathVariable("id") Long id) {
        return ResponseEntity.ok(contaService.consultarSaldo(id));
    }

    @PostMapping("/{id}/depositar")
    public ResponseEntity<Conta> depositar(@PathVariable("id") Long id, @RequestBody DepositoRequestDTO dto) {
        //contaService.depositar(id, dto.getValor());
        return new ResponseEntity<>(contaService.depositar(id, dto.getValor()), HttpStatus.OK);
    }
    
}
