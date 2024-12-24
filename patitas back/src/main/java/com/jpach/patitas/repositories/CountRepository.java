package com.jpach.patitas.repositories;

import com.jpach.patitas.models.Counts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountRepository extends JpaRepository<Counts, Long> {

}
