package io.ylab.intensive.lesson02.TaskComplexNumbers;

public class ComplexNumberTest {
    public static void main(String[] args) {
        ComplexNumber z1 = new ComplexNumber(5, 4);
        ComplexNumber z2 = new ComplexNumber(2, 3);
        System.out.println(z1.plus(z2)); // результат: z = 7 + i * 7
        System.out.println(z1.minus(z2)); // результат: z = 3 + i * 1
        System.out.println(z2.minus(z1)); // результат: z = -3 - i * 1
        System.out.println(z1.multiplication(z2)); // результат: z = -2 + i * 23
        System.out.println(z1.getModule()); // результат: 6.403
        System.out.println(z2.getModule()); // результат: 3.606


        ComplexNumber z3 = new ComplexNumber(5);
        System.out.println(z3); // результат: z = 5
        System.out.println(z3.plus(z1)); // результат: z = 10 + i * 4
        System.out.println(z3.getModule()); // результат: 5
    }
}
