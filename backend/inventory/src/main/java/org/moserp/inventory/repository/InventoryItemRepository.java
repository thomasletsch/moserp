package org.moserp.inventory.repository;

import org.moserp.common.domain.RestUri;
import org.moserp.common.repository.EntityRepository;
import org.moserp.inventory.domain.InventoryItem;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.hateoas.ExposesResourceFor;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@ExposesResourceFor(InventoryItem.class)
@RequestMapping("/inventoryItems")
@RepositoryRestResource
public interface InventoryItemRepository extends EntityRepository<InventoryItem, String>, InventoryItemRepositoryCustom {

    List<InventoryItem> findByProductInstanceProductAndFacility(@Param("product") RestUri product, @Param("facility") RestUri facility);

    List<InventoryItem> findByFacility(@Param("facility") RestUri facility);

    List<InventoryItem> findByProductInstanceProduct(@Param("product") RestUri product);


}
