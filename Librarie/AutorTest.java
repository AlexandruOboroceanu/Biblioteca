import org.junit.Test;
import static org.junit.Assert.*;

public class AutorTest {

    @Test
    public void testConstructor() {

        String autorName = "John";
        int autorAge = 35;

        Autor autor = new Autor(autorName, autorAge);

        assertNotNull(autor);
        assertEquals(autorName, autor.getNameAutor());
        assertEquals(autorAge, autor.getAge());
        assertTrue(autor.getBooks().isEmpty());
    }
}