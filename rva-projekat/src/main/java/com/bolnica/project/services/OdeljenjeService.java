package com.bolnica.project.services;

import com.bolnica.project.models.Bolnica;
import com.bolnica.project.models.Odeljenje;

import java.util.List;

public interface OdeljenjeService extends CrudService<Odeljenje> {
	
	List<Odeljenje> getOdeljenjaByNaziv(String naziv);
	
	List<Odeljenje> getOdeljenjaByBolnica(Bolnica bolnica);
}
