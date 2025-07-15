package com.bolnica.project.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.bolnica.project.implementation.OdeljenjeServiceImpl;
import com.bolnica.project.models.Bolnica;
import com.bolnica.project.models.Odeljenje;

@RestController
public class OdeljenjeController {
	
	private final OdeljenjeServiceImpl service;

    public OdeljenjeController(OdeljenjeServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/odeljenja")
    public ResponseEntity<List<Odeljenje>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("odeljenje/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<Odeljenje> odeljenje = service.findById(id);
        if (odeljenje.isEmpty()) {
            return new ResponseEntity<>(String.format("Odeljenje with ID: %d does not exist.", id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(odeljenje, HttpStatus.OK);
    }

    @GetMapping("/odeljenje/naziv/{naziv}")
    public ResponseEntity<?> getByName(@PathVariable String naziv) {
        List<Odeljenje> list = service.getOdeljenjaByNaziv(naziv);
        if (list.isEmpty()) {
            return new ResponseEntity<>(String.format("No odeljenja exists with naziv: %s", naziv), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/odeljenje")
    public ResponseEntity<String> create(@RequestBody Odeljenje odeljenje) {
        if (service.existsById(odeljenje.getId())) {
            return new ResponseEntity<>(String.format("Odeljenje with ID: %d already exists!", odeljenje.getId()), HttpStatus.CONFLICT);
        }
        service.create(odeljenje);
        return new ResponseEntity<>(String.format("Odeljenje successfully created!"), HttpStatus.CREATED);
    }
    
    @PostMapping("odeljenja/po-bolnici")
    public ResponseEntity<?> getByBolnica(@RequestBody Bolnica bolnica) {
        List<Odeljenje> odeljenja = service.getOdeljenjaByBolnica(bolnica);
        if (odeljenja.isEmpty()) {
            return new ResponseEntity<>(String.format("No odeljenja found for hospital with ID: %d", bolnica.getId()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(odeljenja, HttpStatus.OK);
    }


    @PutMapping("odeljenje/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Odeljenje odeljenje) {
        Optional<Odeljenje> updatedOdeljenje = service.update(odeljenje, id);
        if (updatedOdeljenje.isEmpty()) {
            return new ResponseEntity<>(String.format("Odeljenje with ID: %d does not exist!", id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(String.format("Odeljenje with ID: %d has been successfully updated!", id), HttpStatus.OK);
    }

    @DeleteMapping("odeljenje/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        if (!service.existsById(id)) {
            return new ResponseEntity<>(String.format("Odeljenje with ID: %d does not exist!", id), HttpStatus.NOT_FOUND);
        }
        service.delete(id);
        return new ResponseEntity<>(String.format("Odeljenje with ID: %d has been successfully deleted!", id), HttpStatus.OK);
    }
}
