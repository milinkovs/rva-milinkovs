package com.bolnica.project.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.bolnica.project.models.Dijagnoza;
import com.bolnica.project.repository.DijagnozaRepository;
import com.bolnica.project.services.DijagnozaService;

@Component
public class DijagnozaServiceImpl implements DijagnozaService {
	
	private final DijagnozaRepository repo;
	
	public DijagnozaServiceImpl(DijagnozaRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<Dijagnoza> getAll() {
		return repo.findAll();
	}

	@Override
	public boolean existsById(int id) {
		return repo.existsById(id);
	}

	@Override
	public Dijagnoza create(Dijagnoza t) {
		return repo.save(t);
	}

	@Override
	public Optional<Dijagnoza> update(Dijagnoza t, int id) {
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
	public Optional<Dijagnoza> findById(int id) {
		return repo.findById(id);
	}

	@Override
	public List<Dijagnoza> getDijanozeByOznaka(String oznaka) {
		return repo.findByOznakaContainingIgnoreCase(oznaka);
	}

}
