package com.example.pruebatecnica.exception;

public class InsufficientStockException extends RuntimeException {

    public InsufficientStockException(Integer available, Integer requested) {
        super("Insufficient stock. Available: " + available + ", requested: " + requested);
    }
}
