package com.bolnica.project.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bolnica.project.models.Bolnica;
import com.bolnica.project.models.Odeljenje;
import com.bolnica.project.repository.OdeljenjeRepository;
import com.bolnica.project.services.OdeljenjeService;

@Service
public class OdeljenjeServiceImpl implements OdeljenjeService {
	
	private final OdeljenjeRepository repo;
	
	public OdeljenjeServiceImpl(OdeljenjeRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<Odeljenje> getAll() {
		return repo.findAll();
	}

	@Override
	public boolean existsById(int id) {
		return repo.existsById(id);
	}

	@Override
	public Odeljenje create(Odeljenje t) {
		return repo.save(t);
	}

	@Override
	public Optional<Odeljenje> update(Odeljenje t, int id) {
		if (existsById(id)) {
			t.setId(id);
			return Optional.of(repo.save(t));
		}
		return Optional.empty();
	}

	@Override
	public void delete(int id) {
		if (existsById(id)) {
			repo.deleteById(id);
		}
	}

	@Override
	public Optional<Odeljenje> findById(int id) {
		return repo.findById(id);
	}

	@Override
	public List<Odeljenje> getOdeljenjaByNaziv(String naziv) {
		return repo.findByNazivContainingIgnoreCase(naziv);
	}

	@Override
	public List<Odeljenje> getOdeljenjaByBolnica(Bolnica bolnica) {
		return repo.findByBolnica(bolnica);
	}

}
