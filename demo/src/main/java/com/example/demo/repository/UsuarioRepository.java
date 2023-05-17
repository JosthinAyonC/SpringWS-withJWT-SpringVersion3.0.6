package com.example.demo.repository;


import com.example.demo.models.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    @Modifying
    @Query("UPDATE Usuario u SET u.status = null WHERE u.id = ?1")
    public void deleteById(Long id);

    @Query("SELECT u FROM Usuario u WHERE u.status != null ORDER BY u.id ASC")
    public List<Usuario> findByStatus();
    
    @Query("SELECT u FROM Usuario u WHERE u.email = ?1")
    Usuario findByEmail(String email);
}
