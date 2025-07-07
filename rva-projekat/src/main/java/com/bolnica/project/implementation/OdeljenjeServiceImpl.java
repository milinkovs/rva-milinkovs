package com.bolnica.project.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.bolnica.project.models.Odeljenje;
import com.bolnica.project.services.OdeljenjeService;

@Component
public class OdeljenjeServiceImpl implements OdeljenjeService {

	@Override
	public List<Odeljenje> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Odeljenje create(Odeljenje t) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Odeljenje> update(Odeljenje t, int id) {
		// TODO Auto-generated method stub
		return Optional.empty();
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub

	}

}
