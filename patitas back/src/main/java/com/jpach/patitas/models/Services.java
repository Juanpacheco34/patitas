package com.jpach.patitas.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "services")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Services implements Serializable {
    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_service")
    private Long pk_service;

    @Column(name = "name_service", unique = true, nullable = false)
    private String name_service;

    @Column(name = "active", nullable = false)
    private Boolean active;


}
