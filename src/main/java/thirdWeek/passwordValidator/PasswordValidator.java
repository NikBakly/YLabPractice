package thirdWeek.passwordValidator;

import thirdWeek.passwordValidator.ecxeption.WrongLoginException;
import thirdWeek.passwordValidator.ecxeption.WrongPasswordException;

import java.util.Arrays;

public class PasswordValidator {
    private PasswordValidator() {
    }

    static public boolean checkLoginAndPassword(char[] login, char[] password, char[] confirmPassword) {
        boolean result = true;
        try {
            loginValidation(login);
            passwordValidation(password, confirmPassword);
        } catch (WrongLoginException | WrongPasswordException e) {
            System.out.println(e.getMessage());
            result = false;
        }
        return result;
    }

    private static void loginValidation(char[] login) throws WrongLoginException {
        if (LoginOrPasswordIsNotValid(login)) {
            throw new WrongLoginException("Логин содержит недопустимые символы");
        }
        if (login.length >= 20) {
            throw new WrongLoginException("Логин слишком длинный");
        }
    }

    private static void passwordValidation(char[] password, char[] confirmPassword) throws WrongPasswordException {
        if (LoginOrPasswordIsNotValid(password)) {
            throw new WrongPasswordException("Пароль содержит недопустимые символы");
        }
        if (password.length >= 20) {
            throw new WrongPasswordException("Пароль слишком длинный");
        }
        if (!Arrays.equals(password, confirmPassword)) {
            throw new WrongPasswordException("Пароль и подтверждение не совпадают");
        }
    }

    private static boolean LoginOrPasswordIsNotValid(char[] values) {
        boolean result = false;
        int numberOfUnderscores = 0;
        for (char value : values) {
            if (value == '_') {
                ++numberOfUnderscores;
                if (numberOfUnderscores > 1) {
                    result = true;
                    break;
                }
            }
            if (value != '_'
                    && !(value >= '0' && value <= '9')
                    && !(value >= 'a' && value <= 'z')
                    && !(value >= 'A' && value <= 'Z')) {
                result = true;
                break;
            }
        }
        return result;
    }
}
