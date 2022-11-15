package br.com.janesroberto.milhas.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.janesroberto.milhas.dto.AirlineDto;
import br.com.janesroberto.milhas.dto.AirlineFormDto;
import br.com.janesroberto.milhas.exception.UserNotFoundException;
import br.com.janesroberto.milhas.model.Airline;
import br.com.janesroberto.milhas.model.User;
import br.com.janesroberto.milhas.service.AdminAirlineService;
import br.com.janesroberto.milhas.service.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/admin/airline")
public class AdminAirlineController {

	@Autowired
	private AdminAirlineService airlineService;

	@Autowired
	private UserService userService;


	@GetMapping
	@PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public List<AirlineDto> list() {
		return airlineService.getAllAirlines();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<AirlineDto> read(@PathVariable Long id) {
		Airline airline = airlineService.getAirlineById(id);
		if (airline != null) {
			return ResponseEntity.ok(new AirlineDto(airline));
		}
		return ResponseEntity.notFound().build();
	}

	@PostMapping	
	@PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<AirlineDto> create(@RequestBody @Valid AirlineFormDto form, UriComponentsBuilder uriBuilder) throws UserNotFoundException {
		User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());	
		AirlineDto airline = airlineService.addAirline(form, user);
		URI uri = uriBuilder.path("/user/{id}").buildAndExpand(airline.getId()).toUri();
		return ResponseEntity.created(uri).body(airline);
	}

	@PutMapping("/{id}")	
	@PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<AirlineDto> update(@PathVariable Long id, @RequestBody @Valid AirlineFormDto form) throws UserNotFoundException {		
		User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		AirlineDto airline = airlineService.updateAirline(form, id, user);
		return ResponseEntity.ok(airline);
	}

	@DeleteMapping("/{id}")	
	@PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<AirlineDto> delete(@PathVariable Long id) throws UserNotFoundException {
		User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		Boolean airlineDeleted = airlineService.deleteAirline(id, user);
		if (airlineDeleted) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}
