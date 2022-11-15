package br.com.janesroberto.milhas.service;

import java.util.List;

import br.com.janesroberto.milhas.dto.AirlineDto;
import br.com.janesroberto.milhas.exception.AirlineNotFoundException;
import br.com.janesroberto.milhas.model.Airline;

public interface IAirlineService {

	List<AirlineDto> getAllAirlines();
	
	Airline getAirlineById(Long id) throws AirlineNotFoundException;

}
