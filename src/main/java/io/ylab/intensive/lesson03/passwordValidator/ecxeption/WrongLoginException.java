package io.ylab.intensive.lesson03.passwordValidator.ecxeption;

public class WrongLoginException extends Exception {
    public WrongLoginException() {
    }

    public WrongLoginException(String message) {
        super(message);
    }
}
