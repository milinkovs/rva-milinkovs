package com.bolnica.project.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bolnica.project.implementation.BolnicaServiceImpl;

@RestController
public class BolnicaController {
	
	private BolnicaServiceImpl bolnicaService;
	
	public BolnicaController(BolnicaServiceImpl bolnicaService) {
		this.bolnicaService = bolnicaService;
	}
	
	@GetMapping("/bolnice")
	public ResponseEntity<?> getAllBolnice(){
		return ResponseEntity.ok(bolnicaService.getAll());
	}
	
}
