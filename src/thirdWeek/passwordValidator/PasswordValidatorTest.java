package thirdWeek.passwordValidator;

public class PasswordValidatorTest {
    public static void main(String[] args) {
        // test_1
        String login = "Nikita12_B";
        String password = "1belka_letega1";
        String confirmPassword = "1belka_letega1";
        System.out.println(PasswordValidator.checkLoginAndPassword(
                login.toCharArray(),
                password.toCharArray(),
                confirmPassword.toCharArray())); // true

        // test_2
        login = "Никита12_B";
        password = "1belka_letega1";
        confirmPassword = "1belka_letega1";
        System.out.println(PasswordValidator.checkLoginAndPassword(
                login.toCharArray(),
                password.toCharArray(),
                confirmPassword.toCharArray())); // false: Логин содержит недопустимые символы

        // test_3
        login = "Nikita12_BNikita12BNikita12";
        password = "1belka_letega1";
        confirmPassword = "1belka_letega1";
        System.out.println(PasswordValidator.checkLoginAndPassword(
                login.toCharArray(),
                password.toCharArray(),
                confirmPassword.toCharArray())); // false: Логин слишком длинный
    }
}
