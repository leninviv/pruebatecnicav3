package com.example.pruebatecnica.report;

import com.example.pruebatecnica.product.Product;
import com.example.pruebatecnica.product.ProductService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/reports")
public class KardexController {

    private final ProductService productService;

    public KardexController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/kardex")
    public List<Product> kardex(@RequestParam(required = false) Long productId) {
        if (productId != null) {
            return List.of(productService.getById(productId));
        }
        return productService.findAll(null, null);
    }
}
