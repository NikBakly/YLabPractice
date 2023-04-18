package io.ylab.intensive.lesson02.TaskSequences;

public class SequenceGeneratorImpl implements SequenceGenerator {
    @Override
    public void a(int n) {
        checkN(n);
        for (int i = 1; i <= n; i++) {
            System.out.printf("A_%d = %d \t", i, (2 * i));
        }
        split();
    }

    @Override
    public void b(int n) {
        checkN(n);
        for (int i = 0; i < n; i++) {
            System.out.printf("B_%d = %d \t", (i + 1), (1 + 2 * i));
        }
        split();
    }

    @Override
    public void c(int n) {
        checkN(n);
        for (int i = 1; i <= n; i++) {
            System.out.printf("C_%d = %d \t", i, (i * i));
        }
        split();
    }

    @Override
    public void d(int n) {
        checkN(n);
        for (int i = 1; i <= n; i++) {
            System.out.printf("D_%d = %d \t", i, (i * i * i));
        }
        split();
    }

    @Override
    public void e(int n) {
        checkN(n);
        for (int i = 1; i <= n; i++) {
            System.out.printf("E_%d = %d \t", i, ((int) Math.pow(-1, i + 1)));
        }
        split();
    }

    @Override
    public void f(int n) {
        checkN(n);
        for (int i = 1; i <= n; i++) {
            System.out.printf("F_%d = %d \t", i, ((int) Math.pow(-1, i + 1) * i));
        }
        split();
    }

    @Override
    public void g(int n) {
        checkN(n);
        for (int i = 1; i <= n; i++) {
            System.out.printf("G_%d = %d \t", i, ((int) Math.pow(-1, i + 1) * i * i));
        }
        split();
    }

    @Override
    public void h(int n) {
        checkN(n);
        int j = 1;
        for (int i = 1; i <= n; i++) {
            int result = 0;
            if (i % 2 != 0) {
                result = j;
                ++j;
            }
            System.out.printf("H_%d = %d \t", i, result);
        }
        split();
    }

    @Override
    public void i(int n) {
        checkN(n);
        int firstName = 1;
        int secondNumber = 2;
        int nextNumber;
        for (int i = 1; i <= n; i++) {
            if (i <= 2) {
                System.out.printf("I_%d = %d \t", i, i);
            } else {
                nextNumber = (firstName + secondNumber) * (i - 1);
                System.out.printf("I_%d = %d \t", i, nextNumber);
                firstName = secondNumber;
                secondNumber = nextNumber;
            }
        }
        split();
    }

    @Override
    public void j(int n) {
        checkN(n);
        int firstName = 1;
        int secondNumber = 1;
        int nextNumber;
        for (int i = 1; i <= n; i++) {
            if (i <= 2) {
                System.out.printf("J_%d = %d \t", i, 1);
            } else {
                nextNumber = firstName + secondNumber;
                System.out.printf("J_%d = %d \t", i, nextNumber);
                firstName = secondNumber;
                secondNumber = nextNumber;
            }
        }
        split();

    }

    private void checkN(int n) {
        if (n <= 0) {
            throw new RuntimeException("Номер последовательности n должен быть строго положительным");
        }
    }

    private void split() {
        int separatorSize = 46;

        System.out.println();
        for (int i = 0; i < separatorSize; i++) {
            System.out.print("--");
        }
        System.out.println();
    }
}
