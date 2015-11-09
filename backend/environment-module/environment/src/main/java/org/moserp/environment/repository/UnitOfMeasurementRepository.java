package org.moserp.environment.repository;

import org.moserp.common.repository.EntityRepository;
import org.moserp.environment.domain.UnitOfMeasurement;
import org.springframework.data.repository.query.Param;

public interface UnitOfMeasurementRepository extends EntityRepository<UnitOfMeasurement, String> {

    UnitOfMeasurement findByCode(@Param("code") String code);

}
