package com.bolnica.project.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.bolnica.project.models.Pacijent;
import com.bolnica.project.repository.PacijentRepository;
import com.bolnica.project.services.PacijentService;

@Component
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Pacijent create(Pacijent t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Pacijent> update(Pacijent t, int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

}
