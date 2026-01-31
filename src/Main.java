import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        System.out.println("Задание 1:");
        printThreeWords();
        System.out.println("--------------------");

        System.out.println("Задание 2:");
        checkSumSign();
        System.out.println("--------------------");

        System.out.println("Задание 3:");
        printColor();
        System.out.println("--------------------");

        System.out.println("Задание 4:");
        compareNumbers();
        System.out.println("--------------------");

        System.out.println("Задание 5:");
        System.out.println(isSumInRange(5, 10));
        System.out.println("--------------------");

        System.out.println("Задание 6:");
        printPositiveOrNegative(1);
        System.out.println("--------------------");

        System.out.println("Задание 7:");
        System.out.println(isNegative(-10));
        System.out.println("--------------------");

        System.out.println("Задание 8:");
        printStringMultipleTimes("Привет!", 3);
        System.out.println("--------------------");

        System.out.println("Задание 9:");
        System.out.println(isLeapYear(2024));
        System.out.println("--------------------");

        System.out.println("Задание 10:");
        invertArray();
        System.out.println("--------------------");

        System.out.println("Задание 11:");
        fillArrayWithSequence();
        System.out.println("--------------------");

        System.out.println("Задание 12:");
        multiplyNumbersLessThanSix();
        System.out.println("--------------------");

        System.out.println("Задание 13:");
        fillDiagonalMatrix(7);
        System.out.println("--------------------");

        System.out.println("Задание 14:");
        int[] newArray = createArray(7, 25);
        System.out.println(Arrays.toString(newArray));
        System.out.println("--------------------");
    }

    // 1
    public static void printThreeWords() {
        System.out.println("Orange");
        System.out.println("Banana");
        System.out.println("Apple");
    }

    // 2
    public static void checkSumSign() {
        int a = 10;
        int b = -20;
        int sum = a + b;
        if (sum >= 0) {
            System.out.println("Сумма положительная");
        } else {
            System.out.println("Сумма отрицательная");
        }
    }

    // 3
    public static void printColor() {
        int value = 101;
        if (value <= 0) {
            System.out.println("Красный");
        } else if (value > 0 && value <= 100) {
            System.out.println("Желтый");
        } else {
            System.out.println("Зеленый");
        }
    }

    // 4
    public static void compareNumbers() {
        int a = 5;
        int b = 5;
        if (a >= b) {
            System.out.println("a >= b");
        } else {
            System.out.println("a < b");
        }
    }

    // 5
    public static boolean isSumInRange(int a, int b) {
        int sum = a + b;
        return sum >= 10 && sum <= 20;
    }

    // 6
    public static void printPositiveOrNegative(int number) {
        if (number >= 0) {
            System.out.println("Передали положительное число");
        } else {
            System.out.println("Передали отрицательное число");
        }
    }

    // 7
    public static boolean isNegative(int number) {
        return number < 0;
    }

    // 8
    public static void printStringMultipleTimes(String str, int times) {
        for (int i = 0; i < times; i++) {
            System.out.println(str);
        }
    }

    // 9
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    // 10
    public static void invertArray() {
        int[] arr = {1, 1, 0, 0, 1, 0, 1, 1, 0, 0};
        System.out.println("Исходный массив: " + Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0) {
                arr[i] = 1;
            } else {
                arr[i] = 0;
            }
        }
        System.out.println("Инвертированный массив: " + Arrays.toString(arr));
    }

    // 11
    public static void fillArrayWithSequence() {
        int[] arr = new int[100];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = i + 1;
        }
        System.out.println(Arrays.toString(arr));
    }

    // 12
    public static void multiplyNumbersLessThanSix() {
        int[] arr = {1, 5, 3, 2, 11, 4, 5, 2, 4, 8, 9, 1};
        System.out.println("Исходный массив: " + Arrays.toString(arr));
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 6) {
                arr[i] = arr[i] * 2;
            }
        }
        System.out.println("Измененный массив: " + Arrays.toString(arr));
    }

    // 13
    public static void fillDiagonalMatrix(int size) {
        int[][] matrix = new int[size][size];
        for (int i = 0; i < size; i++) {
            matrix[i][i] = 1;
            matrix[i][size - 1 - i] = 1;
        }

        for (int i = 0; i < size; i++) {
            System.out.println(Arrays.toString(matrix[i]));
        }
    }

    // 14
    public static int[] createArray(int len, int initialValue) {
        int[] arr = new int[len];
        Arrays.fill(arr, initialValue);
        return arr;
    }
}
