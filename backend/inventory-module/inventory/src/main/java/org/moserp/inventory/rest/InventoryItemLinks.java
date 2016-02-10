/*******************************************************************************
 * Copyright 2013 Thomas Letsch (contact@thomas-letsch.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

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
