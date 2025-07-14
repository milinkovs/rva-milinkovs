package com.bolnica.project.services;

import com.bolnica.project.models.Bolnica;
import com.bolnica.project.models.Odeljenje;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface OdeljenjeService extends CrudService<Odeljenje> {
	
	List<Odeljenje> getOdeljenjaByNaziv(String naziv);
	
	List<Odeljenje> getOdeljenjaByBolnica(Bolnica bolnica);
}
