package com.tasks.calculator.repositories;

import com.tasks.calculator.dto.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository("roleRepository")
public interface RoleRepository extends CrudRepository<Role,Long> {


}
