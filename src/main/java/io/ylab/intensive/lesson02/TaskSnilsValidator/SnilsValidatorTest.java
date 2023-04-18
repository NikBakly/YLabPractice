package io.ylab.intensive.lesson02.TaskSnilsValidator;

public class SnilsValidatorTest {
    public static void main(String[] args) {
        SnilsValidator snilsValidator = new SnilsValidatorImpl();
        System.out.println(snilsValidator.validate("48095351208")); // true
        System.out.println(snilsValidator.validate("480-953-512-08")); // true
        System.out.println(snilsValidator.validate("480-953-512 08")); // true
        System.out.println(snilsValidator.validate("48895333208")); // false
    }
}
