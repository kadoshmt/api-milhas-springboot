package br.com.janesroberto.milhas.service;

import java.util.List;

import br.com.janesroberto.milhas.dto.AirlineDto;
import br.com.janesroberto.milhas.dto.AirlineFormDto;
import br.com.janesroberto.milhas.model.Airline;
import br.com.janesroberto.milhas.model.User;

public interface IAirlineService {

	List<AirlineDto> getAllAirlines();
	
	Airline getAirlineById(Long id);
	
	AirlineDto addAirline(AirlineFormDto airline, User user);
	
	AirlineDto updateAirline(AirlineFormDto airline, Long id, User user );
	
	Boolean deleteAirline(Long id, User user);
	
	Boolean airlineExistsById(Long id);

}
