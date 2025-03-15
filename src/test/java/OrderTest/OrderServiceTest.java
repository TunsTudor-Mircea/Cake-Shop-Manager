package OrderTest;

import Domain.Order;
import Repository.Base.MemoryRepository;
import Service.BirthdayCakeService;
import Service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderServiceTest {

    private OrderService orderService;
    private MemoryRepository<String, Order> orderRepository;

    @BeforeEach
    public void setUp() {
        orderRepository = new MemoryRepository<>();
        BirthdayCakeService cakeService = new BirthdayCakeService();

        cakeService.addBirthdayCake( 12, "chocolate", 10, 20.0);
        cakeService.addBirthdayCake(15, "vanilla", 12, 30.0);
        cakeService.addBirthdayCake(18, "strawberry", 15, 25.0);

        orderService = new OrderService(orderRepository, cakeService);
    }

    @Test
    public void testOrderServiceConstructor() {
        OrderService orderService = new OrderService();
        assertNotNull(orderService);
    }

    @Test
    public void testAddOrder() {
        orderService.addOrder("Alice", "phone", "01/11/2023", Arrays.asList(1, 2));

        Order addedOrder = orderRepository.findById("1");
        assertNotNull(addedOrder);
        assertEquals("Alice", addedOrder.getCustomerName());
        assertEquals("phone", addedOrder.getCustomerContact());
        assertEquals("01/11/2023", addedOrder.getOrderDate());
        assertEquals(Arrays.asList(1, 2), addedOrder.getCakeIds());
    }

    @Test
    public void testRemoveOrder() {
        orderService.addOrder("Alice", "phone", "01/11/2023", Arrays.asList(1, 2));
        orderService.removeOrder(1);

        Order removedOrder = orderRepository.findById("1");
        assertNull(removedOrder);
    }

    @Test
    public void testUpdateOrder() {
        orderService.addOrder("Alice", "phone", "01/11/2023", Arrays.asList(1, 2));

        orderService.updateOrder(1, "Alice-Updated", "fax", "05/11/2023", Arrays.asList(2, 3));
        Order updatedOrder = orderRepository.findById("1");

        assertNotNull(updatedOrder);
        assertEquals("Alice-Updated", updatedOrder.getCustomerName());
        assertEquals("fax", updatedOrder.getCustomerContact());
        assertEquals("05/11/2023", updatedOrder.getOrderDate());
        assertEquals(Arrays.asList(2, 3), updatedOrder.getCakeIds());
    }

    @Test
    public void testGetOrderById() {
        orderService.addOrder("Alice", "phone", "01/11/2023", Arrays.asList(1, 2));
        Order result = orderService.getOrderById(1);

        assertNotNull(result);
        assertEquals("Alice", result.getCustomerName());
    }

    @Test
    public void testGetOrders() {
        orderService.addOrder("Alice", "phone", "01/11/2023", Arrays.asList(1, 2));
        orderService.addOrder("Bob", "email", "02/11/2023", List.of(3));

        List<Order> orders = orderService.getOrders();
        assertEquals(2, orders.size());
    }

    @Test
    public void testFilterOrdersByDate() {
        orderService.addOrder("Alice", "phone", "01/11/2023", Arrays.asList(1, 2));
        orderService.addOrder("Bob", "email", "02/11/2023", List.of(3));

        List<Order> result = orderService.filterOrdersByDate("01/11/2023");
        assertEquals(1, result.size());
        assertEquals("Alice", result.get(0).getCustomerName());
    }

    @Test
    public void testFilterOrdersByCustomerName() {
        orderService.addOrder("Alice", "phone", "01/11/2023", Arrays.asList(1, 2));
        orderService.addOrder("Bob", "email", "02/11/2023", List.of(3));
        orderService.addOrder("Alice", "fax", "03/11/2023", List.of(2));

        List<Order> result = orderService.filterOrdersByCustomerName("Alice");
        assertEquals(2, result.size());
    }

    @Test
    public void testGetEarningsFromDate() {
        orderService.addOrder("Alice", "phone", "01/11/2023", Arrays.asList(1, 2));
        orderService.addOrder("Bob", "email", "02/11/2023", List.of(3));

        double earnings = orderService.getEarningsFromDate("01/11/2023");
        assertEquals(50.0, earnings, 0.01);
    }
}
