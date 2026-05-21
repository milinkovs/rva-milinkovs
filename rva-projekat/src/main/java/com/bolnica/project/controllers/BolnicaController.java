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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolnica.project.models.Bolnica;
import com.bolnica.project.services.BolnicaService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class BolnicaController {
	
	private final BolnicaService service;
	
	public BolnicaController(BolnicaService service) {
		this.service = service;
	}
	
	@GetMapping("/bolnice") 
	public ResponseEntity<List<Bolnica>> getAllBolnice(){
		List<Bolnica> bolnice = service.getAll();
		return ResponseEntity.ok(bolnice);
	}
	
	@GetMapping("/bolnica/naziv/{naziv}")
	public ResponseEntity<?> getBolnicalByNaziv(@PathVariable String naziv){
		List<Bolnica> bolnice = service.getBolniceByNaziv(naziv);
		if (bolnice.isEmpty())
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(String.format("No bolnice exists with naziv: %s", naziv));
			
		return ResponseEntity.ok(bolnice);
	}
	
	@GetMapping("/bolnica/{id}")
	public ResponseEntity<?> getBolnicaById(@PathVariable int id){
		
		Optional<Bolnica> bolnica = service.findById(id);
		if(bolnica.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(String.format("Bolnica with id: %s does not exist", id));
		}
		return ResponseEntity.ok(bolnica);
	}
		
	
	@PostMapping("/bolnica")
	public ResponseEntity<String> createBolnica(@Valid @RequestBody Bolnica bolnica){
		if (service.existsById(bolnica.getId())) { 
			return ResponseEntity.status(HttpStatus.CONFLICT)
				.body(String.format("Bolnica with id: %s already exists! ", bolnica.getId()));
		}
		service.create(bolnica);
		return ResponseEntity.status(HttpStatus.CREATED)
			.body(String.format("Bolnica with id: %s has been created successfully!", bolnica.getId()));
	}
	
	@DeleteMapping("/bolnica/{id}")
	public ResponseEntity<String> deleteBolnica(@PathVariable int id){
		if (service.existsById(id)) {
			service.delete(id);
			return ResponseEntity.ok(String.format("Bolnica with id: %s has been deleted successfully!", id));
		}
		return ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(String.format("Bolnica with id: %s does not exist!", id));
	}
	
	@PutMapping("/bolnica/{id}")
	public ResponseEntity<String> updateBolnica(@PathVariable int id, @Valid @RequestBody Bolnica bolnica){
		Optional<Bolnica> updatedBolnica = service.update(bolnica, id);
		if (updatedBolnica.isEmpty()) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
				.body(String.format("Bolnica with id: %s does not exist!", id));
		}
		return ResponseEntity.ok(String.format("Bolnica with id: %s has been updated successfully!", id));
	}
}
