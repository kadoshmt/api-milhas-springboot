package br.com.janesroberto.milhas.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Point {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	@CreationTimestamp
    protected LocalDateTime createdDate;
	
	private String description;
	
	private String origin;
	
	private Integer estimatedPoints;
	
	@Nullable
	private Integer  pointsEarned;
	
	private BigDecimal price = new BigDecimal(0);
	
	@Nullable
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date deadlineDate;
	
	private Boolean isReceived = false;	
	
	@Nullable
	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date receivedDate;
	
	@Column(length = 2000)
	private String observation;
	
	@ManyToOne
	private Airline airline;
	
	@ManyToOne
	private User user;
	

	public Point(String description, String origin, Integer estimatedPoints, BigDecimal price,
			Date deadlineDate, String observation, Airline airline, User user) {		
		this.description = description;
		this.origin = origin;
		this.estimatedPoints = estimatedPoints;
		this.price = price;
		this.deadlineDate = deadlineDate;
		this.observation = observation;
		this.airline = airline;
		this.user = user;
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
//	public Integer getPointsEarned() {
//		return pointsEarned;
//	}
//
//	public void setPointsEarned(Integer pointsEarned) {
//		this.pointsEarned = pointsEarned;
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
//	public Airline getAirline() {
//		return airline;
//	}
//
//	public void setAirline(Airline airline) {
//		this.airline = airline;
//	}
//
//	public User getUser() {
//		return user;
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}

}
