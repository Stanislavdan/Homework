package org.example;

public class CalculatorUtils {

    // 1. Факториал
    public static void factorial(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Число не может быть отрицательным");
        }
        long result = 1;
        for (int i = 1; i <= n; i++) {
            result *= i;
        }
    }

    // 2. Площадь треугольника (по основанию и высоте)
    public static double triangleArea(double base, double height) {
        if (base <= 0 || height <= 0) {
            throw new IllegalArgumentException("Стороны должны быть больше 0");
        }
        return 0.5 * base * height;
    }

    // 3. Арифметические операции
    public static int add(int a, int b) {
        return a + b;
    }

    public static int subtract(int a, int b) {
        return a - b;
    }

    public static int multiply(int a, int b) {
        return a * b;
    }

    public static int divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Деление на 0");
        }
        return a / b;
    }

    // 4. Сравнение чисел
    public static int compare(int a, int b) {
        return Integer.compare(a, b);
    }
}
