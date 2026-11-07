package com.ada.wallet_api.service;

import org.springframework.stereotype.Service;

import com.ada.wallet_api.model.Conta;
import com.ada.wallet_api.repository.ContaRepository;

@Service
public class ContaService {
    
    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Double consultarSaldo(Long contaId) {
        Conta conta = contaRepository.findById(contaId).orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));
        return conta.getSaldo();
    }

}
