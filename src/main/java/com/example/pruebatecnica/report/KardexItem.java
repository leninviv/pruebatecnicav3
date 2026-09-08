package com.example.pruebatecnica.report;

import com.example.pruebatecnica.inventory.StockMovement;
import com.example.pruebatecnica.product.Product;
import java.math.BigDecimal;
import java.util.List;

/**
 * DTO representing a single product's kardex entry.
 * Combines product data with its stock movement history and running balance.
 */
public class KardexItem {

    private Long productId;
    private String code;
    private String name;
    private BigDecimal price;
    private Integer currentStock;
    private Boolean active;
    private List<MovementDetail> movements;

    public KardexItem(Product product, List<StockMovement> movements) {
        this.productId = product.getId();
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
        this.currentStock = product.getStock();
        this.active = product.getActive();
        this.movements = movements.stream().map(MovementDetail::new).toList();
    }

    // Getters
    public Long getProductId() { return productId; }
    public String getCode() { return code; }
    public String getName() { return name; }
    public BigDecimal getPrice() { return price; }
    public Integer getCurrentStock() { return currentStock; }
    public Boolean getActive() { return active; }
    public List<MovementDetail> getMovements() { return movements; }

    /**
     * Inner DTO representing a single stock movement entry in the kardex.
     */
    public static class MovementDetail {

        private Long movementId;
        private String type;
        private Integer quantity;
        private String createdAt;
        /** Positive for IN, negative for OUT — shows the effect on stock */
        private Integer stockEffect;

        public MovementDetail(StockMovement movement) {
            this.movementId = movement.getId();
            this.type = movement.getType();
            this.quantity = movement.getQuantity();
            this.createdAt = movement.getCreatedAt().toString();
            this.stockEffect = "IN".equals(movement.getType()) ? movement.getQuantity() : -movement.getQuantity();
        }

        // Getters
        public Long getMovementId() { return movementId; }
        public String getType() { return type; }
        public Integer getQuantity() { return quantity; }
        public String getCreatedAt() { return createdAt; }
        public Integer getStockEffect() { return stockEffect; }
    }
}
