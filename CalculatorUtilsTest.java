package org.example;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CalculatorUtilsTest {

    // 1. Факториал и арифметика

    @Test
    public void testAdd() {
        assertEquals(CalculatorUtils.add(-2, -3), -5);
    }

    @Test
    public void testSubtract() {
        assertEquals(CalculatorUtils.subtract(5, 3), 2);
    }

    @Test
    public void testMultiply() {
        assertEquals(CalculatorUtils.multiply(-2, 3), -6);
    }

    @Test
    public void testDivide() {
        assertEquals(CalculatorUtils.divide(6, -3), -2);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFactorialNegative() {
        CalculatorUtils.factorial(-1);
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void testDivideByZero() {
        CalculatorUtils.divide(5, 0);
    }

    // 2. Площадь треугольника

    @Test
    public void testTriangleArea() {
        assertEquals(CalculatorUtils.triangleArea(4, 5), 10.0);
    }

    // 3. Арифметические операции

    @Test
    public void testAddition() {
        assertEquals(CalculatorUtils.add(3, 4), 7);
    }

    @Test
    public void testSubtraction() {
        assertEquals(CalculatorUtils.subtract(5, 4), 1);
    }

    @Test
    public void testMultiplication() {
        assertEquals(CalculatorUtils.multiply(4, 5), 20);
    }

    @Test
    public void testDivision() {
        assertEquals(CalculatorUtils.divide(6, 3), 2);
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void testDivisionByZero() {
        CalculatorUtils.divide(5, 0);
    }

    // 4. Сравнение чисел

    @Test
    public void testCompare() {
        assertEquals(CalculatorUtils.compare(5, 5), 0);
        assertTrue(CalculatorUtils.compare(10, 5) > 0);
        assertTrue(CalculatorUtils.compare(3, 7) < 0);
    }
}
