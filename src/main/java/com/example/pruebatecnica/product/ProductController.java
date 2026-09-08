package com.example.pruebatecnica.product;

import com.example.pruebatecnica.inventory.StockMovement;
import com.example.pruebatecnica.inventory.StockMovementRequest;
import com.example.pruebatecnica.inventory.StockService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    private final StockService stockService;

    public ProductController(ProductService productService, StockService stockService) {
        this.productService = productService;
        this.stockService = stockService;
    }

    /** GET /api/products?name=&active= */
    @GetMapping
    public List<Product> findAll(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) Boolean active) {
        return productService.findAll(name, active);
    }

    /** GET /api/products/{id} → 200 or 404 */
    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id) {
        return productService.getById(id);
    }

    /** POST /api/products → 201 Created */
    @PostMapping
    public ResponseEntity<Product> create(@Valid @RequestBody ProductRequest request) {
        Product created = productService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** PUT /api/products/{id} → 200 or 404 */
    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @Valid @RequestBody ProductRequest request) {
        return productService.update(id, request);
    }

    /** DELETE /api/products/{id} → 204 No Content or 404 */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    /** POST /api/products/{id}/stock → 200 with updated product */
    @PostMapping("/{id}/stock")
    public Product updateStock(@PathVariable Long id, @Valid @RequestBody StockMovementRequest request) {
        return stockService.register(id, request);
    }

    /** GET /api/products/{id}/stock-movements → movement history */
    @GetMapping("/{id}/stock-movements")
    public List<StockMovement> stockMovements(@PathVariable Long id) {
        return stockService.findByProduct(id);
    }
}

