package com.ada.wallet_api.dto;

public class DepositoRequestDTO {
    
    private Double valor;

    public DepositoRequestDTO() {}

    public DepositoRequestDTO(Double valor) {
        this.valor = valor;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

}
