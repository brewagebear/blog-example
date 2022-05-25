package io.github.brewagebear.repository;

import io.github.brewagebear.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    default Product findBy(Long id) {
        return findById(id).orElseThrow();
    }

}
