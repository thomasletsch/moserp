package org.moserp.inventory.repository;

import org.moserp.common.domain.RestUri;
import org.moserp.inventory.domain.InventoryItem;
import org.moserp.inventory.domain.ProductInstance;

import java.util.List;

public interface InventoryItemRepositoryCustom {

    InventoryItem findByProductAndFacility(ProductInstance productInstance, RestUri facility);

    List<InventoryItem> findByProductAndFacility(RestUri product, RestUri facility);

}
