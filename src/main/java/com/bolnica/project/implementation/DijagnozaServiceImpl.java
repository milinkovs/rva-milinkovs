package com.bolnica.project.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.bolnica.project.models.Dijagnoza;
import com.bolnica.project.services.DijagnozaService;

@Component
public class DijagnozaServiceImpl implements DijagnozaService {

	@Override
	public List<Dijagnoza> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Dijagnoza create(Dijagnoza t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Dijagnoza> update(Dijagnoza t, int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

}
