package br.com.janesroberto.milhas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.janesroberto.milhas.dto.AirlineDto;
import br.com.janesroberto.milhas.dto.AirlineFormDto;
import br.com.janesroberto.milhas.model.Airline;
import br.com.janesroberto.milhas.model.User;
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
	public Airline getAirlineById(Long id) {
		Optional<Airline> airline = airlineRepository.findById(id);
		if (airline.isPresent()) {
			return airline.get();
		}
		return null;
	}

	@Override
	public AirlineDto addAirline(AirlineFormDto form, User user) {		
		Airline airline = form.prepareToCreate(user);
		airlineRepository.save(airline);
		return new AirlineDto(airline);		
	}

	@Override
	public AirlineDto updateAirline(AirlineFormDto form, Long id, User user) {
		

		Airline airlineFounded = this.getAirlineById(id);
		if (airlineFounded != null) {
			Airline airline = form.prepareToUpdate(id, airlineRepository, user);
			return new AirlineDto(airline);
		}
		return null;
	}

	@Override
	public Boolean deleteAirline(Long id, User user) {

		Airline airlineFounded = this.getAirlineById(id);
		if (airlineFounded != null) {
			airlineRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Boolean airlineExistsById(Long id) {		
		return airlineRepository.existsById(id);
	}
	
	

	
}
