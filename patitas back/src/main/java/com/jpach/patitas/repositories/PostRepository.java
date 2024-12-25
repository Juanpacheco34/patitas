package com.jpach.patitas.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jpach.patitas.models.Posts;

public interface PostRepository extends  JpaRepository<Posts , Long> {


}
