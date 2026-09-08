package com.example.pruebatecnica.inventory;

import com.example.pruebatecnica.exception.InsufficientStockException;
import com.example.pruebatecnica.exception.InvalidMovementException;
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

    @Transactional
    public Product register(Long productId, StockMovementRequest request) {
        // validacion de existencia de producto
        Product product = productRepository.findById(productId).orElseThrow(()->new ProductNotFoundException(productId));
        
        if (request == null) {
            throw new InvalidMovementException("El movimiento es obligatorio");
        }

        String type = request.getType();
        Integer quantity = request.getQuantity();

        // validacion de valor de cantidad validad
        if(quantity==null || quantity<=0){
            throw new InvalidMovementException("La cantidad debe de ser mayor a cero");
        }

        // con equals ahora compara el texto y no si son el mismo objeto
        // fix: ahora se valida que solo exista IN y OUT
        if ("IN".equals(type)){
            product.setStock(product.getStock() + quantity);
        } else if ("OUT".equals(type)) {
            if (product.getStock() < quantity) {
                throw new InsufficientStockException("Stock insuficiente");
            }
            product.setStock(product.getStock() - quantity);
        } else {
            throw new InvalidMovementException("Tipo invalido solo puede ser entrada 'IN' o salida 'OUT'");
        }

        //fix: ahora se guarda el movimiento

        StockMovement movement = new StockMovement();

        movement.setProduct(product);
        movement.setType(type);
        movement.setQuantity(quantity);

        stockMovementRepository.save(movement);

        return productRepository.save(product);
    }

    public List<StockMovement> findByProduct(Long productId) {
        // validacion de existencia de producto
        productRepository.findById(productId).orElseThrow(() -> new ProductNotFoundException(productId));
        return stockMovementRepository.findByProduct_IdOrderByIdDesc(productId);
    }
}
