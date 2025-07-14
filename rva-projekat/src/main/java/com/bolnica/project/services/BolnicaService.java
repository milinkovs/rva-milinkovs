package com.bolnica.project.services;

import com.bolnica.project.models.Bolnica;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface BolnicaService extends CrudService<Bolnica> {
	
	List<Bolnica> getBolniceByNaziv(String naziv);
}
