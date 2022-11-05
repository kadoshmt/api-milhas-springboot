package br.com.janesroberto.milhas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.janesroberto.milhas.dto.UserDto;
import br.com.janesroberto.milhas.model.User;
import br.com.janesroberto.milhas.repository.UserRepository;

@Service
public class UserService implements IUserService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = userRepository.findAll();
		List<UserDto> usersDto = new ArrayList<UserDto>();
		users.forEach(user -> {
			usersDto.add(new UserDto(user));
		});
		return usersDto;
	}

	@Override
	public User getUserById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}
	
	@Override
	public User getUserByEmail(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}
	
	@Override
	public Optional<User> getUserByEmailAuth(String email) {
		Optional<User> user = userRepository.findByEmail(email);
		if (user.isPresent()) {
			return user;
		}
		return null;
	}	
	

	@Override
	public Boolean userExistsById(Long id) {		
		return userRepository.existsById(id);
	}

	

}
