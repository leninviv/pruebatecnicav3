package com.example.pruebatecnica.report.dto;

import java.time.Instant;

public class KardexMovementResponse {

    private String type;
    private Integer quantity;
    private Instant date;
    private Integer effectOnStock;

    // constructor utilizado para construir la respuesta de cada movimiento del kardex
    public KardexMovementResponse(String type, Integer quantity, Instant date, Integer effectOnStock) {
        this.type = type;
        this.quantity = quantity;
        this.date = date;
        this.effectOnStock = effectOnStock;
    }

    public String getType() {
        return type;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public Instant getDate() {
        return date;
    }

    public Integer getEffectOnStock() {
        return effectOnStock;
    }
}