package org.moserp.inventory.repository;

import org.moserp.common.repository.EntityRepository;
import org.moserp.inventory.domain.IncomingDelivery;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface IncomingDeliveryRepository extends EntityRepository<IncomingDelivery, String> {

    String URL = "incomingDeliveries";

}
