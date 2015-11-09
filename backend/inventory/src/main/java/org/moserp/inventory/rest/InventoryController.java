package org.moserp.inventory.rest;

import org.moserp.common.domain.Quantity;
import org.moserp.common.domain.RestUri;
import org.moserp.common.modules.ModuleRegistry;
import org.moserp.inventory.domain.InventoryItem;
import org.moserp.inventory.repository.InventoryItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@ExposesResourceFor(InventoryItem.class)
public class InventoryController {

    @Autowired
    private InventoryItemRepository repository;

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

    @RequestMapping(method = RequestMethod.GET, value = "/facilities/{facilityId}/inventoryItems")
    public Resources<Resource<InventoryItem>> getInventoryItemsPerFacility(@RequestParam(required = false) String productId,
                                                                           @PathVariable String facilityId) {
        List<InventoryItem> items;
        RestUri facilityBaseUri = moduleRegistry.getBaseUriForResource(OtherResources.FACILITIES);
        RestUri facilityUri = facilityBaseUri.slash(facilityId);
        if (productId == null) {
            items = repository.findByFacility(facilityUri);
        } else {
            RestUri productsBaseUri = moduleRegistry.getBaseUriForResource(OtherResources.PRODUCTS);
            RestUri productUri = productsBaseUri.slash(productId);
            items = repository.findByProductAndFacility(productUri, facilityUri);
        }
        Stream<Resource<InventoryItem>> resourceStream = items.stream().map(inventoryItem -> new Resource<>(inventoryItem));
        List<Resource<InventoryItem>> inventoryItemResources = resourceStream.collect(Collectors.toList());
        inventoryItemResources.forEach(inventoryItemLinks::addLinks);
        return new Resources<>(inventoryItemResources);
    }


    @ResponseBody
    @RequestMapping(method = RequestMethod.GET, value = "/products/{productId}/inventoryItems", produces = MediaTypes.HAL_JSON_VALUE)
    public Resources<Resource<InventoryItem>> getInventoryItemsPerProduct(@PathVariable String productId) {
        RestUri productsBaseUri = moduleRegistry.getBaseUriForResource(OtherResources.PRODUCTS);
        RestUri productUri = productsBaseUri.slash(productId);
        List<InventoryItem> inventoryItems = repository.findByProductInstanceProduct(productUri);
        Stream<Resource<InventoryItem>> resourceStream = inventoryItems.stream().map(inventoryItem -> new Resource<>(inventoryItem));
        List<Resource<InventoryItem>> inventoryItemResources = resourceStream.collect(Collectors.toList());
        inventoryItemResources.forEach(inventoryItemLinks::addLinks);
        return new Resources<>(inventoryItemResources);
    }

}
