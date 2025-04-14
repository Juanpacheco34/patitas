package com.jpach.patitas.controllers;

import com.jpach.patitas.models.Users;
import com.jpach.patitas.services.UserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegisterController {

    @Autowired
    private UserServices US;

    @Autowired
    private PasswordEncoder PE;


    // Registramos al usuario con los datos que nos envia desde el front
    @PostMapping(path = "/register")
    public ResponseEntity<?> registerUsers(@Valid @RequestBody Users user) {

        try {

            // Encriptamos la password del usuario
            user.setPassword(PE.encode(user.getPassword()));
            // El metodo save devuelve un objeto ResponseEntity
            return US.save(user);
            
        } catch (Exception e) {

            // En caso de alguna exception
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

        }

    }

}
