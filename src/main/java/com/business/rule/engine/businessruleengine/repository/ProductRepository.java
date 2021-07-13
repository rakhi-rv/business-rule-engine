package com.business.rule.engine.businessruleengine.repository;

import com.business.rule.engine.businessruleengine.domain.Product;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProductRepository extends CrudRepository<Product, Long> {

    Optional<Product> findByProductType(String productType);
}
