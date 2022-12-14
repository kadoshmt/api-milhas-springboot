package br.com.janesroberto.milhas.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.janesroberto.milhas.dto.JwtLoginResponseDto;
import br.com.janesroberto.milhas.dto.MessageDto;
import br.com.janesroberto.milhas.dto.UserSignInDto;
import br.com.janesroberto.milhas.dto.UserSignUpDto;
import br.com.janesroberto.milhas.model.ERole;
import br.com.janesroberto.milhas.model.Role;
import br.com.janesroberto.milhas.model.User;
import br.com.janesroberto.milhas.repository.RoleRepository;
import br.com.janesroberto.milhas.repository.UserRepository;
import br.com.janesroberto.milhas.security.JwtUtils;
import br.com.janesroberto.milhas.security.UserDetailsApi;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody UserSignInDto loginRequest) {

		System.out.println(
				"Chamou Controller **************************************************************************");
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		System.out.println(
				"Chamou m??todo authenticationManager no Controller **************************************************************************");
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsApi userDetails = (UserDetailsApi) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtLoginResponseDto(jwt, userDetails.getId(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody UserSignUpDto signUpRequest) {//	

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageDto("Conflict: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(// signUpRequest.getUsername(),
				signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "sadmin":
					Role modRole = roleRepository.findByName(ERole.ROLE_SUPER_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageDto("User registered successfully!"));
	}
}
