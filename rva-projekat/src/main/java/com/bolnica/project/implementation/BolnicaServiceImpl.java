package com.bolnica.project.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bolnica.project.models.Bolnica;
import com.bolnica.project.repository.BolnicaRepository;
import com.bolnica.project.services.BolnicaService;

@Service
public class BolnicaServiceImpl implements BolnicaService {

	private final BolnicaRepository repo;
	
	public BolnicaServiceImpl(BolnicaRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public List<Bolnica> getAll() {
		return repo.findAll();
	}

	@Override
	public boolean existsById(int id) {
		return repo.existsById(id);
	}

	@Override
	public Bolnica create(Bolnica t) {
		return repo.save(t);
	}

	@Override
	public Optional<Bolnica> update(Bolnica t, int id) {
		if (existsById(id)) {
			t.setId(id);
			return Optional.of(repo.save(t));
		}
		return Optional.empty();
	}

	@Override
	public void delete(int id) {
		if(existsById(id)) {
		repo.deleteById(id);
		}
	}

	@Override
	public Optional<Bolnica> findById(int id) {
		return repo.findById(id);
	}

	@Override
	public List<Bolnica> getBolniceByNaziv(String naziv) {
		return repo.findByNazivContainingIgnoreCase(naziv);
	}

}
