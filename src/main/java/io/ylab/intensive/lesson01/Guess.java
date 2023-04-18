package io.ylab.intensive.lesson01;

import java.util.Random;
import java.util.Scanner;

public class Guess {
    public static void main(String[] args) {
        int number = new Random().nextInt(100); // здесь загадывается число от 1 до 99
        int maxAttempts = 10; // здесь задается количество попыток
        System.out.println("Я загадал число. У тебя " + maxAttempts + " попыток угадать.");
        try (Scanner scanner = new Scanner(System.in)) {
            int numberOfAttempts = 1;
            int userNumber = scanner.nextInt();
            while (userNumber != number) {
                --maxAttempts;
                if (maxAttempts == 0) {
                    System.out.println("Ты не угадал");
                    return;
                }
                if (userNumber > number) {
                    System.out.printf("Мое число меньше! У тебя осталось %d попыток\n", maxAttempts);
                } else {
                    System.out.printf("Мое число больше! У тебя осталось %d попыток\n", maxAttempts);
                }

                userNumber = scanner.nextInt();
                ++numberOfAttempts;
            }

            System.out.printf("Ты угадал с %d попытки", numberOfAttempts);
        }
    }
}
