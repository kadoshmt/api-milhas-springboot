package br.com.janesroberto.milhas.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.janesroberto.milhas.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);
	
	Optional<User> findById(Long id);

	Boolean existsByEmail(String email);

}
