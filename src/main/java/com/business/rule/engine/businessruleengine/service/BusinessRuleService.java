package com.business.rule.engine.businessruleengine.service;

import com.business.rule.engine.businessruleengine.domain.BusinessRule;
import com.business.rule.engine.businessruleengine.domain.Product;
import com.business.rule.engine.businessruleengine.exception.DataNotFoundException;
import com.business.rule.engine.businessruleengine.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class BusinessRuleService {

    private final ProductRepository productRepository;

    public Set<String> getRules(String productType) {
        return productRepository.findByProductType(productType)
                .map(product -> product.getAncestorsStream(product))
                .map(ancestorsStream -> ancestorsStream.map(Product::getRules)
                        .flatMap(Collection::stream)
                        .map(BusinessRule::getRule)
                        .collect(Collectors.toSet()))
                .orElseThrow(() -> new DataNotFoundException("Product not found"));
    }
}
