package org.moserp.environment.repository;

import org.moserp.common.repository.EntityRepository;
import org.moserp.environment.domain.User;

public interface UserRepository extends EntityRepository<User, String> {

    User findByName(String name);
}
