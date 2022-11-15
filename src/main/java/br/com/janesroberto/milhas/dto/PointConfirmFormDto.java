package br.com.janesroberto.milhas.dto;

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.janesroberto.milhas.model.Point;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class PointConfirmFormDto {

	@Min(10)
	@Max(1000000)
	private Integer pointsEarned;

	private Boolean isReceived = true;

	@DateTimeFormat(pattern = "yyyy-MM-dd", iso = ISO.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private Date receivedDate;

//	public PointConfirmFormDto() {
//	}
	
	public PointConfirmFormDto(Point point) {
		this.pointsEarned = point.getPointsEarned();
		this.isReceived = point.getIsReceived();
		this.receivedDate = point.getReceivedDate();
	}
	

	public Point prepareToUpdate(Point point) {
		point.setIsReceived(true);
		point.setPointsEarned(pointsEarned);
		point.setReceivedDate(receivedDate);
		return point;
	}

//	public PointConfirmFormDto(Integer pointsEarned, BigDecimal price, Date receivedDate) {
//		this.pointsEarned = pointsEarned;
//		this.receivedDate = receivedDate;
//	}

	
	
//	public Integer getPointsEarned() {
//		return pointsEarned;
//	}
//
//	public void setPointsEarned(Integer pointsEarned) {
//		this.pointsEarned = pointsEarned;
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
}
