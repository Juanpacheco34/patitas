package com.jpach.patitas.repositories;

import com.jpach.patitas.models.Users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {

  @Query("SELECT u FROM Users u WHERE u.email = :email")
  public Optional<Users> findUserByEmail(@Param("email") String email);

}
