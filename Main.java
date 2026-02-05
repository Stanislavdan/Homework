public class Main {

    public static void main(String[] args) {
        System.out.println("Зазадние 1");
        runTask1();

        System.out.println("\n");

        System.out.println("Задание 2");
        runTask2();
    }

    // Логика задания 1
    public static void runTask1() {
        // 1. Проверка бега и плавания
        Dog dogBobik = new Dog("Бобик");
        Cat catBarsik = new Cat("Барсик", 15);

        dogBobik.run(150);
        dogBobik.swim(5);

        catBarsik.run(150);
        catBarsik.swim(5);

        System.out.println("------------------------------------------");

        // 2. Работа с миской и массивом котов
        Cat[] cats = {
                new Cat("Мурзик", 10),
                new Cat("Васька", 15),
                new Cat("Рыжик", 20)
        };

        Plate plate = new Plate(40); // В тарелке 40 единиц еды
        plate.info();

        // Кормим котов
        for (Cat cat : cats) {
            cat.eat(plate);
            cat.printSatiety();
        }

        System.out.println("--- Проверка тарелки после кормления ---");
        plate.info();

        System.out.println("--- Добавляем корма ---");
        plate.addFood(20);
        plate.info();

        System.out.println("------------------------------------------");
        System.out.println("Всего животных создано: " + Animal.animalCount);
        System.out.println("Всего котов создано: " + Cat.catCount);
        System.out.println("Всего собак создано: " + Dog.dogCount);
    }

    // Логика задания 2
    public static void runTask2() {
        Circle circle = new Circle(10, "Красный", "Черный");
        Rectangle rectangle = new Rectangle(5, 10, "Синий", "Белый");
        Triangle triangle = new Triangle(3, 4, 5, "Зеленый", "Желтый");

        System.out.println("--- Характеристики Круга ---");
        circle.printCharacteristics();

        System.out.println("\n--- Характеристики Прямоугольника ---");
        rectangle.printCharacteristics();

        System.out.println("\n--- Характеристики Треугольника ---");
        triangle.printCharacteristics();
    }
}

// Классы для задания 1

abstract class Animal {
    String name;
    static int animalCount = 0;

    public Animal(String name) {
        this.name = name;
        animalCount++;
    }

    public void run(int distance) {
        System.out.println(name + " пробежал " + distance + " м.");
    }

    public void swim(int distance) {
        System.out.println(name + " проплыл " + distance + " м.");
    }
}

class Dog extends Animal {
    static int dogCount = 0;

    public Dog(String name) {
        super(name);
        dogCount++;
    }

    @Override
    public void run(int distance) {
        if (distance <= 500) {
            super.run(distance);
        } else {
            System.out.println(name + " не смог пробежать " + distance + " м. (лимит 500)");
        }
    }

    @Override
    public void swim(int distance) {
        if (distance <= 10) {
            super.swim(distance);
        } else {
            System.out.println(name + " не смог проплыть " + distance + " м. (лимит 10)");
        }
    }
}

class Cat extends Animal {
    static int catCount = 0;
    boolean satiety = false;
    int appetite;

    public Cat(String name, int appetite) {
        super(name);
        this.appetite = appetite;
        catCount++;
    }

    @Override
    public void run(int distance) {
        if (distance <= 200) {
            super.run(distance);
        } else {
            System.out.println(name + " не смог пробежать " + distance + " м. (лимит 200)");
        }
    }

    @Override
    public void swim(int distance) {
        System.out.println(name + " не умеет плавать!");
    }

    public void eat(Plate p) {
        if (p.takeFood(appetite)) {
            System.out.println(name + " покушал.");
            satiety = true;
        } else {
            System.out.println(name + " не стал есть, еды мало (" + appetite + ").");
            satiety = false;
        }
    }

    public void printSatiety() {
        System.out.println("Кот " + name + " сыт? -> " + satiety);
    }
}

class Plate {
    private int food;

    public Plate(int food) {
        this.food = food;
    }

    public boolean takeFood(int n) {
        if (food < n) {
            return false;
        }
        food -= n;
        return true;
    }

    public void addFood(int n) {
        if (n > 0) {
            food += n;
            System.out.println("В тарелку добавили " + n + " еды.");
        }
    }

    public void info() {
        System.out.println("Еды в тарелке: " + food);
    }
}

// Классы для задания 2

interface Shape {
    double getPerimeter();
    double getArea();
    String getFillColor();
    String getBorderColor();

    default void printCharacteristics() {
        System.out.printf("Периметр: %.2f\n", getPerimeter());
        System.out.printf("Площадь: %.2f\n", getArea());
        System.out.println("Цвет заливки: " + getFillColor());
        System.out.println("Цвет границ: " + getBorderColor());
    }
}

class Circle implements Shape {
    private final double radius;
    private final String fillColor;
    private final String borderColor;

    public Circle(double radius, String fillColor, String borderColor) {
        this.radius = radius;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    @Override
    public double getPerimeter() { return 2 * Math.PI * radius; }

    @Override
    public double getArea() { return Math.PI * radius * radius; }

    @Override
    public String getFillColor() { return fillColor; }

    @Override
    public String getBorderColor() { return borderColor; }
}

class Rectangle implements Shape {
    private final double width;
    private final double height;
    private final String fillColor;
    private final String borderColor;

    public Rectangle(double width, double height, String fillColor, String borderColor) {
        this.width = width;
        this.height = height;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    @Override
    public double getPerimeter() { return 2 * (width + height); }

    @Override
    public double getArea() { return width * height; }

    @Override
    public String getFillColor() { return fillColor; }

    @Override
    public String getBorderColor() { return borderColor; }
}

class Triangle implements Shape {
    private final double a, b, c;
    private final String fillColor;
    private final String borderColor;

    public Triangle(double a, double b, double c, String fillColor, String borderColor) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.fillColor = fillColor;
        this.borderColor = borderColor;
    }

    @Override
    public double getPerimeter() { return a + b + c; }

    @Override
    public double getArea() {
        double p = getPerimeter() / 2;
        return Math.sqrt(p * (p - a) * (p - b) * (p - c));
    }

    @Override
    public String getFillColor() { return fillColor; }

    @Override
    public String getBorderColor() { return borderColor; }
}
