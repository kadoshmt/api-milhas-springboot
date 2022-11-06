package br.com.janesroberto.milhas.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.janesroberto.milhas.model.Point;

public interface PointRepository extends JpaRepository<Point, Long>{

	Page<Point> findByUserId(Long id, Pageable paginacao);
	Page<Point> findByUserIdAndAirlineId(Long userId, Long airlineId, Pageable paginacao);
	
}
