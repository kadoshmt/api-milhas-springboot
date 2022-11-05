package br.com.janesroberto.milhas.dto;

import br.com.janesroberto.milhas.model.User;

public class UserSignInDto {
	
	
	private String email;
	private String password;
	
	
	
	public UserSignInDto(String email, String password) {		
		this.password = password;
		this.email = email;
	}	
	
	public UserSignInDto(User user) {
		this.password = user.getPassword();
		this.email = user.getEmail();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

}
