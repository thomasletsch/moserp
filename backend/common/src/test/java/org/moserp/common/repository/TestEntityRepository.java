package org.moserp.common.repository;

import java.util.List;

import org.springframework.data.repository.query.Param;

public interface TestEntityRepository extends EntityRepository<TestEntityWithAuditInfo, String> {

    List<TestEntityWithAuditInfo> findByName(@Param("name") String name);

}