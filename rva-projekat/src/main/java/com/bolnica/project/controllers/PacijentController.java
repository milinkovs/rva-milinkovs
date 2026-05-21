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
import com.bolnica.project.models.Odeljenje;
import com.bolnica.project.models.Pacijent;
import com.bolnica.project.services.PacijentService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class PacijentController {
	
	private final PacijentService service;

    public PacijentController(PacijentService service) {
        this.service = service;
    }

    @GetMapping("/pacijenti")
    public ResponseEntity<List<Pacijent>> getAll() {
        return ResponseEntity.ok(service.getAll());
    }

    @GetMapping("/pacijent/{id}")
    public ResponseEntity<?> getById(@PathVariable int id) {
        Optional<Pacijent> pacijent = service.findById(id);
        if (pacijent.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Pacijent with ID: %d does not exist.", id));
        }
        return ResponseEntity.ok(pacijent);
    }

    @GetMapping("/pacijent/osiguranje/{vrednost}")
    public ResponseEntity<?> getByOsiguranje(@PathVariable boolean vrednost) {
        List<Pacijent> lista = service.getByOsiguranje(vrednost);
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("No patients found with osiguranje: %b", vrednost));
        }
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/pacijent/po-dijagnozi")
    public ResponseEntity<?> getByDijagnoza(@RequestBody Dijagnoza dijagnoza) {
        List<Pacijent> lista = service.getPacijentsByDijagnoza(dijagnoza);
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("No patients found with dijagnoza ID: %d", dijagnoza.getId()));
        }
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/pacijent/po-odeljenju")
    public ResponseEntity<?> getByOdeljenje(@RequestBody Odeljenje odeljenje) {
        List<Pacijent> lista = service.getPacijentsByOdeljenje(odeljenje);
        if (lista.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("No patients found with department ID: %d", odeljenje.getId()));
        }
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/pacijent")
    public ResponseEntity<String> create(@Valid @RequestBody Pacijent pacijent) {
        if (service.existsById(pacijent.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(String.format("Pacijent with ID: %d already exists!", pacijent.getId()));
        }
        service.create(pacijent);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body("Pacijent successfully created!");
    }

    @PutMapping("/pacijent/{id}")
    public ResponseEntity<String> update(@PathVariable int id, @Valid @RequestBody Pacijent pacijent) {
        Optional<Pacijent> updated = service.update(pacijent, id);
        if (updated.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Pacijent with ID: %d does not exist!", id));
        }
        return ResponseEntity.ok(String.format("Pacijent with ID: %d has been successfully updated!", id));
    }

    @DeleteMapping("/pacijent/{id}")
    public ResponseEntity<String> delete(@PathVariable int id) {
        if (!service.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(String.format("Pacijent with ID: %d does not exist!", id));
        }
        service.delete(id);
        return ResponseEntity.ok(String.format("Pacijent with ID: %d has been successfully deleted!", id));
    }
}
