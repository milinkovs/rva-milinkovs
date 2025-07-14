package com.bolnica.project.services;

import com.bolnica.project.models.Dijagnoza;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public interface DijagnozaService extends CrudService<Dijagnoza> {
	
	List<Dijagnoza> getDijanozeByOznaka(String oznaka);
}
