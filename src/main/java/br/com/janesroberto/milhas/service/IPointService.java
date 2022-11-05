package br.com.janesroberto.milhas.service;

import java.util.List;

import br.com.janesroberto.milhas.dto.PointConfirmFormDto;
import br.com.janesroberto.milhas.dto.PointDto;
import br.com.janesroberto.milhas.dto.PointFormDto;
import br.com.janesroberto.milhas.model.Airline;
import br.com.janesroberto.milhas.model.Point;
import br.com.janesroberto.milhas.model.User;

public interface IPointService {

	List<PointDto> getAllPointsByUser(User user);

	List<PointDto> getPointsByCompanyIdAndUser(Long id, User user);

	Point getPointsById(Long id);

	PointDto addPoint(PointFormDto form, User user, Airline airline);

	PointDto updatePoint(PointFormDto form, Long id, User user);

	PointDto confirmPoint(PointConfirmFormDto form, Long id, User user);

	Boolean deletePoint(Long id, User user);

}
