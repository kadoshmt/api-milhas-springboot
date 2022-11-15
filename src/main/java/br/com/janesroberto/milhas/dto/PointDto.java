package br.com.janesroberto.milhas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.data.domain.Page;

import br.com.janesroberto.milhas.model.Point;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PointDto {

	private Long id;

	protected LocalDateTime createdDate;

	private String description;

	private String origin;

	private Integer estimatedPoints;

	private Integer pointsEarned;

	private BigDecimal price;

	private Date deadlineDate;

	private Boolean isReceived;

	private Date receivedDate;

	private String observation;

	private AirlineDto airline;

//	public PointDto(Long id, LocalDateTime createdDate, String description, String origin, Integer estimatedPoints,
//			Integer pointsEarned, BigDecimal price, Date deadlineDate, Boolean isReceived, Date receivedDate,
//			String observation, AirlineDto airline) {
//		this.id = id;
//		this.createdDate = createdDate;
//		this.description = description;
//		this.origin = origin;
//		this.estimatedPoints = estimatedPoints;
//		this.pointsEarned = pointsEarned;
//		this.price = price;
//		this.deadlineDate = deadlineDate;
//		this.isReceived = isReceived;
//		this.receivedDate = receivedDate;
//		this.observation = observation;
//		this.airline = airline;
//	}

	public PointDto(Point point) {
		this.id = point.getId();
		this.createdDate = point.getCreatedDate();
		this.description = point.getDescription();
		this.origin = point.getOrigin();
		this.estimatedPoints = point.getEstimatedPoints();
		this.pointsEarned = point.getPointsEarned();
		this.price = point.getPrice();
		this.deadlineDate = point.getDeadlineDate();
		this.isReceived = point.getIsReceived();
		this.receivedDate = point.getReceivedDate();
		this.observation = point.getObservation();
		this.airline = new AirlineDto(point.getAirline());
	}

	public static Page<PointDto> convert(Page<Point> points) {
		return points.map(PointDto::new);
	}

//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public LocalDateTime getCreatedDate() {
//		return createdDate;
//	}
//
//	public void setCreatedDate(LocalDateTime createdDate) {
//		this.createdDate = createdDate;
//	}
//
//	public String getDescription() {
//		return description;
//	}
//
//	public void setDescription(String description) {
//		this.description = description;
//	}
//
//	public String getOrigin() {
//		return origin;
//	}
//
//	public void setOrigin(String origin) {
//		this.origin = origin;
//	}
//
//	public Integer getEstimatedPoints() {
//		return estimatedPoints;
//	}
//
//	public void setEstimatedPoints(Integer estimatedPoints) {
//		this.estimatedPoints = estimatedPoints;
//	}
//
//	public Integer getPointsEarned() {
//		return pointsEarned;
//	}
//
//	public void setPointsEarned(Integer pointsEarned) {
//		this.pointsEarned = pointsEarned;
//	}
//
//	public BigDecimal getPrice() {
//		return price;
//	}
//
//	public void setPrice(BigDecimal price) {
//		this.price = price;
//	}
//
//	public Date getDeadlineDate() {
//		return deadlineDate;
//	}
//
//	public void setDeadlineDate(Date deadlineDate) {
//		this.deadlineDate = deadlineDate;
//	}
//
//	public Boolean getIsReceived() {
//		return isReceived;
//	}
//
//	public void setIsReceived(Boolean isReceived) {
//		this.isReceived = isReceived;
//	}
//
//	public Date getReceivedDate() {
//		return receivedDate;
//	}
//
//	public void setReceivedDate(Date receivedDate) {
//		this.receivedDate = receivedDate;
//	}
//
//	public String getObservation() {
//		return observation;
//	}
//
//	public void setObservation(String observation) {
//		this.observation = observation;
//	}
//
//	public AirlineDto getAirline() {
//		return airline;
//	}
//
//	public void setAirline(AirlineDto airline) {
//		this.airline = airline;
//	}	

}
