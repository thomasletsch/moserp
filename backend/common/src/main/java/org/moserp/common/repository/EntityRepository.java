package org.moserp.common.repository;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface EntityRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {
}
