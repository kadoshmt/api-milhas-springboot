package br.com.janesroberto.milhas.exception;

public class UserExistsException extends Exception {
    private static final long serialVersionUID = 2765321618538770770L;

    public UserExistsException(String message) {
        super(message);
    }
}
