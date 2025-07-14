package com.bolnica.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolnica.project.models.Bolnica;
import com.bolnica.project.models.Odeljenje;

public interface OdeljenjeRepository extends JpaRepository<Odeljenje, Integer>{
	
	List<Odeljenje> findByNazivContainingIgnoreCase(String naziv);
	
	List<Odeljenje> findByBolnica(Bolnica bolnica); // pretraga po stranom kljucu
}


