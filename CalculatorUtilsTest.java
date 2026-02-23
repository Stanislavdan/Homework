package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorUtilsTest {

    // 1. Факториал

    // Переделал тесты

    @Test
    void testFactorialZero() {
        assertEquals(1L, CalculatorUtils.factorial(0));
    }

    @Test
    void testFactorialOne() {
        assertEquals(1L, CalculatorUtils.factorial(1));
    }

    @Test
    void testFactorialTwo() {
        assertEquals(2L, CalculatorUtils.factorial(2));
    }

    @Test
    void testFactorialFive() {
        assertEquals(120L, CalculatorUtils.factorial(5));
    }

    @Test
    void testFactorialLargeNumber() {
        assertEquals(3628800L, CalculatorUtils.factorial(10));
    }

    @Test
    void testFactorialMaxLong() {
        assertEquals(2432902008176640000L, CalculatorUtils.factorial(20));
    }

    @Test
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class,
                () -> CalculatorUtils.factorial(-1));
    }

    // 2. Площадь треугольника

    // Добавил тесты для нуля и отрицательных чисел

    @Test
    void testTriangleArea() {
        assertEquals(10.0,
                CalculatorUtils.triangleArea(4, 5));
    }

    @Test
    void testTriangleAreaWithZeroBase() {
        assertThrows(IllegalArgumentException.class, () -> CalculatorUtils.triangleArea(0, 5));
    }

    @Test
    void testTriangleAreaWithZeroHeight() {
        assertThrows(IllegalArgumentException.class, () -> CalculatorUtils.triangleArea(4, 0));
    }

    @Test
    void testTriangleAreaWithZeroBaseAndHeight() {
        assertThrows(IllegalArgumentException.class, () -> CalculatorUtils.triangleArea(0, 0));
    }

    @Test
    void testTriangleAreaWithNegativeBase() {
        assertThrows(IllegalArgumentException.class, () -> CalculatorUtils.triangleArea(-2, 5));
    }

    @Test
    void testTriangleAreaWithNegativeHeight() {
        assertThrows(IllegalArgumentException.class, () -> CalculatorUtils.triangleArea(2, -5));
    }

    @Test
    void testTriangleAreaWithNegativeBaseAndHeight() {
        assertThrows(IllegalArgumentException.class, () -> CalculatorUtils.triangleArea(-2, -5));
    }

    // 3. Арифметические операции

    // Добавил тесты для нуля и отрицательных чисел

    @Test
    void testAddition() {
        assertEquals(7, CalculatorUtils.add(3, 4));
    }

    @Test
    void testAdditionWithNegativeFirst() {
        assertEquals(-1, CalculatorUtils.add(-3, 2));
    }

    @Test
    void testAdditionWithNegativeSecond() {
        assertEquals(1, CalculatorUtils.add(3, -2));
    }

    @Test
    void testAdditionWithZeroFirst() {
        assertEquals(5, CalculatorUtils.add(0, 5));
    }

    @Test
    void testAdditionWithZeroSecond() {
        assertEquals(5, CalculatorUtils.add(5, 0));
    }

    @Test
    void testSubtraction() {
        assertEquals(1, CalculatorUtils.subtract(5, 4));
    }

    @Test
    void testSubtractionWithNegativeFirst() {
        assertEquals(-5, CalculatorUtils.subtract(-3, 2));
    }

    @Test
    void testSubtractionWithNegativeSecond() {
        assertEquals(5, CalculatorUtils.subtract(3, -2));
    }

    @Test
    void testSubtractionWithZeroFirst() {
        assertEquals(-5, CalculatorUtils.subtract(0, 5));
    }

    @Test
    void testSubtractionWithZeroSecond() {
        assertEquals(5, CalculatorUtils.subtract(5, 0));
    }

    @Test
    void testMultiplication() {
        assertEquals(20, CalculatorUtils.multiply(4, 5));
    }

    @Test
    void testMultiplicationWithNegativeFirst() {
        assertEquals(-20, CalculatorUtils.multiply(-4, 5));
    }

    @Test
    void testMultiplicationWithNegativeSecond() {
        assertEquals(-20, CalculatorUtils.multiply(4, -5));
    }

    @Test
    void testMultiplicationTwoNegatives() {
        assertEquals(20, CalculatorUtils.multiply(-4, -5));
    }

    @Test
    void testMultiplicationWithZeroFirst() {
        assertEquals(0, CalculatorUtils.multiply(0, 5));
    }

    @Test
    void testMultiplicationWithZeroSecond() {
        assertEquals(0, CalculatorUtils.multiply(5, 0));
    }

    @Test
    void testDivision() {
        assertEquals(2, CalculatorUtils.divide(6, 3));
    }

    @Test
    void testDivisionWithNegativeFirst() {
        assertEquals(-2, CalculatorUtils.divide(-6, 3));
    }

    @Test
    void testDivisionWithNegativeSecond() {
        assertEquals(-2, CalculatorUtils.divide(6, -3));
    }

    @Test
    void testDivisionBothNegative() {
        assertEquals(2, CalculatorUtils.divide(-6, -3));
    }

    @Test
    void testDivisionWithZeroFirst() {
        assertEquals(0, CalculatorUtils.divide(0, 5));
    }

    @Test
    void testDivisionByZero() {
        assertThrows(ArithmeticException.class,
                () -> CalculatorUtils.divide(5, 0));
    }

    // 4. Сравнение чисел

    // Добавил тесты для нуля и отрицательных чисел

    @Test
    void testCompare() {
        assertTrue(CalculatorUtils.compare(3, 7) < 0);
        assertTrue(CalculatorUtils.compare(10, 5) > 0);
        assertEquals(0, CalculatorUtils.compare(5, 5));
    }

    @Test
    void testCompareNegativeNumbers() {
        assertTrue(CalculatorUtils.compare(-5, -3) < 0);
        assertTrue(CalculatorUtils.compare(-3, -5) > 0);
        assertEquals(0, CalculatorUtils.compare(-5, -5));
        assertTrue(CalculatorUtils.compare(5, -3) > 0);
        assertTrue(CalculatorUtils.compare(-5, 3) < 0);
    }

    @Test
    void testCompareWithZero() {
        assertTrue(CalculatorUtils.compare(-5, 0) < 0);
        assertTrue(CalculatorUtils.compare(0, -5) > 0);
        assertEquals(0, CalculatorUtils.compare(0, 0));
        assertTrue(CalculatorUtils.compare(5, 0) > 0);
        assertTrue(CalculatorUtils.compare(0, 5) < 0);
    }
}
