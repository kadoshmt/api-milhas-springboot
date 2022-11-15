package br.com.janesroberto.milhas.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.janesroberto.milhas.dto.PointConfirmFormDto;
import br.com.janesroberto.milhas.dto.PointDto;
import br.com.janesroberto.milhas.dto.PointFormDto;
import br.com.janesroberto.milhas.exception.AirlineNotFoundException;
import br.com.janesroberto.milhas.exception.PointNotFoundException;
import br.com.janesroberto.milhas.exception.UnauthorizedAccessException;
import br.com.janesroberto.milhas.model.Airline;
import br.com.janesroberto.milhas.model.Point;
import br.com.janesroberto.milhas.model.User;
import br.com.janesroberto.milhas.repository.AirlineRepository;
import br.com.janesroberto.milhas.repository.PointRepository;

@Service
public class PointService implements IPointService {

	@Autowired
	private PointRepository pointRepository;

	@Autowired
	private AirlineRepository airlineRepository;

//	@Override
//	public List<PointDto> getAllPointsByUser(User user) {
//		List<Point> points = pointRepository.findByUserId(user.getId());
//		List<PointDto> pointsDto = new ArrayList<PointDto>();
//		points.forEach(point -> {
//			pointsDto.add(new PointDto(point));
//		});
//		return pointsDto;
//	}

	@Override
	public Page<PointDto> listAllPoints(Long AirlineId, User user, Pageable pageable) {
		Page<Point> points;
		if (AirlineId != null) {
			points = pointRepository.findByUserIdAndAirlineId(user.getId(), AirlineId, pageable);
		} else {
			points = pointRepository.findByUserId(user.getId(), pageable);
		}

		return PointDto.convert(points);
//		List<PointDto> pointsDto = new ArrayList<PointDto>();
//		points.forEach(point -> {
//			pointsDto.add(new PointDto(point));
//		});
//		return pointsDto;
		// return null;
	}

	@Override
	public Point getPointsById(Long id) throws PointNotFoundException {
		Optional<Point> points = pointRepository.findById(id);
		if (!points.isPresent()) {
			throw new PointNotFoundException("Point with id " + id + " not found.");
		}
		return points.get();
	}

	@Override
	@Transactional
	public PointDto addPoint(PointFormDto form, User user, Airline airline) {
		Point point = form.prepareToCreate(user, airline);
		pointRepository.save(point);
		return new PointDto(point);
	}

	@Override
	@Transactional
	public PointDto updatePoint(PointFormDto form, Long id, User user) throws PointNotFoundException, AirlineNotFoundException, UnauthorizedAccessException {
		Point pointFounded = this.getPointsById(id);
		// verify the entry was added by the logged user
		if (pointFounded.getUser().getId() != user.getId()) {
			throw new UnauthorizedAccessException("You are not allowed to update these points..");
		}
		Point point = form.prepareToUpdate(pointFounded);
		// verify if the user changed the company, if he did, change the airline company
		if (form.getAirlineId() != point.getAirline().getId()) {
			Optional<Airline> airline = airlineRepository.findById(form.getAirlineId());
			if (!airline.isPresent()) {
				throw new AirlineNotFoundException("Airline company with id " + id + " not found.");
			}
			point.setAirline(airline.get());
		}
		return new PointDto(point);
	
	}

	@Override
	@Transactional
	public PointDto confirmPoint(PointConfirmFormDto form, Long id, User user) throws PointNotFoundException, UnauthorizedAccessException {
		Point pointFounded = this.getPointsById(id);
		// verify if the entry was added by the logged user
		if (pointFounded.getUser().getId() != user.getId()) {
			throw new UnauthorizedAccessException("You are not allowed to confirm these points.");
		}
		Point point = form.prepareToUpdate(pointFounded);
		return new PointDto(point);
	}

	@Override
	@Transactional
	public Boolean deletePoint(Long id, User user) throws PointNotFoundException, UnauthorizedAccessException {
		Point pointFounded = this.getPointsById(id);
		// verify if the entry was added by the logged user
		if (pointFounded.getUser().getId() != user.getId()) {
			throw new UnauthorizedAccessException("YYou are not allowed to delete these points.");
		}
		pointRepository.deleteById(id);
		return true;
	}
}
