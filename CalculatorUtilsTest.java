package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CalculatorUtilsTest {

    // 1. Факториал

    @Test
    void testAdd() {
        assertEquals(-5, CalculatorUtils.add(-2, -3));
    }

    @Test
    void testSubtract() {
        assertEquals(2, CalculatorUtils.subtract(5, 3));
    }

    @Test
    void testMultiply() {
        assertEquals(-6, CalculatorUtils.multiply(-2, 3));
    }

    @Test
    void testDivide() {
        assertEquals(-2, CalculatorUtils.divide(6, -3));
    }


    @Test
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> CalculatorUtils.factorial(-1));
    }

    @Test
    void testDivideByZero() {
        assertThrows(ArithmeticException.class,
                () -> CalculatorUtils.divide(5, 0));
    }

    // 2. Площадь треугольника

    @Test
    void testTriangleArea() {
        assertEquals(10.0,
                CalculatorUtils.triangleArea(4, 5));
    }

    // 3. Арифметические операции

    @Test
    void testAddition() {
        assertEquals(7, CalculatorUtils.add(3, 4));
    }

    @Test
    void testSubtraction() {
        assertEquals(1, CalculatorUtils.subtract(5, 4));
    }

    @Test
    void testMultiplication() {
        assertEquals(20, CalculatorUtils.multiply(4, 5));
    }

    @Test
    void testDivision() {
        assertEquals(2, CalculatorUtils.divide(6, 3));
    }

    @Test
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class,
                () -> CalculatorUtils.divide(5, 0));
    }

    // 4. Сравнение чисел

    @Test
    void testCompare() {
        assertEquals(0, CalculatorUtils.compare(5, 5));
        assertTrue(CalculatorUtils.compare(10, 5) > 0);
        assertTrue(CalculatorUtils.compare(3, 7) < 0);
    }
}
