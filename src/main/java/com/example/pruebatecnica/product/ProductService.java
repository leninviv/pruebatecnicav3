package com.example.pruebatecnica.product;

import java.util.List;
import org.springframework.stereotype.Service;

import com.example.pruebatecnica.exception.ProductNotFoundException;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll(String name, Boolean active) {
        // Si nombre y active no son nulas devolver la busqueda de ambas
        if (name != null && !name.isBlank() && active != null) {
            return productRepository.findByNameContainingIgnoreCaseAndActive(name, active);
        }
        // si solo envia name
        if (name != null){
            return productRepository.findByNameContainingIgnoreCase(name);
        }
        // si solo envia active
        if (active != null){
            return productRepository.findByActive(active);
        }
        // si ambas son nulas devolver todo
        return productRepository.findAll();
    }

    public Product getById(Long id) {
        return productRepository.findById(id).orElseThrow(()->new ProductNotFoundException(id));
    }

    public Product create(ProductRequest request) {
        Product product = new Product();
        product.setCode(request.getCode());
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setActive(true);
        return productRepository.save(product);
    }

    public Product update(Long id, ProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(()->new ProductNotFoundException(id));
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        // agregar la posibilidad de actualizar el active con un PUT
        product.setActive(request.getActive());
        return productRepository.save(product);
    }

    public void delete(Long id) {
        // ahora verifica si existe antes de eliminarlo
        Product product = productRepository.findById(id).orElseThrow(()->new ProductNotFoundException(id));
        productRepository.delete(product);
    }
}
