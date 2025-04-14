package com.jpach.patitas.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "counts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Counts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk_count")
    private Long pk_count;

    // numeric(10,2) precision de 10 digitos enteros con 2 decimales
    @Column(name = "money", nullable = false, precision = 10, scale = 2)
    private BigDecimal money;

    @ManyToOne
    @JoinColumn(name = "fk_user", nullable = false)
    private Users fk_user;

}
