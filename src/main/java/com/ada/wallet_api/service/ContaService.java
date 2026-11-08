package com.ada.wallet_api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public Conta depositar(Long contaId, Double valor) {
        if(valor == null || valor <= 0) {
            throw new IllegalArgumentException("O valor do depósito deve ser maior que zero");
        }
        Conta conta = contaRepository.findById(contaId).orElseThrow(() -> new IllegalArgumentException("Conta não encontrada"));

        conta.setSaldo(conta.getSaldo() + valor);
        return contaRepository.save(conta);
    }

    @Transactional
    public void transferir(Long contaOrigemId, Long contaDestinoId, Double valor) {
        if(valor == null || valor <= 0) {
            throw new IllegalArgumentException("O valor da transferência deve ser maior que zero");
        }

        Conta origem = contaRepository.findById(contaOrigemId).orElseThrow(() -> new IllegalArgumentException("Conta de origem não encontrada"));

        Conta destino = contaRepository.findById(contaDestinoId).orElseThrow(() -> new IllegalArgumentException("Conta de destino não encontrada"));

        origem.setSaldo(origem.getSaldo() - valor);
        destino.setSaldo(destino.getSaldo() + valor);

        contaRepository.save(origem);
        contaRepository.save(destino);
    }

}
