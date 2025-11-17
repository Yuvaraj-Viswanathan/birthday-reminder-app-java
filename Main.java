import java.time.LocalDate;
import java.util.Scanner;

Great choice! A *Birthday Reminder App (Pure Java, Console-Based)* is simple yet impressive because it shows:

* OOP
* Collections
* Date/Time API
* File I/O (optional)
* Sorting/filtering

Below is a clean project design + sample code to get you started.

---

# 🎉 *Birthday Reminder App – Project Overview*

## *✨ Features*

* Add a person with name + birthday
* View all upcoming birthdays
* Show today’s birthdays
* Save/load birthdays from a file (optional but recommended)
* Automatic sorting by date

---

# 📁 *Project Structure*


BirthdayReminder/
 ├── src/
 │    ├── Main.java
 │    ├── Birthday.java
 │    └── BirthdayManager.java
 └── birthdays.txt   (optional)


---

# 🧱 *Class Design*

### *1. Birthday.java*

Represents a person and their birthday.

### *2. BirthdayManager.java*

Handles:

* Adding birthdays
* Loading/saving
* Filtering upcoming birthdays
* Checking today's birthdays

### *3. Main.java*

Console menu to call the manager.

---

# 🧑‍💻 *Sample Code (Clean & Ready for GitHub)*

### *Birthday.java*

java
import java.time.LocalDate;

public class Birthday {
    private String name;
    private LocalDate date;

    public Birthday(String name, LocalDate date) {
        this.name = name;
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return name + " - " + date.toString();
    }
}


---

### *BirthdayManager.java*

java
import java.io.*;
import java.time.LocalDate;
import java.time.MonthDay;
import java.util.*;

public class BirthdayManager {

    private List<Birthday> birthdays = new ArrayList<>();
    private final String FILE_NAME = "birthdays.txt";

    public void addBirthday(String name, LocalDate date) {
        birthdays.add(new Birthday(name, date));
        System.out.println("Birthday added!");
    }

    public void showAll() {
        birthdays.sort(Comparator.comparing(Birthday::getDate));
        birthdays.forEach(System.out::println);
    }

    public void showTodaysBirthdays() {
        MonthDay today = MonthDay.now();
        boolean found = false;

        for (Birthday b : birthdays) {
            if (MonthDay.from(b.getDate()).equals(today)) {
                System.out.println("🎂 Today: " + b.getName());
                found = true;
            }
        }

        if (!found) System.out.println("No birthdays today.");
    }

    public void showUpcoming() {
        LocalDate now = LocalDate.now();
        birthdays.stream()
                .filter(b -> b.getDate().withYear(now.getYear()).isAfter(now))
                .sorted(Comparator.comparing(b -> b.getDate().withYear(now.getYear())))
                .forEach(b -> System.out.println(b.getName() + " - " + b.getDate()));
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Birthday b : birthdays) {
                writer.write(b.getName() + "," + b.getDate());
                writer.newLine();
            }
            System.out.println("Saved to file!");
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                birthdays.add(new Birthday(parts[0], LocalDate.parse(parts[1])));
            }
            System.out.println("Birthdays loaded!");
        } catch (IOException e) {
            System.out.println("Error loading file.");
        }
    }
}


---

### *Main.java*

java
import java.time.LocalDate;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        BirthdayManager manager = new BirthdayManager();
        manager.loadFromFile();

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- Birthday Reminder ---");
            System.out.println("1. Add Birthday");
            System.out.println("2. Show All Birthdays");
            System.out.println("3. Show Today's Birthdays");
            System.out.println("4. Show Upcoming Birthdays");
            System.out.println("5. Save & Exit");
            System.out.print("Choose: ");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> {
                    System.out.print("Name: ");
                    String name = sc.nextLine();

                    System.out.print("Birthday (yyyy-mm-dd): ");
                    LocalDate date = LocalDate.parse(sc.nextLine());

                    manager.addBirthday(name, date);
                }
                case 2 -> manager.showAll();
                case 3 -> manager.showTodaysBirthdays();
                case 4 -> manager.showUpcoming();
                case 5 -> {
                    manager.saveToFile();
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}


