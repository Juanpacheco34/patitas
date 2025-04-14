package com.jpach.patitas.services;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jpach.patitas.interfaces.ICRUD;
import com.jpach.patitas.models.Roles;
import com.jpach.patitas.models.Users;
import com.jpach.patitas.repositories.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServices implements ICRUD<Users, Long>, UserDetailsService {


    // Inyectamos las clases repository
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> save(Users data) {
        data.setRole(new Roles(1L, ""));
        userRepository.save(data);
        return new ResponseEntity<>(data, HttpStatus.CREATED);

    }


    // Indicaremos a Spring que los usuarios para la autenticacion van a estar en la base de datos
    @Override
    public UserDetails loadUserByUsername(String email) {
        try {
            Users loadUser = userRepository.findUserByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("Email introducido no existe en al base de datos: " + email));

            /*
             * Crea una colección inmutable que contiene un único objeto
             * SimpleGrantedAuthority se obtiene el nombre del rol y se concatena a "ROLE_"
             */
            Collection<GrantedAuthority> authorities = Collections.singleton(
                new SimpleGrantedAuthority("ROLE_".concat(loadUser.getRole().getName())));

            return new User(loadUser.getEmail(),
                loadUser.getPassword(),
                true,
                true,
                true,
                true,
                authorities);

        } catch (Exception e) {
            log.error("Error en el loadUserByUsername: {}", e.getMessage());
            return null;
        }

    }

}
