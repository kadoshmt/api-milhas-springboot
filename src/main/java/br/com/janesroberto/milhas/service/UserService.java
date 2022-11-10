package br.com.janesroberto.milhas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.janesroberto.milhas.dto.UserDto;
import br.com.janesroberto.milhas.exception.UserNotFoundException;
import br.com.janesroberto.milhas.model.User;
import br.com.janesroberto.milhas.repository.UserRepository;

@Service
public class UserService implements IUserService {

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
	public User getUserById(Long id) throws UserNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if (!user.isPresent()) {
			throw new UserNotFoundException("User Not found in user Repository, provide the correct user id");
		}
		return user.get();
	}

	@Override
	public User getUserByEmail(String email) throws UserNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);
		if (!user.isPresent()) {
			throw new UserNotFoundException("User Not found in user Repository, provide the correct user email");
		}
		return user.get();
	}

	@Override
	public Optional<User> getUserByEmailAuth(String email) throws UserNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);
		if (!user.isPresent()) {
			throw new UserNotFoundException("User Not found in user Repository, provide the correct user email");
		}
		return user;
	}

	@Override
	public Boolean userExistsById(Long id) {
		return userRepository.existsById(id);
	}

}
