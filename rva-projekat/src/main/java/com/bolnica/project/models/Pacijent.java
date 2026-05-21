package com.bolnica.project.models;

import java.io.Serializable;
import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


@Entity
public class Pacijent implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="PACIJENT_ID_GEN", sequenceName="pacijent_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PACIJENT_ID_GEN")
	private int id;
	
	@NotBlank
	@Column(nullable = false)
	private String ime;

	private boolean zdrOsiguranje;

	@NotNull
	@Column(nullable = false)
	private LocalDate datumRodjenja;
	
	
	@ManyToOne
	@JoinColumn(name = "id_odeljenja")
	private Odeljenje odeljenje;
	
	
	@ManyToOne
	@JoinColumn(name = "id_dijagnoze")
	private Dijagnoza dijagnoza;
	

	public Pacijent() {
		
	}
	
	public Pacijent(int id, String ime, boolean zdrOsiguranje, LocalDate datumRodjenja, int idOdeljenja, int idDijagnoze) {
		this.id = id;
		this.ime = ime;
		this.zdrOsiguranje = zdrOsiguranje;
		this.datumRodjenja = datumRodjenja;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getIme() {
		return ime;
	}
	public void setIme(String ime) {
		this.ime = ime;
	}
	public boolean isZdrOsiguranje() {
		return zdrOsiguranje;
	}
	public void setZdrOsiguranje(boolean zdrOsiguranje) {
		this.zdrOsiguranje = zdrOsiguranje;
	}
	public LocalDate getDatumRodjenja() {
		return datumRodjenja;
	}
	public void setDatumRodjenja(LocalDate datumRodjenja) {
		this.datumRodjenja = datumRodjenja;
	}

	public Odeljenje getOdeljenje() {
		return odeljenje;
	}

	public void setOdeljenje(Odeljenje odeljenje) {
		this.odeljenje = odeljenje;
	}

	public Dijagnoza getDijagnoza() {
		return dijagnoza;
	}

	public void setDijagnoza(Dijagnoza dijagnoza) {
		this.dijagnoza = dijagnoza;
	}
}
