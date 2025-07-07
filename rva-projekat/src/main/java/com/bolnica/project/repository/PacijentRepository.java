package com.bolnica.project.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bolnica.project.models.Bolnica;
import com.bolnica.project.models.Pacijent;

public interface PacijentRepository extends JpaRepository<Pacijent, Integer>{

}
