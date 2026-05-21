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
import com.bolnica.project.models.Odeljenje;
import com.bolnica.project.services.OdeljenjeService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class OdeljenjeController {
	
	private final OdeljenjeService service;

    public OdeljenjeController(OdeljenjeService service) {
        this.service = service;
    }

    @GetMapping("/odeljenja")
    public ResponseEntity<List<Odeljenje>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/odeljenje/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<Odeljenje> odeljenje = service.findById(id);
        if (odeljenje.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Odeljenje with ID: %d does not exist.", id));
        }
        return ResponseEntity.ok(odeljenje);
    }

    @GetMapping("/odeljenje/naziv/{naziv}")
    public ResponseEntity<?> getByName(@PathVariable String naziv) {
        List<Odeljenje> list = service.getOdeljenjaByNaziv(naziv);
        if (list.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("No odeljenja exists with naziv: %s", naziv));
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping("/odeljenje")
    public ResponseEntity<String> create(@Valid @RequestBody Odeljenje odeljenje) {
        if (service.existsById(odeljenje.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(String.format("Odeljenje with ID: %d already exists!", odeljenje.getId()));
        }
        service.create(odeljenje);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Odeljenje successfully created!");
    }
    
    @PostMapping("/odeljenja/po-bolnici")
    public ResponseEntity<?> getByBolnica(@RequestBody Bolnica bolnica) {
        List<Odeljenje> odeljenja = service.getOdeljenjaByBolnica(bolnica);
        if (odeljenja.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("No odeljenja found for hospital with ID: %d", bolnica.getId()));
        }
        return ResponseEntity.ok(odeljenja);
    }


    @PutMapping("/odeljenje/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @Valid @RequestBody Odeljenje odeljenje) {
        Optional<Odeljenje> updatedOdeljenje = service.update(odeljenje, id);
        if (updatedOdeljenje.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Odeljenje with ID: %d does not exist!", id));
        }
        return ResponseEntity.ok(String.format("Odeljenje with ID: %d has been successfully updated!", id));
    }

    @DeleteMapping("/odeljenje/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        if (!service.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Odeljenje with ID: %d does not exist!", id));
        }
        service.delete(id);
        return ResponseEntity.ok(String.format("Odeljenje with ID: %d has been successfully deleted!", id));
    }
}
