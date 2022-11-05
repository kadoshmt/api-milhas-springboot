package br.com.janesroberto.milhas.security;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.janesroberto.milhas.model.User;

public class UserDetailsApi implements UserDetails{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;	

	private String email;
	
	@JsonIgnore
	private String password;
	
	private Collection<? extends GrantedAuthority> authorities;
	
	public UserDetailsApi(Long id, String email, String password,
			Collection<? extends GrantedAuthority> authorities) {
		System.out.println("Chamou UserDetailsApi Constructor **************************************************************************");
		this.id = id;		
		this.email = email;
		this.password = password;
		this.authorities = authorities;		
	}
	
	public static UserDetailsApi build(User user) {
		System.out.println("Chamou UserDetailsApi.build() **************************************************************************");
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new UserDetailsApi(
				user.getId(),				 
				user.getEmail(),
				user.getPassword(), 
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return authorities;
	}
	
	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}	
	

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsApi user = (UserDetailsApi) o;
		return Objects.equals(id, user.id);
	}

	

}
