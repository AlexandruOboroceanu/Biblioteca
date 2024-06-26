import org.junit.Test;
import static org.junit.Assert.*;

public class LibraryTest {

    @Test
    public void testConstructor() {
        String libraryName = "MyLibrary";

        Library library = new Library(libraryName);

        assertNotNull(library); // Check if name is empty 
        assertEquals(libraryName, library.getName()); // Check if name checks out with itself 
        assertTrue(library.getSet().isEmpty()); // Check if book set is empty initially
        assertTrue(library.getBuyers().isEmpty()); // Check if buyers set is empty initially
    }
}

// **** Further tests could be added by testing the methods inside the Library class ****