package br.com.janesroberto.milhas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.janesroberto.milhas.dto.AirlineDto;
import br.com.janesroberto.milhas.exception.AirlineNotFoundException;
import br.com.janesroberto.milhas.model.Airline;
import br.com.janesroberto.milhas.repository.AirlineRepository;

@Service
public class AirlineService implements IAirlineService{	
	
	
	@Autowired
	private AirlineRepository airlineRepository;

	@Override
	public List<AirlineDto> getAllAirlines() {
		List<Airline> airlines = airlineRepository.findAll();
		List<AirlineDto> airlinesDto = new ArrayList<AirlineDto>();
		airlines.forEach(airline -> {
			airlinesDto.add(new AirlineDto(airline));
		});
		return airlinesDto;
	}

	@Override
	public Airline getAirlineById(Long id) throws AirlineNotFoundException {
		Optional<Airline> airline = airlineRepository.findById(id);
		if (!airline.isPresent()) {
			throw new AirlineNotFoundException("Airline company with id " + id + " not found.");
		}
		return airline.get();
	}

	
}
