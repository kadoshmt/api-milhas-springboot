package br.com.janesroberto.milhas.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.janesroberto.milhas.exception.UserNotFoundException;
import br.com.janesroberto.milhas.model.User;
import br.com.janesroberto.milhas.service.UserService;

@Service
public class UserDetailsServiceApi implements UserDetailsService{
	
	@Autowired
	UserService userService;	
	

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("Chamou UserDetailsServiceApi.loadUserByUsername() **************************************************************************");			
//		User user = userService.getUserByEmailAuth(username)
//				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with E-mail: " + username));
		User user = null;
		try {
			user = userService.getUserByEmailAuth(username)
					.orElseThrow(() -> new UsernameNotFoundException("User Not Found with E-mail: " + username));
		} catch (UsernameNotFoundException | UserNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return UserDetailsApi.build(user);
	}

}
