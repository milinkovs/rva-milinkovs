package com.bolnica.project.models;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Dijagnoza implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="DIJAGNOZA_ID_GEN", sequenceName="DIJAGNOZA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DIJAGNOZA_ID_GEN")
	private int id;
	
	@NotBlank
	@Column(nullable = false)
	private String naziv;

	@NotBlank
	@Column(nullable = false)
	private String opis;

	@NotBlank
	@Column(nullable = false, unique = true)
	private String oznaka;
	
	@OneToMany(mappedBy = "dijagnoza", cascade= {CascadeType.DETACH, CascadeType.REMOVE})
	@JsonIgnore
	private List<Pacijent> pacijenti;
	
	
	public Dijagnoza(int id, String naziv, String opis, String oznaka) {
		this.id = id;
		this.naziv = naziv;
		this.opis = opis;
		this.oznaka = oznaka;
	}
	
	public Dijagnoza() {
		
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
	public String getOpis() {
		return opis;
	}
	public void setOpis(String opis) {
		this.opis = opis;
	}
	public String getOznaka() {
		return oznaka;
	}
	public void setOznaka(String oznaka) {
		this.oznaka = oznaka;
	}

	public List<Pacijent> getPacijenti() {
		return pacijenti;
	}

	public void setPacijenti(List<Pacijent> pacijenti) {
		this.pacijenti = pacijenti;
	}
}
