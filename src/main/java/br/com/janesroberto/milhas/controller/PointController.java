package br.com.janesroberto.milhas.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.janesroberto.milhas.dto.PointConfirmFormDto;
import br.com.janesroberto.milhas.dto.PointDto;
import br.com.janesroberto.milhas.dto.PointFormDto;
import br.com.janesroberto.milhas.model.Airline;
import br.com.janesroberto.milhas.model.Point;
import br.com.janesroberto.milhas.model.User;
import br.com.janesroberto.milhas.service.AirlineService;
import br.com.janesroberto.milhas.service.PointService;
import br.com.janesroberto.milhas.service.UserService;

@RestController
@RequestMapping("/point")
public class PointController {

	@Autowired
	private PointService pointService;
	
	@Autowired
	private AirlineService airlineService;

	@Autowired
	private UserService userService;
	
	
	@GetMapping
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public List<PointDto> list() {		
		User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		return pointService.getAllPointsByUser(user);
	}
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<PointDto> read(@PathVariable Long id) {
		Point point = pointService.getPointsById(id);
		if (point != null) {
			return ResponseEntity.ok(new PointDto(point));
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	@Transactional
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<PointDto> create(@RequestBody @Valid PointFormDto form, UriComponentsBuilder uriBuilder) {
		User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		Airline airline = airlineService.getAirlineById(form.getAirlineId());
		if (user != null && airline != null) {
			PointDto point = pointService.addPoint(form, user, airline);
			URI uri = uriBuilder.path("/point/{id}").buildAndExpand(point.getId()).toUri();
			return ResponseEntity.created(uri).body(point);
		}
		return ResponseEntity.badRequest().build();
	}
	
	@PutMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<PointDto> update(@PathVariable Long id, @RequestBody @Valid PointFormDto form) {
		User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		if (user != null) {
			PointDto point = pointService.updatePoint(form, id, user);
			return ResponseEntity.ok(point);
		}
		return ResponseEntity.notFound().build();
	}
	
	@PutMapping("/confirm/{id}")
	@Transactional
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<PointDto> confirm(@PathVariable Long id, @RequestBody @Valid PointConfirmFormDto form) {
		User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		if (user != null) {
			PointDto point = pointService.confirmPoint(form, id, user);
			return ResponseEntity.ok(point);
		}
		return ResponseEntity.notFound().build();
	}
	
	@DeleteMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<PointDto> delete(@PathVariable Long id) {
		User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
		Boolean pointDeleted = pointService.deletePoint(id, user);
		if (pointDeleted) {
			return ResponseEntity.ok().build();
		}
		return ResponseEntity.notFound().build();
	}

}
