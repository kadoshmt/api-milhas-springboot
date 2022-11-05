package br.com.janesroberto.milhas.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.janesroberto.milhas.model.Airline;

public interface AirlineRepository extends JpaRepository<Airline, Long>{

}
