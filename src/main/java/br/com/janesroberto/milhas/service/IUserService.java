package br.com.janesroberto.milhas.service;

import java.util.List;
import java.util.Optional;

import br.com.janesroberto.milhas.dto.UserDto;
import br.com.janesroberto.milhas.exception.UserNotFoundException;
import br.com.janesroberto.milhas.model.User;

public interface IUserService {

	List<UserDto> getAllUsers();
	
	User getUserById(Long id) throws UserNotFoundException;
	
	User getUserByEmail(String email) throws UserNotFoundException;
	
	Optional<User> getUserByEmailAuth(String email) throws UserNotFoundException;	
	
	Boolean userExistsById(Long id);
	
//	UserDto getUserByEmail(String email);
//	
//	UserDto addUser(User user);
//	
//	UserDto updateUser(UserFormDto user, Long id);
//	
//	Boolean deleteUser(Long id);
//	
//	User Login(String email, String senha);
}
