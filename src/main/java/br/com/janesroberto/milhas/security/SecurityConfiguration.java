package br.com.janesroberto.milhas.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration {

	@Autowired
	UserDetailsServiceApi userDetailsService;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		System.out.println("Chamou SecurityConfiguration.authenticationJwtTokenFilter() **************************************************************************");
		return new AuthTokenFilter();
	}

	/***
	 * Validate UsernamePasswordAuthenticationToken object. If successful,
	 * AuthenticationManager returns a fully populated Authentication object
	 * (including granted authorities).
	 * 
	 * @param authenticationConfiguration
	 * @return AuthenticationManager
	 * @throws Exception
	 */
	@Bean	
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		System.out.println("Chamou SecurityConfiguration.AuthenticationManager() **************************************************************************");
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		System.out.println("Chamou SecurityConfiguration.DaoAuthenticationProvider() **************************************************************************");
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());

		return authProvider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		System.out.println("Chamou SecurityConfiguration.PasswordEncoder() **************************************************************************");
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		System.out.println("Chamou SecurityConfiguration.SecurityFilterChain() **************************************************************************");
		http.cors().and().csrf().disable();
		http.authorizeRequests().antMatchers(HttpMethod.POST, "/api/auth/**").permitAll();
		// http.authorizeRequests().antMatchers(HttpMethod.GET, "/**").permitAll();
		http.exceptionHandling().authenticationEntryPoint(unauthorizedHandler);
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		http.authorizeRequests().anyRequest().authenticated();
		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);	

		return http.build();
	}

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring().antMatchers("/js/**", "/images/**");
	}

}
