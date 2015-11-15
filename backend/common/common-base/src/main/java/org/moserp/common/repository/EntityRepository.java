package org.moserp.common.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.security.access.prepost.PreAuthorize;

import java.io.Serializable;

@PreAuthorize("hasRole('user')")
@NoRepositoryBean
public interface EntityRepository<T, ID extends Serializable> extends CrudRepository<T, ID> {
}
