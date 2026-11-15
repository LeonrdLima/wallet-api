package com.ada.wallet_api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ada.wallet_api.exception.SaldoInsuficienteException;
import com.ada.wallet_api.model.Conta;
import com.ada.wallet_api.repository.ContaRepository;

@Service
public class ContaService {
    
    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {
        this.contaRepository = contaRepository;
    }

    public Double consultarSaldo(Long contaId) {
        Conta conta = buscarConta(contaId, "Conta não encontrada");
        return conta.getSaldo();
    }

    public Conta depositar(Long contaId, Double valor) {
        validarValorPositivo(valor, "O valor do depósito deve ser maior que zero");

        Conta conta = buscarConta(contaId,"Conta não encontrada");

        conta.setSaldo(conta.getSaldo() + valor);
        return contaRepository.save(conta);
    }

    @Transactional
    public void transferir(Long contaOrigemId, Long contaDestinoId, Double valor) {
        validarValorPositivo(valor, "O valor da transferência deve ser maior que zero");

        Conta origem = buscarConta(contaOrigemId, "Conta de origem não encontrada");

        validarSaldoSuficiente(origem, valor);

        Conta destino = buscarConta(contaDestinoId, "Conta de destino não encontrada");

        origem.setSaldo(origem.getSaldo() - valor);
        destino.setSaldo(destino.getSaldo() + valor);

        contaRepository.save(origem);
        contaRepository.save(destino);
    }

    private Conta buscarConta(Long id, String mensagemErro) {
        return contaRepository.findById(id).orElseThrow(() -> new IllegalArgumentException(mensagemErro));
    }

    private void validarValorPositivo(Double valor, String mensagemErro) {
        if(valor == null || valor <= 0) {
            throw new IllegalArgumentException(mensagemErro);
        }
    }

    private void validarSaldoSuficiente(Conta origem, Double valor) {
        if(origem.getSaldo() < valor) {
            throw new SaldoInsuficienteException("Saldo insuficiente para transferência");
        }
    }

}
