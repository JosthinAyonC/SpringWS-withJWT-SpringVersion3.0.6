package com.example.demo.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.Usuario;
import com.example.demo.services.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {
    
    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> listar(){
        return usuarioService.listarTodos();
    }
    
    @GetMapping("/{id}")
    public Usuario getUsuarioById(@PathVariable("id") Long id ){
        return usuarioService.listarById(id);
    } 
    
    @PostMapping(headers = "Accept=application/json")
    public Usuario insertar(@RequestBody Usuario usuarioBody){
        return usuarioService.insertar(usuarioBody);
    }
    
    @PutMapping("/editar/{id}")
    public Usuario actualizar(@PathVariable Long id, @RequestBody Usuario usuarioBody){
        usuarioBody.setIdUsuario(id);
        return usuarioService.actualizar(usuarioBody);
    }

    @PutMapping("/eliminar/{id}")
    public List<Usuario> eliminar(@PathVariable Long id){
        return usuarioService.eliminar(id);
    }
}
