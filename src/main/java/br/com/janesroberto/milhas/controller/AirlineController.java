package br.com.janesroberto.milhas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.janesroberto.milhas.dto.AirlineDto;
import br.com.janesroberto.milhas.exception.AirlineNotFoundException;
import br.com.janesroberto.milhas.model.Airline;
import br.com.janesroberto.milhas.service.AirlineService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/airline")
public class AirlineController {

	@Autowired
	private AirlineService airlineService;	
	

	@GetMapping
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public List<AirlineDto> list() {
		return airlineService.getAllAirlines();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<AirlineDto> read(@PathVariable Long id) throws AirlineNotFoundException {
		Airline airline = airlineService.getAirlineById(id);
		return ResponseEntity.ok(new AirlineDto(airline));
	}

}
