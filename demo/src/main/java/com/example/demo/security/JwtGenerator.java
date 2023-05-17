package com.example.demo.security;

import java.util.Date;

import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtGenerator {
    // Metodo para generar el token por medio de la autenticacion
    public String generarToken(Authentication auth) {
        String usuarioLogeadoName = auth.getName();
        Date tiempoActual = new Date();
        Date expiracionToken = new Date(tiempoActual.getTime() + ConstanteSeguridad.JWT_EXPIRATION_TOKEN);

        // LINEA PARA GENERAR EL TOKEN
        String token = Jwts.builder()
                .setSubject(usuarioLogeadoName)
                .setIssuedAt(new Date())
                .setExpiration(expiracionToken)
                .signWith(SignatureAlgorithm.HS512, ConstanteSeguridad.JWT_FIRMA)
                .compact();

        return token;
    }

    // metodo para extraer el nombre de usuario a travez del token
    public String obtenerUsuarioLogeadoName(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(ConstanteSeguridad.JWT_FIRMA)
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    //Metodo para validar el token
    public Boolean validarToken(String token){
        try {
            Jwts.parser().setSigningKey(ConstanteSeguridad.JWT_FIRMA).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new AuthenticationCredentialsNotFoundException("Jwt expirado o incorrecto");
        }
    }

}
