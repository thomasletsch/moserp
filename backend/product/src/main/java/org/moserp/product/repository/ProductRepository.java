package org.moserp.product.repository;

import org.moserp.common.repository.EntityRepository;
import org.moserp.product.domain.Product;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends EntityRepository<Product, String> {

    String URL = "products";

    Product findByExternalId(@Param("id") String externalId);

    List<Product> findByName(@Param("name") String name);

    @Query("{$or: [{ name : {$regex: '?0', $options: 'i' }}, {ean : {$regex: '?0', $options: 'i' }}]}")
    List<Product> findByNameOrEan(@Param("query") String query);

}
