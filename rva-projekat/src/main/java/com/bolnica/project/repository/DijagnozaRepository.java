package com.bolnica.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolnica.project.models.Dijagnoza;

public interface DijagnozaRepository extends JpaRepository<Dijagnoza, Integer>{
	
	List<Dijagnoza> findByOznakaContainingIgnoreCase(String oznaka);
}
