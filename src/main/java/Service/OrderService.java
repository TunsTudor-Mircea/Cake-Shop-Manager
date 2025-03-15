package Service;

import Domain.*;
import Filter.AbstractFilter;
import Repository.Base.FilteredRepository;
import Repository.Base.MemoryRepository;
import Validation.OrderValidator;

import java.util.List;
import java.util.Optional;

public class OrderService {
    MemoryRepository<String, Order> orderRepository;
    BirthdayCakeService cakeService;
    private final OrderValidator validator;
    private final ActionManager actionManager;

    public OrderService() {
        orderRepository = new MemoryRepository<>();
        cakeService = new BirthdayCakeService();
        validator = new OrderValidator(cakeService);
        actionManager = new ActionManager();
    }

    public OrderService(MemoryRepository<String, Order> orderRepository, BirthdayCakeService cakeService) {
        this.orderRepository = orderRepository;
        this.cakeService = cakeService;
        validator = new OrderValidator(cakeService);
        this.actionManager = new ActionManager();
    }

    public void addOrder(String customerName, String customerContact, String orderDate, List<Integer> cakeIds) {
        String id = orderRepository.getNextId();
        Order order = new Order(id, customerName, customerContact, orderDate, cakeIds);
        validator.validate(order);

        ActionAdd<String, Order> action = new ActionAdd<>(orderRepository, order, id);
        actionManager.executeAction(action);
    }

    public void removeOrder(int orderId) {
        Optional<Order> order = orderRepository.findById(String.valueOf(orderId));
        if (order.isEmpty()) {
            throw new RuntimeException("Order not found");
        }

        Order orderToRemove = order.get();
        ActionRemove<String, Order> action = new ActionRemove<>(orderRepository, orderToRemove, String.valueOf(orderId));
        actionManager.executeAction(action);
    }

    public void updateOrder(int orderId, String newCustomerName, String newCustomerContact, String newDate, List<Integer> newCakeIds) {
        Optional<Order> order = orderRepository.findById(String.valueOf(orderId));
        if (order.isEmpty()) {
            throw new RuntimeException("Order not found");
        }
        Order orderToUpdate = order.get();
        Order newOrder = new Order(Integer.toString(orderId), newCustomerName, newCustomerContact, newDate, newCakeIds);
        validator.validate(newOrder);

        ActionUpdate<String, Order> action = new ActionUpdate<>(orderRepository, orderToUpdate, newOrder, String.valueOf(orderId));
        actionManager.executeAction(action);
    }

    public Optional<Order> getOrderById(int orderId) {
        return orderRepository.findById(Integer.toString(orderId));
    }

    public List<Order> getOrders() {
        return orderRepository.getAll();
    }

    public List<BirthdayCake> getCakes() {
        return cakeService.getAllBirthdayCakes();
    }

    public List<Order> filterOrders(AbstractFilter<Order> filter) {
        FilteredRepository<Order> filteredRepo = new FilteredRepository<>(filter);
        orderRepository.getAll().forEach(order -> filteredRepo.add(order.getId(), order));

        return filteredRepo.getAll();
    }

    public List<Order> filterOrdersByDate(String date) {
        // Filters orders by date.
        return filterOrders(order -> order.getOrderDate().equals(date));
    }

    public List<Order> filterOrdersByCustomerName(String customerName) {
        // Filters orders by customer name.
        return filterOrders(order -> order.getCustomerName().equals(customerName));
    }

    public double getEarningsFromDate(String date) {
        // Returns the total earnings from a given date.
        return orderRepository.getAll().stream()
                .filter(order -> order.getOrderDate().equals(date))
                .flatMap(order -> order.getCakeIds().stream())
                .map(cakeService::getBirthdayCakeById)
                .map(Optional::orElseThrow)
                .mapToDouble(BirthdayCake::getPrice)
                .sum();
    }

    public double getTotalEarnings() {
        // Returns the total earnings.
        return orderRepository.getAll().stream()
                .flatMap(order -> order.getCakeIds().stream())
                .map(cakeService::getBirthdayCakeById)
                .map(Optional::orElseThrow)
                .mapToDouble(BirthdayCake::getPrice)
                .sum();
    }

    public int getNumberOfOrdersByCustomer(String customer) {
        // Returns the number of orders placed by a given Customer.
        return orderRepository.getAll().stream()
                .filter(order -> order.getCustomerName().equals(customer))
                .toList().
                size();
    }

    public void removeInvalidOrders(int cakeId) {
        // Removes the orders that have an invalid cake ID.
        for (Order order : orderRepository.getAll()) {
            if (order.hasCake(cakeId))
                orderRepository.delete(order.getId());
        }
    }

    public double getAverageOrderPrice() {
        // Returns the Average Order Price.
        return orderRepository.getAll().stream()
                .mapToDouble(order -> order.getCakeIds().stream()
                        .map(cakeService::getBirthdayCakeById)
                        .map(Optional::orElseThrow)
                        .mapToDouble(BirthdayCake::getPrice)
                        .sum())
                .average()
                .orElse(0.0);
    }

    public void undo() {
        actionManager.undo();
    }

    public void redo() {
        actionManager.redo();
    }
}
