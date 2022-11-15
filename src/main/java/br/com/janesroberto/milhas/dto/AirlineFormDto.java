package br.com.janesroberto.milhas.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

import br.com.janesroberto.milhas.model.Airline;
import br.com.janesroberto.milhas.model.User;
import br.com.janesroberto.milhas.repository.AirlineRepository;
import lombok.Data;

@Data
public class AirlineFormDto {

	@NotNull
	@NotEmpty
	@Length(min = 3)
	private String company;
	


	public Airline prepareToCreate(User user) {
		
		return new Airline(company, user);
	}
	
	
	public Airline prepareToUpdate(Long id, AirlineRepository airlineRepository, User user){		
		Airline airline = airlineRepository.findById(id).get();
		airline.setCompany(company);
		return airline;		
	}

//	public String getCompany() {
//		return company;
//	}
//	
//	public void setCompany(String company) {
//		this.company = company;
//	}
}
