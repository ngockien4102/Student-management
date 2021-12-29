package com.example.spring.security.basic.repository;

import com.example.spring.security.basic.entity.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles,Long> {
    Roles findByName(String name);
}
