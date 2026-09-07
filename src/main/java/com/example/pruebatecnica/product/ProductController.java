package com.example.pruebatecnica.product;

import com.example.pruebatecnica.inventory.StockMovement;
import com.example.pruebatecnica.inventory.StockMovementRequest;
import com.example.pruebatecnica.inventory.StockService;
import java.util.List;
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

    @GetMapping
    public List<Product> findAll(@RequestParam(required = false) String name,
                                 @RequestParam(required = false) Boolean active) {
        return productService.findAll(name, active);
    }

    @GetMapping("/{id}")
    public Product findById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @PostMapping
    public Product create(@RequestBody ProductRequest request) {
        return productService.create(request);
    }

    @PutMapping("/{id}")
    public Product update(@PathVariable Long id, @RequestBody ProductRequest request) {
        return productService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        productService.delete(id);
    }

    @PostMapping("/{id}/stock")
    public Product updateStock(@PathVariable Long id, @RequestBody StockMovementRequest request) {
        return stockService.register(id, request);
    }

    @GetMapping("/{id}/stock-movements")
    public List<StockMovement> stockMovements(@PathVariable Long id) {
        return stockService.findByProduct(id);
    }
}
