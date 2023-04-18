package io.ylab.intensive.lesson03.passwordValidator.ecxeption;

public class WrongPasswordException extends Exception {
    public WrongPasswordException() {
    }

    public WrongPasswordException(String message) {
        super(message);
    }
}
