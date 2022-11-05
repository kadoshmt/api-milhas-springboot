package br.com.janesroberto.milhas.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.janesroberto.milhas.dto.PointConfirmFormDto;
import br.com.janesroberto.milhas.dto.PointDto;
import br.com.janesroberto.milhas.dto.PointFormDto;
import br.com.janesroberto.milhas.model.Airline;
import br.com.janesroberto.milhas.model.Point;
import br.com.janesroberto.milhas.model.User;
import br.com.janesroberto.milhas.repository.AirlineRepository;
import br.com.janesroberto.milhas.repository.PointRepository;
import br.com.janesroberto.milhas.repository.UserRepository;

@Service
public class PointService implements IPointService {

	@Autowired
	private PointRepository pointRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AirlineRepository airlineRepository;

	
	@Override
	public List<PointDto> getAllPointsByUser(User user) {
		List<Point> points = pointRepository.findByUserId(user.getId());
		List<PointDto> pointsDto = new ArrayList<PointDto>();
		points.forEach(point -> {
			pointsDto.add(new PointDto(point));
		});
		return pointsDto;
	}

	@Override
	public List<PointDto> getPointsByCompanyIdAndUser(Long id, User user) {
//		List<Point> points = pointRepository.findByUserId(user.getId());
//		List<PointDto> pointsDto = new ArrayList<PointDto>();
//		points.forEach(point -> {
//			pointsDto.add(new PointDto(point));
//		});
//		return pointsDto;
		return null;
	}

	@Override
	public Point getPointsById(Long id) {
		Optional<Point> points = pointRepository.findById(id);
		if (points.isPresent()) {
			return points.get();
		}
		return null;
	}

	@Override
	public PointDto addPoint(PointFormDto form, User user, Airline airline) {
		Point point = form.prepareToCreate(user, airline);
		pointRepository.save(point);
		return new PointDto(point);
	}

	@Override
	public PointDto updatePoint(PointFormDto form, Long id, User user) {
		Point pointFounded = this.getPointsById(id);
		// verify if exists an enty and if this enty was added by the logged user
		if (pointFounded != null && pointFounded.getUser().getId() == user.getId()) {
			Point point = form.prepareToUpdate(pointFounded);
			// verify if the user change the company, if he did, change the airline company
			if (form.getAirlineId() != point.getAirline().getId()) {
				Optional<Airline> airline = airlineRepository.findById(form.getAirlineId());
				if (airline.isPresent()) {
					point.setAirline(airline.get());
				}
			}
			return new PointDto(point);
		}
		return null;
	}
	
	@Override
	public PointDto confirmPoint(PointConfirmFormDto form, Long id, User user) {
		Point pointFounded = this.getPointsById(id);
		// verify if exists an enty and if this enty was added by the logged user
		if (pointFounded != null && pointFounded.getUser().getId() == user.getId()) {
			Point point = form.prepareToUpdate(pointFounded);			
			return new PointDto(point);
		}
		return null;
	}


	@Override
	public Boolean deletePoint(Long id, User user) {
		Boolean userExists = userRepository.existsById(user.getId());

		if (!userExists) {
			return false;
		}

		// verify if exists an enty and if this enty was added by the logged user
		Point pointFounded = this.getPointsById(id);
		if (pointFounded != null && pointFounded.getUser().getId() == user.getId()) {
			pointRepository.deleteById(id);
			return true;
		}
		return false;
	}

}
