package com.example.pruebatecnica.product;

import com.example.pruebatecnica.exception.ProductNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Returns all products applying optional filters.
     * Supports filtering by name (partial, case-insensitive), active state, or both combined.
     */
    public List<Product> findAll(String name, Boolean active) {
        if (name != null && active != null) {
            return productRepository.findByNameContainingIgnoreCaseAndActive(name, active);
        }
        if (name != null) {
            return productRepository.findByNameContainingIgnoreCase(name);
        }
        if (active != null) {
            return productRepository.findByActive(active);
        }
        return productRepository.findAll();
    }

    /**
     * Returns a product by ID or throws ProductNotFoundException (→ 404).
     */
    public Product getById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
    }

    /**
     * Creates a new product. The 'active' field defaults to true if not provided.
     */
    public Product create(ProductRequest request) {
        Product product = new Product();
        product.setCode(request.getCode());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setActive(request.getActive() != null ? request.getActive() : true);
        return productRepository.save(product);
    }

    /**
     * Updates an existing product. All provided fields are updated.
     * Throws ProductNotFoundException (→ 404) if the product does not exist.
     */
    public Product update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        if (request.getActive() != null) {
            product.setActive(request.getActive());
        }
        return productRepository.save(product);
    }

    /**
     * Deletes a product by ID.
     * Throws ProductNotFoundException (→ 404) if the product does not exist.
     */
    public void delete(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductNotFoundException(id);
        }
        productRepository.deleteById(id);
    }
}

