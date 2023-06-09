package com.example.demo.repository;

import com.example.demo.models.Roles;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RolesRepository extends JpaRepository<Roles, Long>{
  
    Roles findByName(String name);

    
}
