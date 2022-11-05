package br.com.janesroberto.milhas.dto;

import br.com.janesroberto.milhas.model.User;

public class UserDto {
	
	private Long id;
	private String email;	
	
	
	public UserDto(Long id, String email) {		
		this.id = id;
		this.email = email;
	}	
	
	public UserDto(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
	}

	public Long getId() {
		return id;
	}
	
	public String getEmail() {
		return email;
	}

}
