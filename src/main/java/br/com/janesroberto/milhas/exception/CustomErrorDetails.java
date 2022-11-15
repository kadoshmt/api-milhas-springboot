package br.com.janesroberto.milhas.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;

//Simple custom error details bean
@Getter
@AllArgsConstructor
public class CustomErrorDetails {

    private Date timestamp;
    private String message;
    private String errordetails;
//
//    public CustomErrorDetails(Date timestamp, String message, String errordetails) {
//        super();
//        this.timestamp = timestamp;
//        this.message = message;
//        this.errordetails = errordetails;
//    }
//
//    public Date getTimestamp() {
//        return timestamp;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public String getErrordetails() {
//        return errordetails;
//    }
}
