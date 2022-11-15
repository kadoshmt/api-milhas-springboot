package br.com.janesroberto.milhas.dto;

import br.com.janesroberto.milhas.model.Airline;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AirlineDto {
	
	private Long id;
	private String company;		
	
	
	public AirlineDto(Airline airline) {
		this.id = airline.getId();
		this.company = airline.getCompany();
	}
	
//	public AirlineDto(Long id, String company) {		
//		this.id = id;
//		this.company = company;
//	}	

//	public Long getId() {
//		return id;
//	}
//	
//	public String getCompany() {
//		return company;
//	}

}
