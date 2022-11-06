package br.com.janesroberto.milhas.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
import org.springframework.web.bind.annotation.RequestParam;
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
@RequestMapping("/api/point")
public class PointController {

	@Autowired
	private PointService pointService;

	@Autowired
	private AirlineService airlineService;

	@Autowired
	private UserService userService;

	private User user;

	private void setUserFromContext() {
		this.user = (User) userService.getUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());
	}

//	@GetMapping
//	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
//	public List<PointDto> list() {
//		setUserFromContext();
//		return pointService.getAllPointsByUser(user);
//	}
	
	@GetMapping
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public Page<PointDto> list(@RequestParam(required=false) String airlineId, @PageableDefault(sort="createdDate", direction=Direction.DESC, page = 0, size = 10) Pageable paginacao) {
		setUserFromContext();
		Long id = (airlineId == null) ? null : Long.parseLong(airlineId);
		return pointService.listAllPoints(id, user, paginacao);
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<PointDto> read(@PathVariable Long id) {
		setUserFromContext();
		Point point = pointService.getPointsById(id);
		if (point != null && point.getUser().getId() == user.getId()) {
			return ResponseEntity.ok(new PointDto(point));
		} else if (point != null && point.getUser().getId() != user.getId()) {
			return ResponseEntity.status(401).build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping
	@Transactional
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<PointDto> create(@RequestBody @Valid PointFormDto form, UriComponentsBuilder uriBuilder) {
		setUserFromContext();
		Airline airline = airlineService.getAirlineById(form.getAirlineId());
		if (user != null && airline != null) {
			PointDto point = pointService.addPoint(form, user, airline);
			URI uri = uriBuilder.path("/api/point/{id}").buildAndExpand(point.getId()).toUri();
			return ResponseEntity.created(uri).body(point);
		}
		return ResponseEntity.badRequest().build();
	}

	@PutMapping("/{id}")
	@Transactional
	@PreAuthorize("hasRole('USER') or hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<PointDto> update(@PathVariable Long id, @RequestBody @Valid PointFormDto form) {
		setUserFromContext();
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
		setUserFromContext();
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
		setUserFromContext();
		Boolean pointDeleted = pointService.deletePoint(id, user);
		if (pointDeleted) {
			return ResponseEntity.status(204).build();
		}
		return ResponseEntity.notFound().build();
	}

}
