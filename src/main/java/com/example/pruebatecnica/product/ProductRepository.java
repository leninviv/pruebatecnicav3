package com.example.pruebatecnica.product;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByActive(Boolean active);

    List<Product> findByNameContainingIgnoreCaseAndActive(String name, Boolean active);
    
}
