package br.com.janesroberto.milhas.exception;

public class AirlineNotFoundException extends Exception{
    private static final long serialVersionUID = 2066092252345466345L;

    public AirlineNotFoundException(String message) {
        super(message);
    }
}
