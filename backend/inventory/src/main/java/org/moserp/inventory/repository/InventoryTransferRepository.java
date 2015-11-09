package org.moserp.inventory.repository;

import org.moserp.common.repository.EntityRepository;
import org.moserp.inventory.domain.InventoryTransfer;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface InventoryTransferRepository extends EntityRepository<InventoryTransfer, String> {

    String URL = "inventoryTransfers";
}
