package br.com.janesroberto.milhas.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FormErrorDto {
	
	private String field;
	private String error;

}
