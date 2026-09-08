package com.example.pruebatecnica.report;

import com.example.pruebatecnica.inventory.StockMovement;
import com.example.pruebatecnica.inventory.StockMovementRepository;
import com.example.pruebatecnica.product.Product;
import com.example.pruebatecnica.product.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 * Service responsible for building the kardex report.
 * Combines product information with their stock movement history.
 */
@Service
public class KardexService {

    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    public KardexService(ProductRepository productRepository,
                         StockMovementRepository stockMovementRepository) {
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    /**
     * Builds the full kardex report, optionally filtered by product ID or active state.
     *
     * @param productId optional product ID filter
     * @param active    optional active state filter
     * @return list of KardexItem, each containing product data + movement history
     */
    public List<KardexItem> buildKardex(Long productId, Boolean active) {
        List<Product> products;

        if (productId != null) {
            products = productRepository.findById(productId)
                    .map(List::of)
                    .orElse(List.of());
        } else if (active != null) {
            products = productRepository.findByActive(active);
        } else {
            products = productRepository.findAll();
        }

        return products.stream()
                .map(product -> {
                    List<StockMovement> movements =
                            stockMovementRepository.findByProduct_IdOrderByIdDesc(product.getId());
                    return new KardexItem(product, movements);
                })
                .toList();
    }
}
