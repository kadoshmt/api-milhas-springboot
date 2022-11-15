package br.com.janesroberto.milhas.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

import br.com.janesroberto.milhas.dto.AirlineFormDto;
import br.com.janesroberto.milhas.dto.UserDto;
import br.com.janesroberto.milhas.service.UserService;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/admin/user")
public class AdminUserController {

	@Autowired
	private UserService userService;

	@GetMapping
	@PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public List<UserDto> list() {
		return userService.getAllUsers();
	}

	@GetMapping("/{id}")
	@PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<?> read(@PathVariable Long id) {
		// TO-DO: Any admin user can read user's data
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not implemented yet!");
	}

	@PostMapping
	@PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<?> create(@RequestBody @Valid AirlineFormDto form, UriComponentsBuilder uriBuilder) {
		// TO-DO: Any admin user can create another user with ADMIN_ROLE or USER_ROLE
		// TO-DO: ONLY super admin user can create another user with SUPER_ADMIN_ROLE
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not implemented yet!");
	}

	@PutMapping("/{id}")
	@PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid AirlineFormDto form) {
		// TO-DO: Any admin user can update another user with USER_ROLE and itself
		// TO-DO: ONLY super admin user can update another users with ADMIN_ROLE and itself
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not implemented yet!");
	}

	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('SUPER_ADMIN') or hasRole('ADMIN')")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		// TO-DO: Any admin user can delete another user with USER_ROLE and itself
		// TO-DO: ONLY super admin user can delete another users with ADMIN_ROLE and itself
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Not implemented yet!");
	}

}
