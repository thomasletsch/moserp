package org.moserp.environment.repository;

import org.moserp.common.repository.EntityRepository;
import org.moserp.environment.domain.ValueList;
import org.springframework.data.repository.query.Param;

public interface ValueListRepository extends EntityRepository<ValueList, String> {

    ValueList findByKey(@Param("key") String key);

}
