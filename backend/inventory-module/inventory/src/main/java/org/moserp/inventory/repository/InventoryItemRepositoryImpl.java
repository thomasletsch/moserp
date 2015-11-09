package org.moserp.inventory.repository;

import org.moserp.common.domain.RestUri;
import org.moserp.inventory.domain.InventoryItem;
import org.moserp.inventory.domain.ProductInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@SuppressWarnings("unused")
public class InventoryItemRepositoryImpl implements InventoryItemRepositoryCustom {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public InventoryItem findByProductAndFacility(ProductInstance productInstance, RestUri facility) {
        assert productInstance != null;
        assert facility != null;
        // TODO: Check attributes as well!
        RestUri product = productInstance.getProduct();
        Query query = query(where("productInstance.product").is(product.getUri()).and("facility").is(facility.getUri()));
        return mongoTemplate.findOne(query, InventoryItem.class);
    }

    @Override
    public List<InventoryItem> findByProductAndFacility(RestUri product, RestUri facility) {
        Query query = query(where("productInstance.product").is(product.getUri()).and("facility").is(facility.getUri()));
        return mongoTemplate.find(query, InventoryItem.class);
    }

}
