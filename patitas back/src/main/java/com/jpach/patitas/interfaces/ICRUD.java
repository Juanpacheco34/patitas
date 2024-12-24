package com.jpach.patitas.interfaces;

import org.springframework.http.ResponseEntity;

/**
 * ICRUD con esta intefacer facilitaremos los metodos que
 * puedan ser utilzado en todas las clases.
 */
public interface ICRUD<T, ID> {

    ResponseEntity<?> save(T data);
}
