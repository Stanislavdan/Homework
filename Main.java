public class Main {
    public static void main(String[] args) {
        // Задание 1 и 2:
        System.out.println("Задание 1 и 2:");

        Product[] productsArray = new Product[5];

        productsArray[0] = new Product("Samsung S25 Ultra", "01.02.2025", "Samsung Corp.", "Korea", 120000, true);
        productsArray[1] = new Product("iPhone 15", "10.01.2025", "Apple Inc.", "USA", 95000, false);
        productsArray[2] = new Product("Xiaomi 14", "15.12.2024", "Xiaomi", "China", 60000, false);
        productsArray[3] = new Product("Sony Headphones", "05.11.2024", "Sony", "Japan", 25000, true);
        productsArray[4] = new Product("Dyson Hair Dryer", "20.02.2025", "Dyson", "Malaysia", 45000, true);

        for (int i = 0; i < productsArray.length; i++) {
            productsArray[i].printInfo();
        }

        System.out.println("-----------------------------------");

        // Задание 3:
        System.out.println("Задание 3:");

        Park disneyLand = new Park("Disneyland");

        Park.Attraction rollerCoaster = disneyLand.new Attraction("Американские горки", "10:00 - 22:00", 500);
        Park.Attraction carousel = disneyLand.new Attraction("Карусель", "09:00 - 20:00", 200);
        Park.Attraction horrorRoom = disneyLand.new Attraction("Комната страха", "18:00 - 00:00", 400);

        rollerCoaster.printAttractionInfo();
        carousel.printAttractionInfo();
        horrorRoom.printAttractionInfo();
    }
}

// 1
class Product {
    String name;
    String productionDate;
    String manufacturer;
    String countryOfOrigin;
    double price;
    boolean isBooked;

    public Product(String name, String productionDate, String manufacturer,
                   String countryOfOrigin, double price, boolean isBooked) {
        this.name = name;
        this.productionDate = productionDate;
        this.manufacturer = manufacturer;
        this.countryOfOrigin = countryOfOrigin;
        this.price = price;
        this.isBooked = isBooked;
    }

    public void printInfo() {
        System.out.println("Название: " + name + " | Дата производства: " + productionDate +
                " | Производитель: " + manufacturer + " | Страна происхождения: " + countryOfOrigin +
                " | Цена: " + price + " руб. | Состояние бронирования: " + isBooked);
    }
}

// 3
class Park {
    String parkName;

    public Park(String parkName) {
        this.parkName = parkName;
    }

    public class Attraction {
        String attractionName;
        String workingHours;
        double cost;

        public Attraction(String attractionName, String workingHours, double cost) {
            this.attractionName = attractionName;
            this.workingHours = workingHours;
            this.cost = cost;
        }

        public void printAttractionInfo() {
            System.out.println("Парк: " + parkName + " -> Аттракцион: " + attractionName +
                    " | Время: " + workingHours + " | Цена: " + cost);
        }
    }
}