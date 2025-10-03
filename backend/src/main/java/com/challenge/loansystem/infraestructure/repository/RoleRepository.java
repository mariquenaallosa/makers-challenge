package com.challenge.loansystem.infraestructure.repository;


import com.challenge.loansystem.domain.enums.UserRoleEnum;
import com.challenge.loansystem.domain.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(UserRoleEnum name);
}

