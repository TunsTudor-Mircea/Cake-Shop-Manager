package Repository.EntityRepositories;

import Domain.Order;
import Repository.Base.MemoryRepository;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

public class OrderRepository extends MemoryRepository<String, Order> {
    public OrderRepository() {
        super();
    }

    public void addOrder(Order order) { add(getNextId(), order); }

    public void removeOrder(int orderId) {
        delete(Integer.toString(orderId));
    }

    public List<Order> getOrders() {
        return new ArrayList<>((Collection<? extends Order>) getAll());
    }

    public void updateOrder(Order order) {
        modify(order.getId(), order);
    }

    public Optional<Order> getOrderById(String id) {
        return findById(id);
    }
}
