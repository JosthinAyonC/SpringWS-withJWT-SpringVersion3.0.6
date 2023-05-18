package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


//clase para la configuracion de seguridad
@Configuration //indicador para que reconozca clase de configuracion de seguridad
@EnableWebSecurity //indicador que activa la seguridad web en nuestra app
public class SecurityConfig  {
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint; 

    //Bean encargado de verificar la informacion de usuario que se logearan en nuestra api
    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
    //Bean para encargarnos de encriptar todas nuestras claves
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    //Bean encargado a realizar el filtro de seguridad del JWT
    @Bean
    JwtAuthenticationFilter jwtAuthenticationFilter(){
        return new JwtAuthenticationFilter();
    }
    //Bean para establecer una cadena de filtros de seguridad en nuestra aplicacion, 
    //y es aqui donde determinaremos los permisos segun los roles de usuaio
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
        .csrf().disable()
        .exceptionHandling()//permitimos el manejo de exepciones
        .authenticationEntryPoint(jwtAuthenticationEntryPoint)// establece un punto de entrada personalizado de autentificacion, para el manejo de autenticaciones no autorizadas.
        .and()
        .sessionManagement()//establece la gestion de la sesion
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authorizeHttpRequests()//Toda peticion http debe ser autorizada
        .requestMatchers("/api/auth/**").permitAll()
        .requestMatchers(HttpMethod.GET, "/api/usuario/***").hasAuthority("ADMIN")
        .anyRequest().authenticated()//cualquier otra peticion debe ser autenticada
        .and()
        .httpBasic();

        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}