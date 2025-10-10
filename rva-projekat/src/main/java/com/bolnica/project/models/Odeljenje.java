package com.bolnica.project.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
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
	private String lokacija;
	
	@ManyToOne
	@JoinColumn(name = "id_bolnice")
	private Bolnica bolnica;
	
	@OneToMany(mappedBy = "odeljenje", cascade= {CascadeType.DETACH, CascadeType.REMOVE})
	@JsonIgnore
	private List<Pacijent> pacijenti;
	
	
	
	public Odeljenje() {
		
	}

	public Odeljenje(int id, String naziv, String lokacija) {
		this.id = id;
		this.naziv = naziv;
		this.lokacija = lokacija;
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

	public String getLokacija() {
		return lokacija;
	}

	public void setLokacija(String lokacija) {
		this.lokacija = lokacija;
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
