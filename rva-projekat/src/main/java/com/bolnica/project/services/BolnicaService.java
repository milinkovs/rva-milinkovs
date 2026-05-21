package com.bolnica.project.services;

import com.bolnica.project.models.Bolnica;

import java.util.List;

public interface BolnicaService extends CrudService<Bolnica> {
	
	List<Bolnica> getBolniceByNaziv(String naziv);
}
