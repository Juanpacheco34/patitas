package com.jpach.patitas.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_user")
    private Long pkId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "lastname", nullable = false)
    private String lastname;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 255)
    @Size(min = 10)
    private String password;

    @Column(name = "dni", nullable = true, unique = true)
    private String dni;

    // TEXT sin limite de caracteres
    @Column(name = "description", nullable = true, columnDefinition = "TEXT")
    private String description;

    @Column(name = "web", nullable = true)
    private String web;

    @Column(name = "picture", nullable = true)
    private String picture;

    @ManyToOne
    @JoinColumn(name = "fk_role", nullable = false)
    private Roles role;


}
