package com.example.pruebatecnica.report;

import com.example.pruebatecnica.inventory.StockMovementRepository;
import com.example.pruebatecnica.product.Product;
import com.example.pruebatecnica.product.ProductRepository;
import com.example.pruebatecnica.report.dto.KardexMovementResponse;
import com.example.pruebatecnica.report.dto.KardexResponse;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class KardexService {

    private final ProductRepository productRepository;
    private final StockMovementRepository stockMovementRepository;

    public KardexService(ProductRepository productRepository, StockMovementRepository stockMovementRepository) {
        this.productRepository = productRepository;
        this.stockMovementRepository = stockMovementRepository;
    }

    public List<KardexResponse> findAll(Long productId) {

        List<Product> products;

         // si se recibe un ID se genera el kardex unicamente para ese producto
        if (productId != null) {
            products = List.of(
                productRepository.findById(productId)
                    .orElseThrow(()->new com.example.pruebatecnica.exception.ProductNotFoundException(productId))
            );
        } else {
            // si no se recibe un ID se genera el kardex de todos los productos
            products = productRepository.findAll();
        }

        return products.stream().map(this::buildKardex).toList();
    }

    private KardexResponse buildKardex(Product product) {

        List<KardexMovementResponse> movements = 
                stockMovementRepository
                        .findByProduct_IdOrderByIdDesc(product.getId())
                        .stream()
                        .map(movement -> {
                            // las entradas aumentan el stock y las salidas lo disminuyen
                            int effect = "IN".equals(movement.getType())
                                    ? movement.getQuantity()
                                    : -movement.getQuantity();

                            return new KardexMovementResponse(
                                    movement.getType(),
                                    movement.getQuantity(),
                                    movement.getCreatedAt(),
                                    effect
                            );
                        })
                        .toList();
                        
        // se combinan los datos actuales del producto con el historial de movimientos
        return new KardexResponse(
                product.getId(),
                product.getCode(),
                product.getName(),
                product.getPrice(),
                product.getStock(),
                product.getActive(),
                movements
        );
    }
}