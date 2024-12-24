package com.jpach.patitas.repositories;

import com.jpach.patitas.models.Movements;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovementRepository extends JpaRepository<Movements, Long> {

}
