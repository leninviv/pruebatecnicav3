package com.example.pruebatecnica.inventory;

import com.example.pruebatecnica.exception.InsufficientStockException;
import com.example.pruebatecnica.exception.ProductNotFoundException;
import com.example.pruebatecnica.product.Product;
import com.example.pruebatecnica.product.ProductRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("StockService unit tests")
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
        product.setCode("PROD-001");
        product.setName("Teclado Logitech K120");
        product.setPrice(new BigDecimal("25.50"));
        product.setStock(50);
        product.setActive(true);
    }

    // ─── register: IN ────────────────────────────────────────────────────────

    @Test
    @DisplayName("Movimiento IN incrementa el stock del producto")
    void register_IN_incrementsStock() {
        StockMovementRequest request = new StockMovementRequest();
        request.setType("IN");
        request.setQuantity(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));
        when(stockMovementRepository.save(any(StockMovement.class))).thenAnswer(inv -> inv.getArgument(0));

        Product result = stockService.register(1L, request);

        assertThat(result.getStock()).isEqualTo(60);
    }

    @Test
    @DisplayName("Movimiento IN persiste el registro de movimiento")
    void register_IN_persistsMovementRecord() {
        StockMovementRequest request = new StockMovementRequest();
        request.setType("IN");
        request.setQuantity(10);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));
        when(stockMovementRepository.save(any(StockMovement.class))).thenAnswer(inv -> inv.getArgument(0));

        stockService.register(1L, request);

        ArgumentCaptor<StockMovement> captor = ArgumentCaptor.forClass(StockMovement.class);
        verify(stockMovementRepository).save(captor.capture());
        assertThat(captor.getValue().getType()).isEqualTo("IN");
        assertThat(captor.getValue().getQuantity()).isEqualTo(10);
    }

    // ─── register: OUT ───────────────────────────────────────────────────────

    @Test
    @DisplayName("Movimiento OUT reduce el stock del producto")
    void register_OUT_decrementsStock() {
        StockMovementRequest request = new StockMovementRequest();
        request.setType("OUT");
        request.setQuantity(20);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));
        when(stockMovementRepository.save(any(StockMovement.class))).thenAnswer(inv -> inv.getArgument(0));

        Product result = stockService.register(1L, request);

        assertThat(result.getStock()).isEqualTo(30);
    }

    @Test
    @DisplayName("Movimiento OUT persiste el registro de movimiento")
    void register_OUT_persistsMovementRecord() {
        StockMovementRequest request = new StockMovementRequest();
        request.setType("OUT");
        request.setQuantity(5);

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));
        when(stockMovementRepository.save(any(StockMovement.class))).thenAnswer(inv -> inv.getArgument(0));

        stockService.register(1L, request);

        ArgumentCaptor<StockMovement> captor = ArgumentCaptor.forClass(StockMovement.class);
        verify(stockMovementRepository).save(captor.capture());
        assertThat(captor.getValue().getType()).isEqualTo("OUT");
        assertThat(captor.getValue().getQuantity()).isEqualTo(5);
    }

    @Test
    @DisplayName("Movimiento OUT exactamente al stock disponible deja stock en 0")
    void register_OUT_exactStock_leavesZero() {
        StockMovementRequest request = new StockMovementRequest();
        request.setType("OUT");
        request.setQuantity(50); // exactly all stock

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenAnswer(inv -> inv.getArgument(0));
        when(stockMovementRepository.save(any(StockMovement.class))).thenAnswer(inv -> inv.getArgument(0));

        Product result = stockService.register(1L, request);

        assertThat(result.getStock()).isZero();
    }

    // ─── register: stock negativo ─────────────────────────────────────────────

    @Test
    @DisplayName("Movimiento OUT que supera el stock lanza InsufficientStockException")
    void register_OUT_exceedingStock_throwsInsufficientStockException() {
        StockMovementRequest request = new StockMovementRequest();
        request.setType("OUT");
        request.setQuantity(100); // more than available (50)

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        assertThatThrownBy(() -> stockService.register(1L, request))
                .isInstanceOf(InsufficientStockException.class)
                .hasMessageContaining("50")  // available
                .hasMessageContaining("100"); // requested
    }

    // ─── register: producto inexistente ──────────────────────────────────────

    @Test
    @DisplayName("register con productId inexistente lanza ProductNotFoundException")
    void register_nonExistingProduct_throwsProductNotFoundException() {
        StockMovementRequest request = new StockMovementRequest();
        request.setType("IN");
        request.setQuantity(5);

        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> stockService.register(999L, request))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("999");
    }

    // ─── findByProduct ────────────────────────────────────────────────────────

    @Test
    @DisplayName("findByProduct con ID válido retorna los movimientos")
    void findByProduct_existingId_returnsMovements() {
        when(productRepository.existsById(1L)).thenReturn(true);
        when(stockMovementRepository.findByProduct_IdOrderByIdDesc(1L)).thenReturn(List.of());

        List<StockMovement> result = stockService.findByProduct(1L);

        assertThat(result).isNotNull();
        verify(stockMovementRepository).findByProduct_IdOrderByIdDesc(1L);
    }

    @Test
    @DisplayName("findByProduct con ID inexistente lanza ProductNotFoundException")
    void findByProduct_nonExistingId_throwsProductNotFoundException() {
        when(productRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> stockService.findByProduct(999L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("999");
    }
}
