package com.elo7.probe_spring.repositories;

import com.elo7.probe_spring.models.Plateau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlateauRepository extends JpaRepository<Plateau, Long> {}
