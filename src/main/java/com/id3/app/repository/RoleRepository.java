package com.id3.app.repository;

import com.id3.app.model.Role;
import com.id3.app.model.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByUserRoleEquals(UserRole name);
}
