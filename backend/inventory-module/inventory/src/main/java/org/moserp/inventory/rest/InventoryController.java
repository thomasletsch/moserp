package org.moserp.inventory.rest;

import org.moserp.common.domain.Quantity;
import org.moserp.common.domain.RestUri;
import org.moserp.common.modules.ModuleRegistry;
import org.moserp.inventory.domain.InventoryItem;
import org.moserp.inventory.repository.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RestController
@ExposesResourceFor(InventoryItem.class)
public class InventoryController {

    @Autowired
    private InventoryItemRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private InventoryItemLinks inventoryItemLinks;

    @Autowired
    private ModuleRegistry moduleRegistry;

    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/products/{productId}/quantityOnHand")
    public Quantity getProductQuantityOnHand(@PathVariable String productId) {
        RestUri productsBaseUri = moduleRegistry.getBaseUriForResource(OtherResources.PRODUCTS);
        RestUri productUri = productsBaseUri.slash(productId);
        List<InventoryItem> inventoryItems = repository.findByProductInstanceProduct(productUri);
        return inventoryItems.stream().map(InventoryItem::getQuantityOnHand).reduce(Quantity.ZERO, Quantity::add);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/inventoryItems/search/findByProductIdOrFacilityId")
    public Resources<Resource<InventoryItem>> findByProductIdOrFacilityId(@RequestParam(required = false) String productId,
                                                                          @RequestParam(required = false) String facilityId) {
        List<InventoryItem> items;
        RestUri facilityBaseUri = moduleRegistry.getBaseUriForResource(OtherResources.FACILITIES);
        RestUri facilityUri = facilityBaseUri.slash(facilityId);
        RestUri productsBaseUri = moduleRegistry.getBaseUriForResource(OtherResources.PRODUCTS);
        RestUri productUri = productsBaseUri.slash(productId);
        if (productId == null) {
            items = repository.findByFacility(facilityUri);
        } else if (facilityId == null) {
            items =  repository.findByProductInstanceProduct(productUri);
        } else {
            Query query = query(where("productInstance.product").is(productUri.getUri()).and("facility").is(facilityUri.getUri()));
            items =  mongoTemplate.find(query, InventoryItem.class);
        }
        Stream<Resource<InventoryItem>> resourceStream = items.stream().map(inventoryItem -> new Resource<>(inventoryItem));
        List<Resource<InventoryItem>> inventoryItemResources = resourceStream.collect(Collectors.toList());
        inventoryItemResources.forEach(inventoryItemLinks::addLinks);
        return new Resources<>(inventoryItemResources);
    }

}
