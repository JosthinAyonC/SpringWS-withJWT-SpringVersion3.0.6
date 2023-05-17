// package com.example.demo.services;

// import java.util.List;
// import java.util.Optional;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import com.example.demo.models.Usuario;
// import com.example.demo.repository.UsuarioRepository;


// @Service
// public class UsuarioService {

//     @Autowired
//     private UsuarioRepository usuarioRepository;

//     public Usuario insertar (Usuario user){
//         return usuarioRepository.save(user);        
//     }

//     public Usuario actualizar(Long id, Usuario user) {
//         Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        
//         if (optionalUsuario.isEmpty()) {
//             return null;
//         }
//         Usuario usuarioEditado = optionalUsuario.get();
//         copiarCamposNoNulos(user, usuarioEditado);
//         return usuarioRepository.save(usuarioEditado);
//     }
    
    
//     public List<Usuario> listarTodos(){
//         return usuarioRepository.findByEstado();
//     }

//     public Usuario listarById(Long id){
//         return usuarioRepository.findById(id).get();
//     }
    

//     public List<Usuario> eliminar(Long id){
//         usuarioRepository.deleteById(id);
//         return usuarioRepository.findByEstado();
//     }

//     private void copiarCamposNoNulos(Usuario fuente, Usuario destino) {
//         if (fuente.getNombre() != null) {
//             destino.setNombre(fuente.getNombre());
//         }
//         if (fuente.getApellido() != null) {
//             destino.setApellido(fuente.getApellido());
//         }
//         if (fuente.getCorreo() != null) {
//             destino.setCorreo(fuente.getCorreo());
//         }
//         if (fuente.getClave() != null) {
//             destino.setClave(fuente.getClave());
//         }
//         if (fuente.getRoles() != null) {
//             destino.setRoles(fuente.getRoles());
//         }
//         if (fuente.getEstado() != null) {
//             destino.setEstado(fuente.getEstado());
//         }
//     }
// }
