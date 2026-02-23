package org.example;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class CalculatorUtilsTest {

    // 1. Факториал и арифметика

    // Переделал тесты

    @Test
    public void testFactorialZero() {
        assertEquals(CalculatorUtils.factorial(0), 1L);
    }

    @Test
    public void testFactorialOne() {
        assertEquals(CalculatorUtils.factorial(1), 1L);
    }

    @Test
    public void testFactorialTwo() {
        assertEquals(CalculatorUtils.factorial(2), 2L);
    }

    @Test
    public void testFactorialFive() {
        assertEquals(CalculatorUtils.factorial(5), 120L);
    }

    @Test
    public void testFactorialLargeNumber() {
        assertEquals(CalculatorUtils.factorial(10), 3628800L);
    }

    @Test
    public void testFactorialMaxLong() {
        assertEquals(CalculatorUtils.factorial(20), 2432902008176640000L);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testFactorialNegative() {
        CalculatorUtils.factorial(-1);
    }

    // 2. Площадь треугольника

    // Добавил тесты для нуля и отрицательных чисел

    @Test
    public void testTriangleArea() {
        assertEquals(CalculatorUtils.triangleArea(4, 5), 10.0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTriangleAreaWithZeroBase() {
        CalculatorUtils.triangleArea(0, 5);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTriangleAreaWithZeroHeight() {
        CalculatorUtils.triangleArea(4, 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTriangleAreaWithZeroBaseAndHeight() {
        CalculatorUtils.triangleArea(0, 0);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTriangleAreaWithNegativeBase() {
        CalculatorUtils.triangleArea(-2, 5);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTriangleAreaWithNegativeHeight() {
        CalculatorUtils.triangleArea(2, -5);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testTriangleAreaWithNegativeBaseAndHeight() {
        CalculatorUtils.triangleArea(-2, -5);
    }


    // 3. Арифметические операции

    // Добавил тесты для нуля и отрицательных чисел

    @Test
    public void testAddition() {
        assertEquals(CalculatorUtils.add(3, 4), 7);
    }

    @Test
    public void testAdditionWithNegativeFirst() {
        assertEquals(CalculatorUtils.add(-3, 2), -1);
    }

    @Test
    public void testAdditionWithNegativeSecond() {
        assertEquals(CalculatorUtils.add(3, -2), 1);
    }

    @Test
    public void testAdditionWithZeroFirst() {
        assertEquals(CalculatorUtils.add(0, 5), 5);
    }

    @Test
    public void testAdditionWithZeroSecond() {
        assertEquals(CalculatorUtils.add(5, 0), 5);
    }

    @Test
    public void testSubtraction() {
        assertEquals(CalculatorUtils.subtract(5, 4), 1);
    }

    @Test
    public void testSubtractionWithNegativeFirst() {
        assertEquals(CalculatorUtils.subtract(-3, 2), -5);
    }

    @Test
    public void testSubtractionWithNegativeSecond() {
        assertEquals(CalculatorUtils.subtract(3, -2), 5);
    }

    @Test
    public void testSubtractionWithZeroFirst() {
        assertEquals(CalculatorUtils.subtract(0, 5), -5);
    }

    @Test
    public void testSubtractionWithZeroSecond() {
        assertEquals(CalculatorUtils.subtract(5, 0), 5);
    }

    @Test
    public void testMultiplication() {
        assertEquals(CalculatorUtils.multiply(4, 5), 20);
    }

    @Test
    public void testMultiplicationWithNegativeFirst() {
        assertEquals(CalculatorUtils.multiply(-4, 5), -20);
    }

    @Test
    public void testMultiplicationWithNegativeSecond() {
        assertEquals(CalculatorUtils.multiply(4, -5), -20);
    }

    @Test
    public void testMultiplicationTwoNegatives() {
        assertEquals(CalculatorUtils.multiply(-4, -5), 20);
    }

    @Test
    public void testMultiplicationWithZeroFirst() {
        assertEquals(CalculatorUtils.multiply(0, 5), 0);
    }

    @Test
    public void testMultiplicationWithZeroSecond() {
        assertEquals(CalculatorUtils.multiply(5, 0), 0);
    }

    @Test
    public void testDivision() {
        assertEquals(CalculatorUtils.divide(6, 3), 2);
    }

    @Test
    public void testDivisionWithNegativeFirst() {
        assertEquals(CalculatorUtils.divide(-6, 3), -2);
    }

    @Test
    public void testDivisionWithNegativeSecond() {
        assertEquals(CalculatorUtils.divide(6, -3), -2);
    }

    @Test
    public void testDivisionBothNegative() {
        assertEquals(CalculatorUtils.divide(-6, -3), 2);
    }

    @Test
    public void testDivisionWithZeroFirst() {
        assertEquals(CalculatorUtils.divide(0, 5), 0);
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void testDivisionByZero() {
        CalculatorUtils.divide(5, 0);
    }

    // 4. Сравнение чисел

    // Добавил тесты для нуля и отрицательных чисел

    @Test
    public void testCompare() {
        assertTrue(CalculatorUtils.compare(3, 7) < 0);
        assertTrue(CalculatorUtils.compare(7, 3) > 0);
        assertEquals(CalculatorUtils.compare(5, 5), 0);
    }

    @Test
    public void testCompareNegativeNumbers() {
        assertTrue(CalculatorUtils.compare(-5, -3) < 0);
        assertTrue(CalculatorUtils.compare(-3, -5) > 0);
        assertEquals(CalculatorUtils.compare(-5, -5), 0);
        assertTrue(CalculatorUtils.compare(5, -3) > 0);
        assertTrue(CalculatorUtils.compare(-5, 3) < 0);
    }

    @Test
    public void testCompareWithZero() {
        assertTrue(CalculatorUtils.compare(-5, 0) < 0);
        assertTrue(CalculatorUtils.compare(0, -5) > 0);
        assertEquals(CalculatorUtils.compare(0, 0), 0);
        assertTrue(CalculatorUtils.compare(5, 0) > 0);
        assertTrue(CalculatorUtils.compare(0, 5) < 0);
    }
}
