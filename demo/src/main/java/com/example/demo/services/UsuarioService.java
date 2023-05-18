package com.example.demo.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Usuario insertar(Usuario user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(rolesRepository.findByName(user.getRoles().get(0).getName())));
        return usuarioRepository.save(user);
    }

    public Usuario actualizar(Usuario user) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(user.getIdUsuario());
        if (optionalUsuario.isEmpty()) {
            return null;
        }
        Usuario usuarioEditado = optionalUsuario.get();
        copiarCamposNoNulos(user, usuarioEditado);
        return usuarioRepository.save(usuarioEditado);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findByStatus();
    }

    public Usuario listarById(Long id) {
        return usuarioRepository.findById(id).get();
    }

    public List<Usuario> eliminar(Long id) {
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
            destino.setPassword(this.passwordEncoder.encode(fuente.getPassword()));
        }
        if (fuente.getStatus() != null) {
            destino.setStatus(fuente.getStatus());
        }
        if (fuente.getRoles().size()>0) {
            List<Roles> roles = fuente.getRoles().stream().map(role -> rolesRepository.findByName(role.getName())).collect(Collectors.toList());
            destino.setRoles(roles);
        }
    }

    public ResponseEntity<String> registrar(DtoRegistro dtoRegistro, String roleParameter) {
        if (usuarioRepository.findByEmail(dtoRegistro.getEmail()) == null) {
            Usuario usuarioRegisterNew = new Usuario();
            usuarioRegisterNew.setEmail(dtoRegistro.getEmail());
            usuarioRegisterNew.setPassword(passwordEncoder.encode(dtoRegistro.getPassword()));
            usuarioRegisterNew.setStatus("A");
            usuarioRegisterNew.setFirstname(dtoRegistro.getFirstname());
            usuarioRegisterNew.setLastname(dtoRegistro.getLastname());

            Roles roles = rolesRepository.findByName(roleParameter);
            usuarioRegisterNew.setRoles(Collections.singletonList(roles));
            usuarioRepository.save(usuarioRegisterNew);
            return new ResponseEntity<>("Registro exitoso", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error: email registrado ya existe, intenta con otro", HttpStatus.BAD_REQUEST);
        }
    }
}
