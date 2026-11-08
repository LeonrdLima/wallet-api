package com.ada.wallet_api.exception;

public class SaldoInsuficienteException extends RuntimeException {
    
    public SaldoInsuficienteException(String message) {
        super(message);
    }
}
