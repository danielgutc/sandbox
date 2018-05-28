package org.dgc.sandbox.jwt.server.repository;

import org.dgc.sandbox.jwt.server.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, String>
{
}
