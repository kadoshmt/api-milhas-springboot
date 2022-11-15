package br.com.janesroberto.milhas.controller;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.janesroberto.milhas.dto.PointConfirmFormDto;
import br.com.janesroberto.milhas.dto.PointDto;
import br.com.janesroberto.milhas.dto.PointFormDto;
import br.com.janesroberto.milhas.exception.AirlineNotFoundException;
import br.com.janesroberto.milhas.exception.PointNotFoundException;
import br.com.janesroberto.milhas.exception.UserNotFoundException;
import br.com.janesroberto.milhas.model.Airline;
import br.com.janesroberto.milhas.model.Point;
import br.com.janesroberto.milhas.model.User;
import br.com.janesroberto.milhas.service.AirlineService;
import br.com.janesroberto.milhas.service.PointService;
import br.com.janesroberto.milhas.service.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/point")
public class PointController {

	@Autowired
	private PointService pointService;

	@Autowired
	private AirlineService airlineService;

	@Autowired
	private UserService userService;


//	@GetMapping
//	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
//	public List<PointDto> list() {
//		setUserFromContext();
//		return pointService.getAllPointsByUser(user);
//	}
	
	@GetMapping
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public Page<PointDto> list(@RequestParam(required=false) String airlineId, @PageableDefault(page = 0, size = 10, sort="createdDate", direction=Direction.DESC) Pageable pageable) throws UserNotFoundException {
		User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());	
		Long id = (airlineId == null) ? null : Long.parseLong(airlineId);
		return pointService.listAllPoints(id, user, pageable);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<PointDto> read(@PathVariable Long id) throws UserNotFoundException, PointNotFoundException {
		User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());	
		Point point = pointService.getPointsById(id);
		if (point.getUser().getId() != user.getId()) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();			
		}
		return ResponseEntity.ok(new PointDto(point));
	}
		

	@PostMapping	
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<PointDto> create(@RequestBody @Valid PointFormDto form, UriComponentsBuilder uriBuilder) throws UserNotFoundException, AirlineNotFoundException {
		User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());	
		Airline airline = airlineService.getAirlineById(form.getAirlineId());
		PointDto point = pointService.addPoint(form, user, airline);
		URI uri = uriBuilder.path("/api/point/{id}").buildAndExpand(point.getId()).toUri();
		return ResponseEntity.created(uri).body(point);
	}

	@PutMapping("/{id}")	
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<PointDto> update(@PathVariable Long id, @RequestBody @Valid PointFormDto form) throws UserNotFoundException, PointNotFoundException, AirlineNotFoundException {
		User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());	
		PointDto point = pointService.updatePoint(form, id, user);
		return ResponseEntity.ok(point);
	}

	@PutMapping("/confirm/{id}")	
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<PointDto> confirm(@PathVariable Long id, @RequestBody @Valid PointConfirmFormDto form) throws UserNotFoundException, PointNotFoundException {
		User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());	
		PointDto point = pointService.confirmPoint(form, id, user);
		return ResponseEntity.ok(point);
	}

	@DeleteMapping("/{id}")	
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<PointDto> delete(@PathVariable Long id) throws UserNotFoundException, PointNotFoundException {
		User user = userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());	
		Boolean pointDeleted = pointService.deletePoint(id, user);
		if (!pointDeleted) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.status(204).build();
	}

}
