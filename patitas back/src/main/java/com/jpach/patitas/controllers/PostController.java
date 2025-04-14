package com.jpach.patitas.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jpach.patitas.models.Posts;
import com.jpach.patitas.services.PostServices;

//Controlador para manejar los post(publicaciones) de la webapp
@RestController
public class PostController {

    @Autowired
    private PostServices postServices;

    // Retornamos todos los post disponibles por el usuario
    @GetMapping(path = "/allPost")
    public ResponseEntity<?> getAllPosts() {
        return postServices.getAllPosts();
    }

    // Guardamos la publicacion insertada por el usuario
    @PostMapping("/new-post")
    public ResponseEntity<?> creatPost(@Valid @RequestBody Posts post) {
        return postServices.savePost(post);
    }

}
