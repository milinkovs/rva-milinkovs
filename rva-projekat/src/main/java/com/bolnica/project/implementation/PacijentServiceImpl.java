package com.bolnica.project.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.bolnica.project.models.Dijagnoza;
import com.bolnica.project.models.Odeljenje;
import com.bolnica.project.models.Pacijent;
import com.bolnica.project.repository.PacijentRepository;
import com.bolnica.project.services.PacijentService;

@Service
public class PacijentServiceImpl implements PacijentService {
	
	private final PacijentRepository repo;
	
	public PacijentServiceImpl (PacijentRepository repo) {
		this.repo = repo;
	}
	
	@Override
	public List<Pacijent> getAll() {
		return repo.findAll();
	}

	@Override
	public boolean existsById(int id) {
		return repo.existsById(id);
	}

	@Override
	public Pacijent create(Pacijent t) {
		return repo.save(t);
	}

	@Override
	public Optional<Pacijent> update(Pacijent t, int id) {
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
	public Optional<Pacijent> findById(int id) {
		return repo.findById(id);
	}

	@Override
	public List<Pacijent> getByOsiguranje(boolean zdr_osiguranje) {
		return repo.findByZdrOsiguranje(zdr_osiguranje);
	}

	@Override
	public List<Pacijent> getPacijentsByDijagnoza(Dijagnoza dijagnoza) {
		return repo.findByDijagnoza(dijagnoza);
	}

	@Override
	public List<Pacijent> getPacijentsByOdeljenje(Odeljenje odeljenje) {
		return repo.findByOdeljenje(odeljenje);
	}
}
