package br.com.janesroberto.milhas.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.janesroberto.milhas.model.Point;

public interface PointRepository extends JpaRepository<Point, Long>{

	List<Point> findByUserId(Long id);
}
