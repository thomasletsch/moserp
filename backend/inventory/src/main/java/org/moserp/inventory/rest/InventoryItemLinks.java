package org.moserp.inventory.rest;

import org.moserp.inventory.domain.InventoryItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

@Component
public class InventoryItemLinks {
    private static final String PRODUCT = "productInstance";
    private static final String FACILITY = "facility";
    private final EntityLinks entityLinks;

    @Autowired
    public InventoryItemLinks(EntityLinks entityLinks) {
        this.entityLinks = entityLinks;
    }

    public void addLinks(Resource<InventoryItem> inventoryItemResource) {
        inventoryItemResource.add(getSelfLink(inventoryItemResource.getContent()));
        inventoryItemResource.add(getFacilityLink(inventoryItemResource.getContent()));
        inventoryItemResource.add(getProductLink(inventoryItemResource.getContent()));
    }

    Link getSelfLink(InventoryItem inventoryItem) {
        return this.entityLinks.linkForSingleResource(InventoryItem.class, inventoryItem.getId())
                .withSelfRel();
    }

    Link getProductLink(InventoryItem inventoryItem) {
        return new Link(inventoryItem.getProductInstance().getProduct().toString(), PRODUCT);
    }

    Link getFacilityLink(InventoryItem inventoryItem) {
        return new Link(inventoryItem.getFacility().toString(), FACILITY);
    }

}
