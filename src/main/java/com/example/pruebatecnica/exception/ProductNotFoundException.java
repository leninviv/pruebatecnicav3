package com.example.pruebatecnica.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(Long id) {
        super("No se encontro el producto del id: " + id);
    }
}
