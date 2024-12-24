package com.jpach.patitas.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "movements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Movements {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_movement", nullable = false)
    private Long pk_movement;

    @Column(name = "mount", nullable = false, precision = 10, scale = 2)
    private BigDecimal mount;

    @Column(name = "successful", nullable = false)
    private Boolean successful;

    @ManyToOne
    @JoinColumn(name = "fk_service", nullable = false)
    private Services fk_service;

    @ManyToOne
    @JoinColumn(name = "fk_user", nullable = false)
    private Users fk_user;

}
