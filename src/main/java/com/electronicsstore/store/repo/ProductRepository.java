package com.electronicsstore.store.repo;

import com.electronicsstore.store.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
