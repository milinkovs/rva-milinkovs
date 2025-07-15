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

import com.bolnica.project.implementation.PacijentServiceImpl;
import com.bolnica.project.models.Dijagnoza;
import com.bolnica.project.models.Odeljenje;
import com.bolnica.project.models.Pacijent;

@RestController
public class PacijentController {
	
	private final PacijentServiceImpl service;

    public PacijentController(PacijentServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/pacijenti")
    public ResponseEntity<List<Pacijent>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/pacijent/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<Pacijent> pacijent = service.findById(id);
        if (pacijent.isEmpty()) {
            return new ResponseEntity<>(String.format("Pacijent with ID: %d does not exist.", id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(pacijent, HttpStatus.OK);
    }

    @GetMapping("pacijent/osiguranje/{vrednost}")
    public ResponseEntity<?> getByOsiguranje(@PathVariable boolean vrednost) {
        List<Pacijent> lista = service.getByOsiguranje(vrednost);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(String.format("No patients found with osiguranje: %b", vrednost), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("pacijent/po-dijagnozi")
    public ResponseEntity<?> getByDijagnoza(@RequestBody Dijagnoza dijagnoza) {
        List<Pacijent> lista = service.getPacijentsByDijagnoza(dijagnoza);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(String.format("No patients found with dijagnoza ID: %d", dijagnoza.getId()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("pacijent/po-odeljenju")
    public ResponseEntity<?> getByOdeljenje(@RequestBody Odeljenje odeljenje) {
        List<Pacijent> lista = service.getPacijentsByOdeljenje(odeljenje);
        if (lista.isEmpty()) {
            return new ResponseEntity<>(String.format("No patients found with department ID: %d", odeljenje.getId()), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @PostMapping("/pacijent")
    public ResponseEntity<String> create(@RequestBody Pacijent pacijent) {
        if (service.existsById(pacijent.getId())) {
            return new ResponseEntity<>(String.format("Pacijent with ID: %d already exists!", pacijent.getId()), HttpStatus.CONFLICT);
        }
        service.create(pacijent);
        return new ResponseEntity<>(String.format("Pacijent successfully created!"), HttpStatus.CREATED);
    }

    @PutMapping("/pacijent/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @RequestBody Pacijent pacijent) {
        Optional<Pacijent> updated = service.update(pacijent, id);
        if (updated.isEmpty()) {
            return new ResponseEntity<>(String.format("Pacijent with ID: %d does not exist!", id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(String.format("Pacijent with ID: %d has been successfully updated!", id), HttpStatus.OK);
    }

    @DeleteMapping("/pacijent/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        if (!service.existsById(id)) {
            return new ResponseEntity<>(String.format("Pacijent with ID: %d does not exist!", id), HttpStatus.NOT_FOUND);
        }
        service.delete(id);
        return new ResponseEntity<>(String.format("Pacijent with ID: %d has been successfully deleted!", id), HttpStatus.OK);
    }
}
