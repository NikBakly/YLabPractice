package firstWeek;

import java.util.Scanner;

public class Pell {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            int n = scanner.nextInt();
            System.out.println(getPellNumber(n));
            System.out.println(getPellNumberWithWhile(n));
        }
    }

    private static long getPellNumber(int n) {
        if (n <= 1) {
            return n;
        }
        return 2 * getPellNumber(n - 1) + getPellNumber(n - 2);
    }

    private static long getPellNumberWithWhile(int n) {
        if (n <= 1) {
            return n;
        }
        long firstNumber = 0;
        long secondNumber = 1;
        long result = 2;

        for (int i = 2; i <= n; i++) {
            result = 2 * secondNumber + firstNumber;
            firstNumber = secondNumber;
            secondNumber = result;
        }
        return result;
    }

}
