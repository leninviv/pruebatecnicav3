package com.example.pruebatecnica.inventory;

import com.example.pruebatecnica.exception.InsufficientStockException;
import com.example.pruebatecnica.exception.ProductNotFoundException;
import com.example.pruebatecnica.product.Product;
import com.example.pruebatecnica.product.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StockService {

    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    public StockService(ProductRepository productRepository, StockMovementRepository stockMovementRepository) {
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    /**
     * Registers a stock movement (IN or OUT) for the given product.
     *
     * Bug fixed: the original code used `type == "OUT"` (reference comparison),
     * which always evaluates to false in Java. Changed to "OUT".equals(type).
     *
     * Bug fixed: the original code never persisted the StockMovement record.
     * Now both the product stock and the movement are saved atomically.
     *
     * @throws ProductNotFoundException   if the product does not exist (→ 404)
     * @throws InsufficientStockException if an OUT movement would leave negative stock (→ 400)
     */
    @Transactional
    public Product register(Long productId, StockMovementRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(productId));

        String type = request.getType();
        Integer quantity = request.getQuantity();

        if ("OUT".equals(type)) {
            int updatedStock = product.getStock() - quantity;
            if (updatedStock < 0) {
                throw new InsufficientStockException(product.getStock(), quantity);
            }
            product.setStock(updatedStock);
        } else {
            product.setStock(product.getStock() + quantity);
        }

        Product saved = productRepository.save(product);

        // Persist the movement record (was missing in the original implementation)
        StockMovement movement = new StockMovement();
        movement.setProduct(saved);
        movement.setType(type);
        movement.setQuantity(quantity);
        stockMovementRepository.save(movement);

        return saved;
    }

    /**
     * Returns stock movement history for a product, ordered by most recent first.
     *
     * @throws ProductNotFoundException if the product does not exist (→ 404)
     */
    public List<StockMovement> findByProduct(Long productId) {
        if (!productRepository.existsById(productId)) {
            throw new ProductNotFoundException(productId);
        }
        return stockMovementRepository.findByProduct_IdOrderByIdDesc(productId);
    }
}

