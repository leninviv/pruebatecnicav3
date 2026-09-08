package com.example.pruebatecnica.product;

import com.example.pruebatecnica.exception.ProductNotFoundException;
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
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private ProductRequest request;

    @BeforeEach
    void setUp() {
        request = new ProductRequest();
        request.setCode("TEST-001");
        request.setName("Producto Test");
        request.setPrice(new BigDecimal("10.50"));
        request.setStock(20);
        request.setActive(true);
    }

    @Test
    void shouldCreateProduct() {
        when(productRepository.save(any(Product.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        Product result = productService.create(request);

        assertNotNull(result);
        assertEquals("TEST-001", result.getCode());
        assertEquals("Producto Test", result.getName());
        assertEquals(new BigDecimal("10.50"), result.getPrice());
        assertEquals(20, result.getStock());
        assertTrue(result.getActive());

        verify(productRepository).save(any(Product.class));
    }

    @Test
    void shouldGetProductById() {
        Product product = new Product();
        product.setId(1L);
        product.setCode("TEST-001");
        product.setName("Producto Test");
        product.setPrice(new BigDecimal("10.50"));
        product.setStock(20);
        product.setActive(true);

        when(productRepository.findById(1L))
                .thenReturn(Optional.of(product));

        Product result = productService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("TEST-001", result.getCode());
        assertEquals("Producto Test", result.getName());

        verify(productRepository).findById(1L);
    }

    @Test
    void shouldThrowExceptionWhenProductDoesNotExist() {
        when(productRepository.findById(999L))
                .thenReturn(Optional.empty());

        assertThrows(
                ProductNotFoundException.class,
                () -> productService.getById(999L)
        );

        verify(productRepository).findById(999L);
    }
}