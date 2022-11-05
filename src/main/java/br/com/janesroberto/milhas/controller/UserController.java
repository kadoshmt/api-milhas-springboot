package br.com.janesroberto.milhas.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.janesroberto.milhas.dto.UserDto;
import br.com.janesroberto.milhas.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	@GetMapping
	public List<UserDto> list() {		
		return userService.getAllUsers();
	}

}
