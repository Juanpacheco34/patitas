package com.jpach.patitas.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Table(name = "roles")
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Roles implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_role")
    private Long pk_role;

    @Column(name = "name", nullable = false)
    private String name;


}
