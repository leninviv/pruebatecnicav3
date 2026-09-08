package com.example.pruebatecnica.inventory;

import com.example.pruebatecnica.exception.InsufficientStockException;
import com.example.pruebatecnica.exception.ProductNotFoundException;
import com.example.pruebatecnica.product.Product;
import com.example.pruebatecnica.product.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private StockMovementRepository stockMovementRepository;

    @InjectMocks
    private StockService stockService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setId(1L);
        product.setCode("TEST-001");
        product.setName("Producto Test");
        product.setPrice(new BigDecimal("10.50"));
        product.setStock(50);
        product.setActive(true);
    }

    @Test
    void shouldIncreaseStockWhenMovementIsIn() {
        StockMovementRequest request = new StockMovementRequest();
        request.setType("IN");
        request.setQuantity(10);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Product result = stockService.register(1L, request);

        assertEquals(60, result.getStock());

        verify(stockMovementRepository).save(any(StockMovement.class));
        verify(productRepository).save(product);
    }

    @Test
    void shouldDecreaseStockWhenMovementIsOut() {
        StockMovementRequest request = new StockMovementRequest();
        request.setType("OUT");
        request.setQuantity(10);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Product result = stockService.register(1L, request);

        assertEquals(40, result.getStock());

        verify(stockMovementRepository).save(any(StockMovement.class));
        verify(productRepository).save(product);
    }

    @Test
    void shouldThrowExceptionWhenStockIsInsufficient() {
        StockMovementRequest request = new StockMovementRequest();
        request.setType("OUT");
        request.setQuantity(100);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(
                InsufficientStockException.class,
                () -> stockService.register(1L, request)
        );

        assertEquals(50, product.getStock());

        verify(stockMovementRepository, never()).save(any(StockMovement.class));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {
        StockMovementRequest request = new StockMovementRequest();
        request.setType("IN");
        request.setQuantity(10);

        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> stockService.register(999L, request)
        );

        verify(stockMovementRepository, never()).save(any(StockMovement.class));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenMovementTypeIsInvalid() {
        StockMovementRequest request = new StockMovementRequest();
        request.setType("INVALID");
        request.setQuantity(10);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(
                com.example.pruebatecnica.exception.InvalidMovementException.class,
                () -> stockService.register(1L, request)
        );

        assertEquals(50, product.getStock());

        verify(stockMovementRepository, never()).save(any(StockMovement.class));
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void shouldThrowExceptionWhenQuantityIsInvalid() {
        StockMovementRequest request = new StockMovementRequest();
        request.setType("IN");
        request.setQuantity(0);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        assertThrows(
                com.example.pruebatecnica.exception.InvalidMovementException.class,
                () -> stockService.register(1L, request)
        );

        assertEquals(50, product.getStock());

        verify(stockMovementRepository, never()).save(any(StockMovement.class));
        verify(productRepository, never()).save(any(Product.class));
    }
}