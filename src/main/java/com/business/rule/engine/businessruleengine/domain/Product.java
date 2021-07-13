package com.business.rule.engine.businessruleengine.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Getter
@Setter
@Entity(name = "product")
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_type")
    private String productType;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_type", referencedColumnName = "product_type")
    private List<BusinessRule> rules;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent", referencedColumnName = "product_type")
    private Product parent;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Product> children;

    public Product(){
        this.rules = new ArrayList<>();
        this.children = new ArrayList<>();
    }

    public Stream<Product> getAncestorsStream(Product product) {
        return Stream.concat(Stream.of(product), this.getAncestorByParent(product));
    }

    public Stream<Product> getAncestorByParent(Product product) {
        return product.isRootNode() ? Stream.empty() : getAncestorsStream(product.getParent());
    }

    public boolean isRootNode() {
        return Optional.ofNullable(this.getParent()).isEmpty();
    }
}
