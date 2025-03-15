package OrderTest;

import Domain.Order;
import Repository.EntityRepositories.OrderRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderRepositoryTest {

    private OrderRepository orderRepository;
    private Order order1;
    private Order order2;

    @BeforeEach
    public void setUp() {
        orderRepository = new OrderRepository();

        order1 = new Order("1", "Alice", "123-456-7890", "2023-11-01", Arrays.asList(101, 102));
        order2 = new Order("2", "Bob", "987-654-3210", "2023-11-02", Arrays.asList(103, 104));

        orderRepository.addOrder(order1);
        orderRepository.addOrder(order2);
    }

    @Test
    public void testAddOrder() {
        Order order3 = new Order("3", "Charlie", "555-555-5555", "2023-11-03", Arrays.asList(105));
        orderRepository.addOrder(order3);

        assertEquals(3, orderRepository.getOrders().size());
        assertEquals(order3, orderRepository.getOrderById(order3.getOrderId()));
    }

    @Test
    public void testRemoveOrder() {
        orderRepository.removeOrder(Integer.parseInt(order1.getOrderId()));

        assertEquals(1, orderRepository.getOrders().size());
        assertNull(orderRepository.getOrderById(order1.getOrderId()));
    }

    @Test
    public void testRemoveOrder_InvalidId() {
        assertThrows(RuntimeException.class, () -> orderRepository.removeOrder(999));
    }

    @Test
    public void testGetOrders() {
        List<Order> orders = orderRepository.getOrders();
        assertEquals(2, orders.size());
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
    }

    @Test
    public void testUpdateOrder() {
        order1.setCustomerName("Alice Updated");
        orderRepository.updateOrder(order1);

        Order updatedOrder = orderRepository.getOrderById(order1.getOrderId());
        assertEquals("Alice Updated", updatedOrder.getCustomerName());
    }

    @Test
    public void testUpdateOrder_InvalidId() {
        Order nonExistentOrder = new Order("999", "Nonexistent", "000-000-0000", "2023-12-01", Arrays.asList(106));
        assertThrows(RuntimeException.class, () -> orderRepository.updateOrder(nonExistentOrder));
    }

    @Test
    public void testGetOrderById() {
        assertEquals(order1, orderRepository.getOrderById(order1.getOrderId()));
        assertNull(orderRepository.getOrderById("999"));
    }

    @Test
    public void testGetNextId() {
        String nextId = orderRepository.getNextId();
        assertEquals("3", nextId);

        Order order3 = new Order(nextId, "Charlie", "555-555-5555", "2023-11-03", Arrays.asList(105));
        orderRepository.addOrder(order3);

        assertEquals("4", orderRepository.getNextId());
    }
}
