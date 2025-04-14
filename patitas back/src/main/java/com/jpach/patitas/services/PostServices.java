package com.jpach.patitas.services;

import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jpach.patitas.models.Posts;
import com.jpach.patitas.repositories.PostRepository;

@Slf4j
@Service
public class PostServices {

    @Autowired
    private PostRepository postRepository;

    public ResponseEntity<?> getAllPosts() {
        List<Posts> postList = null;

        try {

            postList = postRepository.findAll();

            if (postList.isEmpty()) {
                return ResponseEntity.ok("No hay publicaciones post disponibles");
            }

        } catch (Exception e) {
            log.error("Error en getAllPosts: {}", e.getMessage());
        }

        return ResponseEntity.ok(postList);
    }

    public ResponseEntity<?> savePost(Posts post) {

        if (post.getTitle().isEmpty() || post.getPicture().isEmpty())
            return ResponseEntity.ok("Ingresa los datos obligatorios para publicar tu post");

        try {
            postRepository.save(post);
        } catch (Exception e) {
            log.error("Error en savePost: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ocurrio un error al guardar el Post");
        }

        return ResponseEntity.ok("Post publicado con exito!!");
    }

}
