package com.example.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dtos.DtoAuthResponse;
import com.example.demo.dtos.DtoLogin;
import com.example.demo.dtos.DtoRegistro;
import com.example.demo.security.JwtGenerator;
import com.example.demo.services.UsuarioService;

@RestController
@RequestMapping("/api/auth/")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtGenerator jwtGenerator;
    @Autowired
    private UsuarioService usuarioService;
    
    @PostMapping("defaultRegister")
    public ResponseEntity<String> registerDefault(@RequestBody DtoRegistro dtoRegistro) {
        String role="USER";
        return usuarioService.registrar(dtoRegistro,  role);
    }

    @PostMapping("adminRegister")
    public ResponseEntity<String> registerAdmin(@RequestBody DtoRegistro dtoRegistro) {
        String role="ADMIN";
        return usuarioService.registrar(dtoRegistro,  role);
    }

    @PostMapping("login")
    public ResponseEntity<DtoAuthResponse> login(@RequestBody DtoLogin dtoLogin){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            dtoLogin.getEmail(), dtoLogin.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generarToken(authentication);
        return new ResponseEntity<>(new DtoAuthResponse(token), HttpStatus.OK);
    }   
}
