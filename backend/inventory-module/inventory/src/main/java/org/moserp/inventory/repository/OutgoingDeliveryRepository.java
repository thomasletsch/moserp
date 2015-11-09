package org.moserp.inventory.repository;

import org.moserp.common.repository.EntityRepository;
import org.moserp.inventory.domain.OutgoingDelivery;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface OutgoingDeliveryRepository extends EntityRepository<OutgoingDelivery, String> {
    String URL = "outgoingDeliveries";
}
