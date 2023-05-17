package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "role")
public class Roles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_role")
    private Long idRole;

    private String name;

    public Roles() {
    }

    public Roles(String name) {
        this.name = name;
    }

    public Long getIdRole() {
        return this.idRole;
    }

    public void setIdRole(Long idRole) {
        this.idRole = idRole;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
}
