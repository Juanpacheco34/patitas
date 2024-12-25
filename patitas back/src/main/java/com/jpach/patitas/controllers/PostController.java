package com.jpach.patitas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jpach.patitas.models.Posts;
import com.jpach.patitas.services.PostServices;

import jakarta.validation.Valid;

@RestController
public class PostController {

  @Autowired
  private PostServices ps;

  @GetMapping(path = "/allPost")
  public ResponseEntity<?> getAllPosts() {
    return ps.getAllPostSerices();
  }

  @PostMapping("/new-post")
  public ResponseEntity<?> creatPost(@RequestBody Posts post) {

    return ps.savePost(post);
  }

}
