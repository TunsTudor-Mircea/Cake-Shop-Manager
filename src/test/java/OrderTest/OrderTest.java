package OrderTest;

import Domain.Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {

    private Order order;

    @BeforeEach
    public void setUp() {
        List<Integer> cakeIds = new ArrayList<>(Arrays.asList(101, 102, 103));
        order = new Order("O123", "Alice", "123-456-7890", "2023-11-01", cakeIds);
    }

    @Test
    public void testConstructorAndGetters() {
        assertEquals("O123", order.getOrderId());
        assertEquals("Alice", order.getCustomerName());
        assertEquals("123-456-7890", order.getCustomerContact());
        assertEquals("2023-11-01", order.getOrderDate());
        assertEquals(Arrays.asList(101, 102, 103), order.getCakeIds());
    }

    @Test
    public void testSetAndGetId() {
        order.setId("O124");
        assertEquals("O124", order.getId());
    }

    @Test
    public void testSetOrderId() {
        order.setOrderId("O125");
        assertEquals("O125", order.getOrderId());
    }

    @Test
    public void testSetCustomerName() {
        order.setCustomerName("Bob");
        assertEquals("Bob", order.getCustomerName());
    }

    @Test
    public void testSetCustomerContact() {
        order.setCustomerContact("987-654-3210");
        assertEquals("987-654-3210", order.getCustomerContact());
    }

    @Test
    public void testSetOrderDate() {
        order.setOrderDate("2023-12-01");
        assertEquals("2023-12-01", order.getOrderDate());
    }

    @Test
    public void testSetCakeIds() {
        List<Integer> newCakeIds = Arrays.asList(104, 105);
        order.setCakeIds(newCakeIds);
        assertEquals(newCakeIds, order.getCakeIds());
    }

    @Test
    public void testAddCake() {
        order.addCake(104);
        assertEquals(Arrays.asList(101, 102, 103, 104), order.getCakeIds());
    }

    @Test
    public void testToString() {
        String expectedString = "Order O123\n" +
                "- customerName: Alice\n" +
                "- customerContact:123-456-7890\n" +
                "- orderDate: 2023-11-01\n" +
                "- cake ID's: [101, 102, 103]\n";
        assertEquals(expectedString, order.toString());
    }
}
