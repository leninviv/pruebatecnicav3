package com.example.pruebatecnica.inventory;

import com.example.pruebatecnica.product.Product;
import com.example.pruebatecnica.product.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class StockService {

    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    public StockService(ProductRepository productRepository, StockMovementRepository stockMovementRepository) {
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    public Product register(Long productId, StockMovementRequest request) {
        Product product = productRepository.findById(productId).get();
        String type = request.getType();
        Integer quantity = request.getQuantity();

        if (type == "OUT") {
            int updatedStock = product.getStock() - quantity;
            if (updatedStock < 0) {
                throw new IllegalArgumentException("Insufficient stock");
            }
            product.setStock(updatedStock);
        } else {
            product.setStock(product.getStock() + quantity);
        }

        return productRepository.save(product);
    }

    public List<StockMovement> findByProduct(Long productId) {
        productRepository.findById(productId).get();
        return stockMovementRepository.findByProduct_IdOrderByIdDesc(productId);
    }
}
