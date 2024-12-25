package com.jpach.patitas.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.jpach.patitas.models.Posts;
import com.jpach.patitas.repositories.PostRepository;

@Service
public class PostServices {

  @Autowired
  private PostRepository pr;

  public ResponseEntity<?> getAllPostSerices() {
    List<Posts> postList = null;

    try {

      postList = pr.findAll();

      if (postList.isEmpty())
        return ResponseEntity.ok("No hay publicaciones post disponibles");
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return ResponseEntity.ok(postList);
  }

  public ResponseEntity<?> savePost(Posts post) {

    if (post.getTitle().isEmpty() || post.getPicture().isEmpty())
      return ResponseEntity.ok("Ingresa los datos obligatorios para publicar tu post");

    try {

      pr.save(post);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    return ResponseEntity.ok("Post publicado con exito!!");
  }

}
