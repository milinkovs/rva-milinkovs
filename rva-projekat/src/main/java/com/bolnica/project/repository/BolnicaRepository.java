package com.bolnica.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolnica.project.models.Bolnica;

public interface BolnicaRepository extends JpaRepository<Bolnica, Integer>{

	List<Bolnica> findByNazivContainingIgnoreCase(String naziv);
}
