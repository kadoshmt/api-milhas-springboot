package br.com.janesroberto.milhas.exception;

public class UnauthorizedAccessException extends Exception{
    private static final long serialVersionUID = 2066092252345464322L;

    public UnauthorizedAccessException(String message) {
        super(message);
    }
}
