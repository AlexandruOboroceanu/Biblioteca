import org.junit.Test;
import static org.junit.Assert.*;

public class CarteTest {

    @Test
    public void testConstructorWithName() {
        String bookName = "The Great Gatsby";

        Carte book = new Carte(bookName);

        assertNotNull(book);
        assertEquals(bookName, book.getName());
        assertNotNull(book.getIdentifier());
    }

    //  *** This class also checks if the id generated is unique across books ***
    
    @Test
    public void testUniqueIdGenerated() {
        Carte book1 = new Carte("Book1");
        Carte book2 = new Carte("Book2");

        assertNotNull(book1.getIdentifier());
        assertNotNull(book2.getIdentifier());
        assertNotEquals(book1.getIdentifier(), book2.getIdentifier());
    }



}