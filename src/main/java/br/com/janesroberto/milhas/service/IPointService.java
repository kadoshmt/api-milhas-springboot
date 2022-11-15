package br.com.janesroberto.milhas.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.janesroberto.milhas.dto.PointConfirmFormDto;
import br.com.janesroberto.milhas.dto.PointDto;
import br.com.janesroberto.milhas.dto.PointFormDto;
import br.com.janesroberto.milhas.exception.AirlineNotFoundException;
import br.com.janesroberto.milhas.exception.PointNotFoundException;
import br.com.janesroberto.milhas.model.Airline;
import br.com.janesroberto.milhas.model.Point;
import br.com.janesroberto.milhas.model.User;

public interface IPointService {

	//List<PointDto> getAllPointsByUser(User user);

	Page<PointDto> listAllPoints(Long id, User user, Pageable pageable);

	Point getPointsById(Long id) throws PointNotFoundException;

	PointDto addPoint(PointFormDto form, User user, Airline airline);

	PointDto updatePoint(PointFormDto form, Long id, User user) throws PointNotFoundException, AirlineNotFoundException;

	PointDto confirmPoint(PointConfirmFormDto form, Long id, User user) throws PointNotFoundException;

	Boolean deletePoint(Long id, User user) throws PointNotFoundException;

}
