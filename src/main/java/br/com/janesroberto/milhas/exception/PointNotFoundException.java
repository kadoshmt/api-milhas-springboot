package br.com.janesroberto.milhas.exception;

public class PointNotFoundException extends Exception{
    private static final long serialVersionUID = 2066092252345466323L;

    public PointNotFoundException(String message) {
        super(message);
    }
}
