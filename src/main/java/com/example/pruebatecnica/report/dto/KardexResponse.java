package com.example.pruebatecnica.report.dto;

import java.math.BigDecimal;
import java.util.List;

public class KardexResponse {

    private Long productId;
    private String code;
    private String name;
    private BigDecimal price;
    private Integer stock;
    private Boolean active;
    private List<KardexMovementResponse> movements;

    // constructor utilizado para combinar la informacion del producto con sus movimientos de stock
    public KardexResponse(Long productId, String code, String name, BigDecimal price, Integer stock, Boolean active, List<KardexMovementResponse> movements) {
        this.productId = productId;
        this.code = code;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.active = active;
        this.movements = movements;
    }

    public Long getProductId() {
        return productId;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStock() {
        return stock;
    }

    public Boolean getActive() {
        return active;
    }

    public List<KardexMovementResponse> getMovements() {
        return movements;
    }
}