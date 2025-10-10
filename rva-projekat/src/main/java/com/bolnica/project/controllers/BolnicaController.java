package com.bolnica.project.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bolnica.project.implementation.BolnicaServiceImpl;
import com.bolnica.project.models.Bolnica;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BolnicaController {
	
	private final BolnicaServiceImpl service;
	
	public BolnicaController(BolnicaServiceImpl service) {
		this.service = service;
	}
	
	@GetMapping("/bolnice") 
	public ResponseEntity<List<Bolnica>> getAllBolnice(){
		List<Bolnica> bolnice = service.getAll();
		return new ResponseEntity<>(bolnice, HttpStatus.OK);
	}
	
	@GetMapping("/bolnica/naziv/{naziv}")
	public ResponseEntity<?> getBolnicalByNaziv(@PathVariable String naziv){
		List<Bolnica> bolnice = service.getBolniceByNaziv(naziv);
		if (bolnice.isEmpty())
			return new ResponseEntity<String>(String.format(("No bolnice exists with naziv: %s"), naziv), HttpStatus.NOT_FOUND);
			
		return new ResponseEntity<>(bolnice, HttpStatus.OK);
	}
	
	@GetMapping("/bolnica/{id}")
	public ResponseEntity<?> getBolnicaById(@PathVariable int id){
		
		Optional<Bolnica> bolnica = service.findById(id);
		if(bolnica.isEmpty()) {
			return new ResponseEntity<String>(String.format("Bolnica with id: %s does not exist", id), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(bolnica, HttpStatus.OK);
	}
		
	
	@PostMapping("/bolnica")
	public ResponseEntity<String> createBolnica(@RequestBody Bolnica bolnica){
		if (service.existsById(bolnica.getId())) { 
			return new ResponseEntity<>(String.format("Bolnica with id: %s already exists! ", bolnica.getId()), HttpStatus.CONFLICT);
		}
		service.create(bolnica);
		return new ResponseEntity<>(String.format(("Bolnica with id: %s has been created successfully!"), bolnica.getId()), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/bolnica/{id}")
	public ResponseEntity<String> deleteBolnica(@PathVariable int id){
		if (service.existsById(id)) {
			service.delete(id);
			return new ResponseEntity<>(String.format(("Bolnica with id: %s has been deleted successfully!"), id), HttpStatus.OK);
		}
		return new ResponseEntity<>(String.format(("Bolnica with id: %s does not exist!"), id), HttpStatus.NOT_FOUND);
	}
	
	@PutMapping("/bolnica/{id}")
	public ResponseEntity<String> updateBolnica(@PathVariable int id, @RequestBody Bolnica bolnica){
		Optional<Bolnica> updatedBolnica = service.update(bolnica, id);
		if (updatedBolnica.isEmpty()) {
			return new ResponseEntity<>(String.format(("Bolnica with id: %s does not exist!"), id), HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(String.format(("Bolnica with id: %s has been updated successfully!"), id), HttpStatus.OK);
	}
}
