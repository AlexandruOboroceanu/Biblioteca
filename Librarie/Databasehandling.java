import java.sql.*;
import java.util.Scanner;
import java.util.List;

public class Databasehandling {
    private static final String URL = "jdbc:mysql://localhost:3306/projectbase1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    private User currentUser;

    private Main mainInstance;

    public boolean isAdmin() {
        return currentUser != null && currentUser.isAdmin();
    }

    public void login() {

        System.out.println("Please login");

        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scanner.nextLine();

        boolean isAdmin = username.equals("admin");

        currentUser = new User(username, isAdmin);
        System.out.println("Login successful!");

    }

    private void userMenu() {
        System.out.println("User Database Access Menu:");
        System.out.println("1. Display all books");
        System.out.println("2. Add books to the database");
        System.out.println("3. Display authors");
        System.out.println("0. Back to the main menu");
    }

    private void adminMenu() {
        System.out.println("Admin Database Access Menu:");
        System.out.println("1. Display all books");
        System.out.println("2. Add books to the database");
        System.out.println("3. Display authors");
        System.out.println("4. Add Buyers");
        System.out.println("5. Display buyers");
        System.out.println("0. Back to the main menu");
    }

    public void dataConnection() {
        boolean exit = false;

        login();

        while (true) {
            if (isAdmin()) {
                // Admin has access to all features
                adminMenu();
            } else {
                // Regular user has limited access
                userMenu();
            }

            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    displayAllbooks();
                    break;
                case 2:
                    addBook();
                    break;
                case 3:
                    insertAuthors();
                    break;
                case 4:
                    addBuyers();
                    break;
                case 5:
                    displayBuyers();
                    break;
                case 0:
                    System.out.println("Exiting Database Access Menu.");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }

    public void displayAllbooks() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("select * from books")) {

                while (resultSet.next()) {
                    System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getInt(3));
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void addBook() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Statement statement = connection.createStatement();

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the number of books you want to enter: ");
            int numberOfBooks = scanner.nextInt();

            for (int i = 0; i < numberOfBooks; i++) {
                System.out.println("Book " + (i + 1) + ":");

                System.out.print("Enter book ID: ");
                int bookId = scanner.nextInt();

                scanner.nextLine(); // Consume the newline character left by nextInt()

                System.out.print("Enter book name: ");
                String bookName = scanner.nextLine();

                System.out.print("Enter author ID: ");
                int authorId = scanner.nextInt();

                String insertQuery = "INSERT INTO books (book_id, title, author_id) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setInt(1, bookId);
                    preparedStatement.setString(2, bookName);
                    preparedStatement.setInt(3, authorId);
                    preparedStatement.executeUpdate();
                    System.out.println("Book added successfully!");
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertAuthors() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String insertQuery = "INSERT INTO authors (name) VALUES (?)";

            List<Autor> authorList = mainInstance.getAuthor();

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                for (Autor author : authorList) {
                    preparedStatement.setString(1, author.getNameAutor());
                    preparedStatement.executeUpdate();
                }

                displayAuthorsFromDatabase();
            }
        } catch (SQLException e) {
            System.out.println("Error inserting authors: " + e.getMessage());
        }
    }

    public Databasehandling(Main mainInstance) {
        this.mainInstance = mainInstance;
    }

    private void displayAuthorsFromDatabase() {
        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            String selectQuery = "SELECT * FROM authors";

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(selectQuery)) {

                while (resultSet.next()) {
                    System.out.println("Author Name: " + resultSet.getString("name"));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error displaying authors: " + e.getMessage());
        }
    }

    public void addBuyers() {
        if (!isAdmin()) {
            System.out.println("You do not have permission to add buyers.");
            return;
        }

        try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the number of buyers to add: ");
            int numberOfBuyers = scanner.nextInt();
            scanner.nextLine();

            for (int i = 0; i < numberOfBuyers; i++) {
                System.out.println("Buyer " + (i + 1) + ":");

                System.out.print("Enter buyer name: ");
                String buyerName = scanner.nextLine();

                // Insert the new buyer into the database
                String insertQuery = "INSERT INTO buyers (name) VALUES (?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                    preparedStatement.setString(1, buyerName);
                    preparedStatement.executeUpdate();
                    System.out.println("Buyer added successfully!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error adding buyers: " + e.getMessage());
        }
    }

    public void displayBuyers() {
        if (!isAdmin()) {
            System.out.println("You do not have permission to display buyers.");
            return;
        }
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                try (Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                 Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery("SELECT * FROM buyers")) {

                    while (resultSet.next()) {
                        String buyerName = resultSet.getString("name");

                        System.out.println(buyerName);
                    }
            }
        } catch (Exception e) {
            System.out.println("Error displaying buyers: " + e.getMessage());
        }
    }

}