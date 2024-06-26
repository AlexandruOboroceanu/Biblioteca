import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {

    Scanner scanner = new Scanner(System.in);
    Library library = null;
    private List<Autor> authorList = new ArrayList<>();

    Databasehandling database = new Databasehandling(this);
    public void displayOptions() {
        System.out.println("1. Create ur library");
        System.out.println("2. Add author and books");
        System.out.println("3. Display authors and books");
        System.out.println("4. Sort Authors by name");
        System.out.println("5. Sort Books");
        System.out.println("6. Store data to file");
        System.out.println("7. ACCESS DATABASE");
        System.out.println("0. Exit");
    }

    public static int getUserChoice(Scanner scanner) {

        System.out.println("Enter a number");

        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            scanner.next();
        }
        int userInt = scanner.nextInt();
        return userInt;
    }

    public void processUserChoice(int choice) {
        switch (choice) {
            case 1:
                createLibrary();
                break;
            case 2:
                createAuthor();
                break;
            case 3:
                displayAuthorsAndBooks();
                break;
            case 4:
                sortAuthorsByName();
                break;
            case 5:
                sortBooksOfAuthors();
                break;
            case 6:
                storeDataToFile();
                break;
            case 7:
                database.dataConnection();
                break;
            case 0:
                System.out.println("Exiting menu.");
                break;
            default:
                System.out.println("Invalid choice. Please choose a valid option.");
        }
    }

    public void runmenu() {
        int userChoice;

        System.out.println("Choose an initial mode:");
        System.out.println("1. Load data from file");
        System.out.println("2. Start fresh");
    
        int initialChoice = getUserChoice(scanner);

        if (initialChoice == 1) {
            loadDataConcurrently();
        } else if (initialChoice != 2) {
            System.out.println("Invalid choice. Exiting menu.");
            return;
        }

        do {
            displayOptions();
            userChoice = getUserChoice(scanner);
            System.out.print("\033[H\033[2J");
            System.out.flush();
            processUserChoice(userChoice);
        } while (userChoice != 0);
        scanner.close();
    }

    public void loadDataConcurrently() {
        Thread loadDataThread = new Thread(() -> {
            loadDataFromFile();
        });

        loadDataThread.start();

        try {
            loadDataThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public void createLibrary() {
        System.out.println("Enter a name for you library");
        String libraryName = "";

        while (libraryName == null || libraryName.trim().isEmpty()) {
            libraryName = scanner.nextLine();
        }

        library = new Library(libraryName); 
        System.out.println("Library '" + library.getName() + "' created successfully.");
    }

    public void createAuthor(){
        System.out.println("Enter the name of the author you want to add to you library: ");
        String authorName = "";

        while (authorName == null || authorName.trim().isEmpty()) {
            authorName = scanner.nextLine();
        }

        System.out.println("Enter the age of the author: ");
        int authorAge = 0;
    
        while (true) {
            try {
                authorAge = Integer.parseInt(scanner.nextLine().trim());
                if (authorAge >= 0) {
                    break;
                } else {
                    System.out.println("Invalid age. Please enter a non-negative integer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer for the age.");
            }
        }
    
        Autor autor = new Autor(authorName, authorAge);
        authorList.add(autor);
        autor.addBooks();
    }

    public void displayAuthorsAndBooks() {
        System.out.println("Authors and their Books:");
        for (Autor author : authorList) {
            System.out.println("Author: " + author.getNameAutor() + ", Age: " + author.getAge());
            List<Carte> books = author.getBooks();
            if (books.isEmpty()) {
                System.out.println("No books for this author.");
            } else {
                System.out.println("Books:");
                for (Carte book : books) {
                    System.out.println("- " + book.getName());
                }
            }
            System.out.println();
        }
    }

    public void storeDataToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("library_data.txt"))) {
            for (Autor author : authorList) {
                writer.write("Author: " + author.getNameAutor() + ", Age: " + author.getAge());
                writer.newLine();
                List<Carte> books = author.getBooks();
                if (!books.isEmpty()) {
                    writer.write("Books:");
                    writer.newLine();
                    for (Carte book : books) {
                        writer.write("- " + book.getName());
                        writer.newLine();
                    }
                }
                writer.newLine();
            }
            System.out.println("Data stored successfully.");
        } catch (IOException e) {
            System.out.println("Error storing data: " + e.getMessage());
        }
    }

    public void loadDataFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader("library_data.txt"))) {
            String line;
            Autor currentAuthor = null;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Author:")) {
                    // Parse author information
                    String[] authorInfo = line.split(", ");
                    String authorName = authorInfo[0].substring("Author: ".length());
                    int authorAge = Integer.parseInt(authorInfo[1].substring("Age: ".length()));
                    currentAuthor = new Autor(authorName, authorAge);
                    authorList.add(currentAuthor);
                } else if (line.startsWith("- ")) {
                    // Parse book information and add to the current author
                    if (currentAuthor != null) {
                        try {
                            currentAuthor.addBook(new Carte(line.substring(2)));
                        } catch (bookListFull e) {
                            System.out.println("Error adding book: " + e.getMessage());
                        } catch (invalidName e) {
                            System.out.println("Invalid name for book: " + e.getMessage());
                        }
                    }
                }
            }
            System.out.println("Data loaded successfully.");
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    public void sortBooksOfAuthors() {
        for (Autor author : authorList) {
            Collections.sort(author.getBooks());
        }
    }
    public List<Autor> getAuthor(){
        return authorList;
    }
    public void sortAuthorsByName() {
        Collections.sort(authorList);
    }


    public static void main(String[] args) {
        Main menu = new Main();
        menu.runmenu();
    }
}