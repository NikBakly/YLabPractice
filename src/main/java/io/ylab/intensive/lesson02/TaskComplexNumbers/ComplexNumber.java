package io.ylab.intensive.lesson02.TaskComplexNumbers;

public class ComplexNumber {
    // действительная часть
    private final double re;

    // мнимая часть
    private final double im;

    public ComplexNumber(double re) {
        this.re = re;
        this.im = 0;
    }

    public ComplexNumber(double re, double im) {
        this.re = re;
        this.im = im;
    }

    public ComplexNumber plus(ComplexNumber secondNumber) {
        return new ComplexNumber(
                this.re + secondNumber.re,
                this.im + secondNumber.im
        );
    }

    public ComplexNumber minus(ComplexNumber secondNumber) {
        return new ComplexNumber(
                this.re - secondNumber.re,
                this.im - secondNumber.im
        );
    }

    public ComplexNumber multiplication(ComplexNumber secondNumber) {
        // Пояснение. Пусть имеются два комплексных числа: z1 = a1 + i * b1, z2 = a2 + i * b2;
        // Тогда z1 * z2 = (a1 + i * b1) * (a2 + i * b2) =>
        // => [раскрываем скобки и сгруппируем по действительной и мнимой части] => (a1 * a2 - b1 * b2) + i * (a1 * b2 + b1 * a2);
        // Таким образом у нас получилась общая формула нахождения произведений для комплексных чисел.
        return new ComplexNumber(
                (this.re * secondNumber.re - this.im * secondNumber.im),
                (this.re * secondNumber.im + this.im * secondNumber.re)
        );
    }

    public Double getModule() {
        return Math.sqrt(this.re * this.re + this.im * this.im);
    }

    @Override
    public String toString() {
        String result;
        if (im < 0) {
            result = String.format("z = %s - i * %s", re, -im);
        } else if (im > 0) {
            result = String.format("z = %s + i * %s", re, im);
        } else {
            result = String.format("z = %s", re);
        }
        return result;
    }
}
