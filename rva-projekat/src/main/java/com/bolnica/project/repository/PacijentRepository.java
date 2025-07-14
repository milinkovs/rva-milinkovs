package com.bolnica.project.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolnica.project.models.Dijagnoza;
import com.bolnica.project.models.Odeljenje;
import com.bolnica.project.models.Pacijent;

public interface PacijentRepository extends JpaRepository<Pacijent, Integer>{
	
	List<Pacijent> findByZdrOsiguranje(boolean zdr_osiguranje);
	
	List<Pacijent> findByDijagnoza(Dijagnoza dijagnoza);
	List<Pacijent> findByOdeljenje(Odeljenje odeljenje);
}
