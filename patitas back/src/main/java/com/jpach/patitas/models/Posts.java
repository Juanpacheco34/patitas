package com.jpach.patitas.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Posts implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_post")
    private Long pk_post;


    @Column(name = "title", nullable = false)
    private String title;

    // TEXT sin limite de caracteres
    @Column(name = "description", nullable = true, columnDefinition = "TEXT")
    private String description;

    @Column(name = "picture", nullable = false)
    private String picture;

    @Column(name = "i_like")
    private Integer i_like;

    @ManyToOne
    @JoinColumn(name = "fk_user", nullable = false)
    private Users fk_user;


}
