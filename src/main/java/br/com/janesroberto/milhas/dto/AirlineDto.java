package br.com.janesroberto.milhas.dto;

import br.com.janesroberto.milhas.model.Airline;


public class AirlineDto {
	
	private Long id;
	private String company;	
	
	
	public AirlineDto(Long id, String company) {		
		this.id = id;
		this.company = company;
	}	
	
	public AirlineDto(Airline airline) {
		this.id = airline.getId();
		this.company = airline.getCompany();
	}

	public Long getId() {
		return id;
	}
	
	public String getCompany() {
		return company;
	}

}
