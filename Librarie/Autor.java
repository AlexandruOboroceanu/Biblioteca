import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Autor implements Ageable,Comparable<Autor> {

    private String nameAutor;
    private int age;
    private static final int MAX_BOOKS = 10;
    private ArrayList<Carte> lisBooks;

    public Autor(String name,int age) {
        this.nameAutor = name;
        this.age = age;
        this.lisBooks = new ArrayList<>();
    }

    public String getNameAutor() {
        return nameAutor;
    }

    @Override
    public int getAge(){
        return age;
    }

    public void addBook(Carte book) throws bookListFull, invalidName {
        if (lisBooks.size() >= MAX_BOOKS) {
            throw new bookListFull("Cannot add more books. The book list is full.");
        }

        if (book.getName() == null || book.getName().trim().isEmpty()) {
            throw new invalidName("Invalid book name. Please provide a valid name for the book.");
        }

        lisBooks.add(book);
        System.out.println("Book '" + book.getName() + "' with ID '" + book.getIdentifier() + "' added successfully.");
    }

    public void addBooks() {
    Scanner scanner = new Scanner(System.in);

    int n = 0;
    boolean validInput = false;

    while (!validInput) {
        try {
            System.out.print("Enter the number of books to add: ");
            n = scanner.nextInt();

            if (n > 0) {
                validInput = true;
            } else {
                System.out.println("Please enter a positive integer.");
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid integer.");
            scanner.nextLine();  
        }
    }

    scanner.nextLine();  

    for (int i = 0; i < n; i++) {
        try {
            System.out.print("Enter the name of book " + (i + 1) + ": ");
            String bookName = scanner.nextLine();
            Carte book = new Carte(bookName);
            addBook(book);
        } catch (bookListFull| invalidName e) {
            System.out.println("Error: " + e.getMessage());
            i--;
        }
    }

}

    public ArrayList<Carte> getBooks() {
        return lisBooks;
    }

    @Override
    public int compareTo(Autor otherAuthor) {
        return this.nameAutor.compareTo(otherAuthor.getNameAutor());
    }

}