package com.example.demo.repository;


import com.example.demo.models.Roles;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolesRepository extends JpaRepository<Roles, Long>{
  
    Optional<Roles> findByName(String name);
}
