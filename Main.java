public class Main {

    private static final int SIZE = 4;

    public static void main(String[] args) {

        System.out.println("--- Тест 1: Корректный массив ---");
        String[][] correctMatrix = {
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"}
        };

        try {
            int result = processArray(correctMatrix);
            System.out.println("Сумма элементов массива: " + result);
        } catch (MyArraySizeException e) {
            System.out.println("Ошибка размера массива: " + e.getMessage());
        } catch (MyArrayDataException e) {
            System.out.println("Ошибка данных: " + e.getMessage());
        }

        System.out.println("\n--- Тест 2: Неверный размер массива ---");
        String[][] wrongSizeMatrix = {
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"}
        };

        try {
            processArray(wrongSizeMatrix);
        } catch (MyArraySizeException e) {
            System.out.println("Ошибка размера массива: " + e.getMessage());
        } catch (MyArrayDataException e) {
            System.out.println("Ошибка данных: " + e.getMessage());
        }

        System.out.println("\n--- Тест 3: Некорректные данные ---");
        String[][] wrongDataMatrix = {
                {"1", "2", "3", "4"},
                {"1", "2", "3", "4"},
                {"1", "Abcde", "3", "4"},
                {"1", "2", "3", "4"}
        };

        try {
            processArray(wrongDataMatrix);
        } catch (MyArraySizeException e) {
            System.out.println("Ошибка размера массива: " + e.getMessage());
        } catch (MyArrayDataException e) {
            System.out.println("Ошибка данных: " + e.getMessage());
        }


        System.out.println("\n--- Задание 4: ArrayIndexOutOfBoundsException ---");
        generateOutOfBoundsError();
    }

    public static int processArray(String[][] array)
            throws MyArraySizeException, MyArrayDataException {

        validateArraySize(array);

        int sum = 0;

        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                try {
                    sum += Integer.parseInt(array[i][j]);
                } catch (NumberFormatException e) {
                    throw new MyArrayDataException(i, j, array[i][j]);
                }
            }
        }

        return sum;
    }

    private static void validateArraySize(String[][] array)
            throws MyArraySizeException {

        if (array == null) {
            throw new MyArraySizeException("Массив не должен быть null.");
        }

        if (array.length != SIZE) {
            throw new MyArraySizeException(
                    "Количество строк должно быть " + SIZE + ". Обнаружено: " + array.length
            );
        }

        for (int i = 0; i < SIZE; i++) {
            if (array[i] == null || array[i].length != SIZE) {
                throw new MyArraySizeException(
                        "В строке " + i + " количество столбцов должно быть " + SIZE +
                                ". Обнаружено: " + (array[i] == null ? "null" : array[i].length)
                );
            }
        }
    }

    // 4
    public static void generateOutOfBoundsError() {
        int[] nums = {1, 2, 3};
        try {
            int value = nums[10];
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Выход за границы массива!");
            System.out.println("Детали: " + e);
        }
    }

    static class MyArraySizeException extends Exception {
        public MyArraySizeException(String message) {
            super(message);
        }
    }

    static class MyArrayDataException extends Exception {

        private final int row;
        private final int column;
        private final String badValue;

        public MyArrayDataException(int row, int column, String badValue) {
            super("Неверные данные в ячейке [" + row + "][" + column +
                    "]: '" + badValue + "' не является числом.");
            this.row = row;
            this.column = column;
            this.badValue = badValue;
        }

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public String getBadValue() {
            return badValue;
        }
    }
}
