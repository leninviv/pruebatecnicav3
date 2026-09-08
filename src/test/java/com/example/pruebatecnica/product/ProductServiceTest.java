package com.example.pruebatecnica.product;

import com.example.pruebatecnica.exception.ProductNotFoundException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService unit tests")
class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product sampleProduct;

    @BeforeEach
    void setUp() {
        sampleProduct = new Product();
        sampleProduct.setId(1L);
        sampleProduct.setCode("PROD-001");
        sampleProduct.setName("Teclado Logitech K120");
        sampleProduct.setPrice(new BigDecimal("25.50"));
        sampleProduct.setStock(50);
        sampleProduct.setActive(true);
    }

    // ─── findAll ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findAll sin filtros retorna todos los productos")
    void findAll_noFilters_returnsAll() {
        when(productRepository.findAll()).thenReturn(List.of(sampleProduct));

        List<Product> result = productService.findAll(null, null);

        assertThat(result).hasSize(1);
        verify(productRepository).findAll();
    }

    @Test
    @DisplayName("findAll con filtro de nombre usa búsqueda parcial")
    void findAll_byName_usesContainingSearch() {
        when(productRepository.findByNameContainingIgnoreCase("teclado")).thenReturn(List.of(sampleProduct));

        List<Product> result = productService.findAll("teclado", null);

        assertThat(result).hasSize(1);
        verify(productRepository).findByNameContainingIgnoreCase("teclado");
    }

    @Test
    @DisplayName("findAll con filtro active usa findByActive")
    void findAll_byActive_usesActiveFilter() {
        when(productRepository.findByActive(true)).thenReturn(List.of(sampleProduct));

        List<Product> result = productService.findAll(null, true);

        assertThat(result).hasSize(1);
        verify(productRepository).findByActive(true);
    }

    @Test
    @DisplayName("findAll combinado usa filtro nombre+active")
    void findAll_byNameAndActive_usesCombinedFilter() {
        when(productRepository.findByNameContainingIgnoreCaseAndActive("teclado", true))
                .thenReturn(List.of(sampleProduct));

        List<Product> result = productService.findAll("teclado", true);

        assertThat(result).hasSize(1);
        verify(productRepository).findByNameContainingIgnoreCaseAndActive("teclado", true);
    }

    // ─── getById ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("getById con ID válido retorna el producto")
    void getById_existingId_returnsProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));

        Product result = productService.getById(1L);

        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getCode()).isEqualTo("PROD-001");
    }

    @Test
    @DisplayName("getById con ID inexistente lanza ProductNotFoundException")
    void getById_nonExistingId_throwsProductNotFoundException() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.getById(999L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("999");
    }

    // ─── create ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("create guarda y retorna el producto creado")
    void create_validRequest_savesAndReturnsProduct() {
        ProductRequest request = new ProductRequest();
        request.setCode("PROD-NEW");
        request.setName("Nuevo Producto");
        request.setPrice(new BigDecimal("99.99"));
        request.setStock(10);

        when(productRepository.save(any(Product.class))).thenReturn(sampleProduct);

        Product result = productService.create(request);

        assertThat(result).isNotNull();
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("create sin campo active usa true como default")
    void create_withoutActive_defaultsToTrue() {
        ProductRequest request = new ProductRequest();
        request.setCode("PROD-NEW");
        request.setName("Nuevo Producto");
        request.setPrice(new BigDecimal("99.99"));
        request.setStock(10);
        // active is null → should default to true

        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = productService.create(request);

        assertThat(result.getActive()).isTrue();
    }

    // ─── update ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("update con ID válido actualiza y retorna el producto")
    void update_existingId_updatesProduct() {
        ProductRequest request = new ProductRequest();
        request.setName("Nombre Actualizado");
        request.setPrice(new BigDecimal("30.00"));
        request.setStock(60);

        when(productRepository.findById(1L)).thenReturn(Optional.of(sampleProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Product result = productService.update(1L, request);

        assertThat(result.getName()).isEqualTo("Nombre Actualizado");
        assertThat(result.getPrice()).isEqualByComparingTo("30.00");
        assertThat(result.getStock()).isEqualTo(60);
    }

    @Test
    @DisplayName("update con ID inexistente lanza ProductNotFoundException")
    void update_nonExistingId_throwsProductNotFoundException() {
        ProductRequest request = new ProductRequest();
        request.setName("X");
        request.setPrice(BigDecimal.ONE);
        request.setStock(1);

        when(productRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> productService.update(999L, request))
                .isInstanceOf(ProductNotFoundException.class);
    }

    // ─── delete ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("delete con ID válido elimina el producto")
    void delete_existingId_deletesProduct() {
        when(productRepository.existsById(1L)).thenReturn(true);

        productService.delete(1L);

        verify(productRepository).deleteById(1L);
    }

    @Test
    @DisplayName("delete con ID inexistente lanza ProductNotFoundException sin borrar")
    void delete_nonExistingId_throwsWithoutDeleting() {
        when(productRepository.existsById(999L)).thenReturn(false);

        assertThatThrownBy(() -> productService.delete(999L))
                .isInstanceOf(ProductNotFoundException.class);

        verify(productRepository, never()).deleteById(any());
    }
}
