package com.example.demo.services;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dtos.DtoRegistro;
import com.example.demo.models.Roles;
import com.example.demo.models.Usuario;
import com.example.demo.repository.RolesRepository;
import com.example.demo.repository.UsuarioRepository;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RolesRepository rolesRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario insertar (Usuario user){
        return usuarioRepository.save(user);        
    }

    public Usuario actualizar(Long id, Usuario user) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        
        if (optionalUsuario.isEmpty()) {
            return null;
        }
        Usuario usuarioEditado = optionalUsuario.get();
        copiarCamposNoNulos(user, usuarioEditado);
        return usuarioRepository.save(usuarioEditado);
    }
    
    
    public List<Usuario> listarTodos(){
        return usuarioRepository.findByStatus();
    }

    public Usuario listarById(Long id){
        return usuarioRepository.findById(id).get();
    }
    

    public List<Usuario> eliminar(Long id){
        usuarioRepository.deleteById(id);
        return usuarioRepository.findByStatus();
    }

    private void copiarCamposNoNulos(Usuario fuente, Usuario destino) {
        if (fuente.getFirstname() != null) {
            destino.setFirstname(fuente.getFirstname());
        }
        if (fuente.getLastname() != null) {
            destino.setLastname(fuente.getLastname());
        }
        if (fuente.getEmail() != null) {
            destino.setEmail(fuente.getEmail());
        }
        if (fuente.getPassword() != null) {
            destino.setPassword(fuente.getPassword());
        }
        if (fuente.getRoles() != null) {
            destino.setRoles(fuente.getRoles());
        }
        if (fuente.getStatus() != null) {
            destino.setStatus(fuente.getStatus());
        }
    }

    public ResponseEntity<String> registrar(DtoRegistro dtoRegistro) {
        if (usuarioRepository.findByEmail(dtoRegistro.getEmail()) == null) {
            Usuario usuarioRegisterNew = new Usuario();
            usuarioRegisterNew.setEmail(dtoRegistro.getEmail());
            usuarioRegisterNew.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
            Roles roles = rolesRepository.findByName("USER");
            usuarioRegisterNew.setRoles(Collections.singletonList(roles));
            usuarioRepository.save(usuarioRegisterNew);
            return new ResponseEntity<>("Registro exitoso", HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Error: email registrado ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
        }
    }
}
