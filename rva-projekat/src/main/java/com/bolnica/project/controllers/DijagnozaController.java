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

import com.bolnica.project.models.Dijagnoza;
import com.bolnica.project.services.DijagnozaService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class DijagnozaController {
	
	private final DijagnozaService service;

    public DijagnozaController(DijagnozaService service) {
        this.service = service;
    }

    @GetMapping("/dijagnoze")
    public ResponseEntity<List<Dijagnoza>> getAll() {
        List<Dijagnoza> dijagnoze = service.getAll();
        return ResponseEntity.ok(dijagnoze);
    }

    @GetMapping("/dijagnoza/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<Dijagnoza> dijagnoza = service.findById(id);
        if (dijagnoza.isEmpty()) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND)
        		.body(String.format("Dijagnoza with ID: %d does not exist.", id));
        }
        return ResponseEntity.ok(dijagnoza);
    }

    @GetMapping("/dijagnoza/oznaka/{oznaka}")
    public ResponseEntity<?> getDijagnozeByOznaka(@PathVariable String oznaka) {
        List<Dijagnoza> lista = service.getDijanozeByOznaka(oznaka);
        if (lista.isEmpty()) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND)
        		.body(String.format("No dijagnoza found with oznaka containing: %s", oznaka));
        }
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/dijagnoza")
    public ResponseEntity<String> create(@Valid @RequestBody Dijagnoza dijagnoza) {
        if (service.existsById(dijagnoza.getId())) {
        	return ResponseEntity.status(HttpStatus.CONFLICT)
        		.body(String.format("Dijagnoza with ID: %d already exists!", dijagnoza.getId()));
        }
        service.create(dijagnoza);
        return ResponseEntity.status(HttpStatus.CREATED)
        	.body("Dijagnoza has been successfully created!");
    }

    @PutMapping("/dijagnoza/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @Valid @RequestBody Dijagnoza nova) {
        Optional<Dijagnoza> updated = service.update(nova, id);
        if (updated.isEmpty()) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND)
        		.body(String.format("Dijagnoza with ID: %d does not exist!", id));
        }
        return ResponseEntity.ok(String.format("Dijagnoza with ID: %d has been successfully updated!", id));
    }

    @DeleteMapping("/dijagnoza/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        if (!service.existsById(id)) {
        	return ResponseEntity.status(HttpStatus.NOT_FOUND)
        		.body(String.format("Dijagnoza with ID: %d does not exist!", id));
        }
        service.delete(id);
        return ResponseEntity.ok(String.format("Dijagnoza with ID: %d has been successfully deleted!", id));
    }
}
