package OrderTest;

import Domain.Order;
import Repository.Base.MemoryRepository;
import Service.BirthdayCakeService;
import Service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

        Optional<Order> addedOrder = orderRepository.findById("1");
        Order addedOrder1 = addedOrder.get();
        assertNotNull(addedOrder);
        assertEquals("Alice", addedOrder1.getCustomerName());
        assertEquals("phone", addedOrder1.getCustomerContact());
        assertEquals("01/11/2023", addedOrder1.getOrderDate());
        assertEquals(Arrays.asList(1, 2), addedOrder1.getCakeIds());
    }

    @Test
    public void testRemoveOrder() {
        orderService.addOrder("Alice", "phone", "01/11/2023", Arrays.asList(1, 2));
        orderService.removeOrder(1);

        Optional<Order> removedOrder = orderRepository.findById("1");
        assertNull(removedOrder);
    }

    @Test
    public void testUpdateOrder() {
        orderService.addOrder("Alice", "phone", "01/11/2023", Arrays.asList(1, 2));

        orderService.updateOrder(1, "Alice-Updated", "fax", "05/11/2023", Arrays.asList(2, 3));
        Optional<Order> updatedOrder = orderRepository.findById("1");
        Order updatedOrder1 = updatedOrder.get();

        assertNotNull(updatedOrder);
        assertEquals("Alice-Updated", updatedOrder1.getCustomerName());
        assertEquals("fax", updatedOrder1.getCustomerContact());
        assertEquals("05/11/2023", updatedOrder1.getOrderDate());
        assertEquals(Arrays.asList(2, 3), updatedOrder1.getCakeIds());
    }

    @Test
    public void testGetOrderById() {
        orderService.addOrder("Alice", "phone", "01/11/2023", Arrays.asList(1, 2));
        Optional<Order> result = orderService.getOrderById(1);
        Order result1 = result.get();

        assertNotNull(result);
        assertEquals("Alice", result1.getCustomerName());
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
