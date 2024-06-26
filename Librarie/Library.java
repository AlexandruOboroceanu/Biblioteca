import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Library {
    private String nameLibrary;
    private Set<Carte> bookSet;
    private List<Buyer> buyers;
    private Libraryan libraryan = new Libraryan("jon", 10);

    public Library(String name) {
        this.bookSet = new HashSet<>();
        this.buyers = new ArrayList<>();
        this.nameLibrary = name;
    }

    public void addBooks(Collection<Carte> books) {
        bookSet.addAll(books);
    }

    public void addBuyer(Buyer buyer) {
        buyers.add(buyer);
    }

    public List<Buyer> getBuyers() {
        return buyers;
    }
    
    public String getName(){
        return nameLibrary;
    }

    public Set<Carte> getSet(){
        return bookSet;
    }

    public void displayBuyers() {
        System.out.println("Buyers in the Library:");
        for (Buyer buyer : buyers) {
            if (buyer instanceof Ageable) {
                Ageable ageableBuyer = (Ageable) buyer;
                System.out.println("- Name: " + buyer.getName() + ", Age: " + ageableBuyer.getAge());
            } else {
                System.out.println("- Unknown buyer");
            }
        }
    }

    public void displayBooks() {
        System.out.println("Books in the Library:");
        for (Carte book : bookSet) {
            System.out.println("- Name: " + book.getName() + ", ID: " + book.getIdentifier());
        }
    }
}