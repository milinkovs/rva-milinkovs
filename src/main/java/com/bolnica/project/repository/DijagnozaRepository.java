package com.bolnica.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolnica.project.models.Bolnica;
import com.bolnica.project.models.Dijagnoza;

public interface DijagnozaRepository extends JpaRepository<Dijagnoza, Integer>{

}
