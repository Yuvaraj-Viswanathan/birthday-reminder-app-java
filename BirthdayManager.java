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

