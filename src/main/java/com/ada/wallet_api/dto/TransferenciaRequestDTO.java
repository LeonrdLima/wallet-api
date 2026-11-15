package com.ada.wallet_api.dto;

public class TransferenciaRequestDTO {
    
    private Long contaDestinoId;
    private Double valor;

    public TransferenciaRequestDTO() {}

    public TransferenciaRequestDTO(Long contaDestinoId, Double valor) {
        this.contaDestinoId = contaDestinoId;
        this.valor = valor;
    }

    public Long getContaDestinoId() {
        return contaDestinoId;
    }

    public void setContaDestinoId(Long contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

}
