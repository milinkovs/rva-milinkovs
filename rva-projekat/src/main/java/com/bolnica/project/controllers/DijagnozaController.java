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

import com.bolnica.project.implementation.DijagnozaServiceImpl;
import com.bolnica.project.models.Dijagnoza;

@CrossOrigin
@RestController
public class DijagnozaController {
	
	private final DijagnozaServiceImpl service;

    public DijagnozaController(DijagnozaServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/dijagnoze")
    public ResponseEntity<List<Dijagnoza>> getAll() {
        List<Dijagnoza> dijagnoze = service.getAll();
        return new ResponseEntity<>(dijagnoze, HttpStatus.OK);
    }

    @GetMapping("/dijagnoza/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<Dijagnoza> dijagnoza = service.findById(id);
        if (dijagnoza.isEmpty()) {
        	return new ResponseEntity<>(String.format("Dijagnoza with ID: %d does not exist.", id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dijagnoza, HttpStatus.OK);
    }

    @GetMapping("dijagnoza/oznaka/{oznaka}")
    public ResponseEntity<?> getDijagnozeByOznaka(@PathVariable String oznaka) {
        List<Dijagnoza> lista = service.getDijanozeByOznaka(oznaka);
        if (lista.isEmpty()) {
        	return new ResponseEntity<>(String.format("No dijagnoza found with oznaka containing: %s", oznaka), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/dijagnoza")
    public ResponseEntity<String> create(@RequestBody Dijagnoza dijagnoza) {
        if (service.existsById(dijagnoza.getId())) {
        	return new ResponseEntity<>(String.format("Dijagnoza with ID: %d already exists!", dijagnoza.getId()), HttpStatus.CONFLICT);
        }
        service.create(dijagnoza);
        return new ResponseEntity<>(String.format("Dijagnoza has been successfully created!"), HttpStatus.CREATED);
    }

    @PutMapping("/dijagnoza/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Dijagnoza nova) {
        Optional<Dijagnoza> updated = service.update(nova, id);
        if (updated.isEmpty()) {
        	return new ResponseEntity<>(String.format("Dijagnoza with ID: %d does not exist!", id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(String.format("Dijagnoza with ID: %d has been successfully updated!", id), HttpStatus.OK);
    }

    @DeleteMapping("/dijagnoza/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        if (!service.existsById(id)) {
        	return new ResponseEntity<>(String.format("Dijagnoza with ID: %d does not exist!", id), HttpStatus.NOT_FOUND);
        }
        service.delete(id);
        return new ResponseEntity<>(String.format("Dijagnoza with ID: %d has been successfully deleted!", id), HttpStatus.OK);
    }
}
