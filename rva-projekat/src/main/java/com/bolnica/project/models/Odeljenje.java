package com.bolnica.project.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Odeljenje implements Serializable {
	
	private static final long serialVerisonUID = 1L;
	
	@Id
	@SequenceGenerator(name="ODELJENJE_ID_GEN", sequenceName="ODELJENJE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ODELJENJE_ID_GEN")
	private int id;
	
	private String naziv;
	private String lokacijal;
	
	@ManyToOne
	@JoinColumn(name = "id_bolnice")
	Bolnica bolnica;
	
	@OneToMany(mappedBy = "odeljenje")
	private List<Pacijent> pacijenti;
	
	
	
	public Odeljenje() {
		
	}

	public Odeljenje(int id, String naziv, String lokacijal) {
		this.id = id;
		this.naziv = naziv;
		this.lokacijal = lokacijal;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getLokacijal() {
		return lokacijal;
	}

	public void setLokacijal(String lokacijal) {
		this.lokacijal = lokacijal;
	}

	public Bolnica getBolnica() {
		return bolnica;
	}

	public void setBolnica(Bolnica bolnica) {
		this.bolnica = bolnica;
	}

	public List<Pacijent> getPacijenti() {
		return pacijenti;
	}

	public void setPacijenti(List<Pacijent> pacijenti) {
		this.pacijenti = pacijenti;
	}	
	
	
}
