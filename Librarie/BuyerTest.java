import org.junit.Test;
import static org.junit.Assert.*;

public class BuyerTest {

    @Test
    public void testConstructorWithName() {

        String buyerName = "Alice";

        Buyer buyer = new Buyer(buyerName);

        assertNotNull(buyer);
        assertEquals(buyerName, buyer.getName());
        assertEquals(18, buyer.getAge());  // Age should be the 18!
    }
}