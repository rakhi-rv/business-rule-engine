package com.business.rule.engine.businessruleengine.service;

import com.business.rule.engine.businessruleengine.domain.BusinessRule;
import com.business.rule.engine.businessruleengine.domain.Product;
import com.business.rule.engine.businessruleengine.exception.DataNotFoundException;
import com.business.rule.engine.businessruleengine.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;

@ExtendWith(SpringExtension.class)
class BusinessRuleServiceTest {

    private BusinessRuleService businessRuleService;

    @MockBean
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        this.businessRuleService = new BusinessRuleService(productRepository);
    }

    @AfterEach
    void tearDown() {
        this.businessRuleService = null;
    }

    @Test
    void getBusinessRulesWhenProductFoundIsParentProductShouldReturnCorrectRulesForThatProduct() {
        //given
        BusinessRule businessRule = new BusinessRule();
        businessRule.setId(1L);
        businessRule.setProductType("physical_product");
        businessRule.setRule("generate packing slip for shipping");

        Product product = new Product();
        product.setId(1L);
        product.setProductType("physical_product");
        product.getRules().add(businessRule);

        given(productRepository.findByProductType("physical_product"))
                .willReturn(Optional.of(product));

        //when
        var businessRules = businessRuleService.getRules("physical_product");

        //then
        assertEquals(1, businessRules.size());
        assertTrue(businessRules.contains("generate packing slip for shipping"));
    }

    @Test
    void getBusinessRulesWhenProductFoundWithAncestorsShouldReturnRulesForThatProduct() {
        //given
        BusinessRule parentRule_1 = new BusinessRule();
        parentRule_1.setId(1L);
        parentRule_1.setProductType("physical_product");
        parentRule_1.setRule("generate packing slip for shipping");

        BusinessRule parentRule_2 = new BusinessRule();
        parentRule_2.setId(2L);
        parentRule_2.setProductType("physical_product");
        parentRule_2.setRule("generate a commission payment to the agent");

        BusinessRule node = new BusinessRule();
        node.setId(3L);
        node.setProductType("book");
        node.setRule("create a duplicate packing slip for royalty department");

        Product parent = new Product();
        parent.setId(1L);
        parent.setProductType("physical_product");
        parent.getRules().add(parentRule_1);
        parent.getRules().add(parentRule_2);

        Product product = new Product();
        product.setId(1L);
        product.setProductType("book");
        product.setParent(parent);
        product.getRules().add(node);

        given(productRepository.findByProductType("physical_product"))
                .willReturn(Optional.of(product));

        //when
        var businessRules = businessRuleService.getRules("physical_product");

        //then
        assertEquals(3, businessRules.size());
        assertTrue(businessRules.contains("create a duplicate packing slip for royalty department"));
        assertTrue(businessRules.contains("generate packing slip for shipping"));
        assertTrue(businessRules.contains("generate a commission payment to the agent"));
    }


    @Test
    void getBusinessRulesWhenProductNotFoundShouldDelegateToDataNotFoundException() {
        //given
        given(productRepository.findByProductType("physical_product"))
                .willReturn(Optional.empty());

        //when
        DataNotFoundException exception = assertThrows(DataNotFoundException.class,
                () -> businessRuleService.getRules("physical_product"));
        //then
        assertEquals("Product not found", exception.getMessage());
    }
}