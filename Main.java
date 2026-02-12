import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        System.out.println("--- Задание 1: Студенты ---");

        Set<Student> students = new HashSet<>();

        Student s1 = new Student("Иван Иванов", "Группа-101", 1);
        s1.addGrade(5);
        s1.addGrade(4);
        s1.addGrade(5);

        Student s2 = new Student("Петр Петров", "Группа-101", 1);
        s2.addGrade(2);
        s2.addGrade(2);
        s2.addGrade(3);

        Student s3 = new Student("Анна Сидорова", "Группа-202", 2);
        s3.addGrade(4);
        s3.addGrade(3);
        s3.addGrade(4);

        students.addAll(Arrays.asList(s1, s2, s3));

        System.out.println("--- До обработки ---");
        students.forEach(System.out::println);

        removeLowGradeStudents(students);
        promoteStudents(students);

        System.out.println("\n--- После обработки ---");
        students.forEach(System.out::println);

        System.out.println("\n--- Студенты 2 курса ---");
        printStudents(students, 2);


        System.out.println("\n--- Задание 2: Телефонный справочник ---");

        PhoneBook phoneBook = new PhoneBook();
        phoneBook.add("Смирнов", "89991112233");
        phoneBook.add("Смирнов", "89997778899");
        phoneBook.add("Кузнецов", "89994445566");

        System.out.println("Смирнов: " + phoneBook.get("Смирнов"));
        System.out.println("Соколов: " + phoneBook.get("Соколов"));
    }

    // Методы обработки студентов:

    public static void removeLowGradeStudents(Set<Student> students) {
        Objects.requireNonNull(students, "Студент не может быть null");
        students.removeIf(student -> student.getAverageGrade() < 3.0);
    }

    public static void promoteStudents(Set<Student> students) {
        Objects.requireNonNull(students, "Студент не может быть null");
        students.stream()
                .filter(student -> student.getAverageGrade() >= 3.0)
                .forEach(Student::promote);
    }

    public static void printStudents(Set<Student> students, int course) {
        Objects.requireNonNull(students, "Студент не может быть null");
        List<String> names = students.stream()
                .filter(student -> student.getCourse() == course)
                .map(Student::getName)
                .collect(Collectors.toList());

        if (names.isEmpty()) {
            System.out.println("Студентов на этом курсе не найдено.");
        } else {
            names.forEach(System.out::println);
        }
    }

    static class Student {

        private final String name;
        private final String group;
        private int course;
        private final List<Integer> grades = new ArrayList<>();

        public Student(String name, String group, int course) {
            Objects.requireNonNull(name, "Имя не может быть null");
            Objects.requireNonNull(group, "Группа не может быть null");
            if (name.isBlank()) {
                throw new IllegalArgumentException("Имя не может быть пустым");
            }
            if (group.isBlank()) {
                throw new IllegalArgumentException("Группа не может быть пустой");
            }
            if (course <= 0) {
                throw new IllegalArgumentException("Курс должен быть > 0, получено " + course);
            }
            this.name = name;
            this.group = group;
            this.course = course;
        }

        public void addGrade(int grade) {
            if (grade < 1 || grade > 5) {
                throw new IllegalArgumentException("Оценка должна быть от 1 до 5, получено " + grade);
            }
            grades.add(grade);
        }

        public double getAverageGrade() {
            if (grades.isEmpty()) return 0.0;
            return grades.stream()
                    .mapToInt(Integer::intValue)
                    .average()
                    .orElse(0.0);
        }

        public void promote() {
            course++;
        }

        public String getName() {
            return name;
        }

        public int getCourse() {
            return course;
        }

        public List<Integer> getGrades() {
            return Collections.unmodifiableList(grades);
        }

        @Override
        public String toString() {
            return String.format(
                    "Студент [Имя = %s, %s, курс = %d, ср.балл = %.2f]",
                    name, group, course, getAverageGrade()
            );
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Student)) return false;
            Student student = (Student) o;
            return Objects.equals(name, student.name) &&
                    Objects.equals(group, student.group);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, group);
        }
    }

    // Телефонный справочник

    static class PhoneBook {

        private final Map<String, List<String>> book = new HashMap<>();

        public void add(String surname, String phoneNumber) {
            Objects.requireNonNull(surname, "Фамилия не может быть null");
            Objects.requireNonNull(phoneNumber, "Номер телефона не может быть null");
            if (surname.isBlank()) {
                throw new IllegalArgumentException("Фамилия не может быть пустая");
            }
            if (phoneNumber.isBlank()) {
                throw new IllegalArgumentException("Номер телефона не может быть пустым");
            }
            book.computeIfAbsent(surname, k -> new ArrayList<>()).add(phoneNumber);
        }

        public List<String> get(String surname) {
            Objects.requireNonNull(surname, "Фамилия не может быть null");
            List<String> numbers = book.get(surname);
            return numbers == null
                    ? Collections.emptyList()
                    : Collections.unmodifiableList(numbers);
        }
    }
}