package br.com.janesroberto.milhas.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.janesroberto.milhas.model.Airline;
import br.com.janesroberto.milhas.model.Point;
import br.com.janesroberto.milhas.model.User;


public class PointFormDto {

	
	
	//private LocalDateTime createdDate;

	@NotBlank
	@Length(min = 6, max = 150)
	private String description;

	@Length(min = 6, max = 40)
	private String origin;

	@Min(10)
	@Max(1000000)
	private Integer estimatedPoints;

	@Min(10)
	@Max(1000000)
	private Integer pointsEarned;

	private BigDecimal price = new BigDecimal(0);

	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date deadlineDate;

	private Boolean isReceived = false;

	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date receivedDate;

	@Length(max = 2000)
	private String observation;
	
	@NotNull
	private Long airlineId;

	private AirlineDto airline;
	
	
	public PointFormDto() {
		
	}

	public PointFormDto(Long id, LocalDateTime createdDate, String description, String origin, Integer estimatedPoints,
			Integer pointsEarned, BigDecimal price, Date deadlineDate, Boolean isReceived,
			Date receivedDate, String observation, Long airlineId) {		
		//this.createdDate = createdDate;
		this.description = description;
		this.origin = origin;
		this.estimatedPoints = estimatedPoints;
		this.pointsEarned = pointsEarned;
		this.price = price;
		this.deadlineDate = deadlineDate;
		this.isReceived = isReceived;
		this.receivedDate = receivedDate;
		this.observation = observation;
		this.airlineId = airlineId;
	}
	
	public PointFormDto(Point point) {		
		//this.createdDate = point.getCreatedDate();
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
		this.airlineId = point.getAirline().getId();
	}

	
//	public LocalDateTime getCreatedDate() {
//		return createdDate;
//	}
//
//	public void setCreatedDate(LocalDateTime createdDate) {
//		this.createdDate = createdDate;
//	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public Integer getEstimatedPoints() {
		return estimatedPoints;
	}

	public void setEstimatedPoints(Integer estimatedPoints) {
		this.estimatedPoints = estimatedPoints;
	}

	public Integer getPointsEarned() {
		return pointsEarned;
	}

	public void setPointsEarned(Integer pointsEarned) {
		this.pointsEarned = pointsEarned;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Date getDeadlineDate() {
		return deadlineDate;
	}

	public void setDeadlineDate(Date deadlineDate) {
		this.deadlineDate = deadlineDate;
	}

	public Boolean getIsReceived() {
		return isReceived;
	}

	public void setIsReceived(Boolean isReceived) {
		this.isReceived = isReceived;
	}

	public Date getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(Date receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public AirlineDto getAirline() {
		return airline;
	}

	public void setAirline(AirlineDto airline) {
		this.airline = airline;
	}
	
	
	
	public Long getAirlineId() {
		return airlineId;
	}

	public void setAirlineId(Long airlineId) {
		this.airlineId = airlineId;
	}

	public Point prepareToCreate(User user, Airline airline) {
		return new Point(description, origin, estimatedPoints, price, deadlineDate, observation, airline, user);
	}
	
	public Point prepareToUpdate(Point point){		
		point.setDeadlineDate(deadlineDate);
		point.setDescription(description);
		point.setEstimatedPoints(estimatedPoints);
		point.setObservation(observation);
		point.setOrigin(origin);
		point.setPrice(price);		
		return point;		
	}

}
