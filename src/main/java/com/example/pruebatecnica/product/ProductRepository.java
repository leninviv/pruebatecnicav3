package com.example.pruebatecnica.product;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    /** Búsqueda parcial por nombre (case-insensitive) */
    List<Product> findByNameContainingIgnoreCase(String name);

    /** Filtro por estado activo/inactivo */
    List<Product> findByActive(Boolean active);

    /** Filtro combinado: nombre parcial + estado */
    List<Product> findByNameContainingIgnoreCaseAndActive(String name, Boolean active);
}

