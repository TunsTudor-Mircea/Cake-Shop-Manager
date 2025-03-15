package Domain;

import Repository.Base.IRepository;

import java.util.ArrayList;
import java.util.List;

public class ActionRemoveCakeAndOrder implements IAction<String, BirthdayCake> {
    private final IRepository<String, BirthdayCake> cakeRepo;
    private final IRepository<String, Order> orderRepo;
    private final BirthdayCake birthdayCake;
    private final List<Order> orders;

    public ActionRemoveCakeAndOrder(IRepository<String, BirthdayCake> cakeRepo, IRepository<String, Order> orderRepo, BirthdayCake birthdayCake, List<Order> orders) {
        this.cakeRepo = cakeRepo;
        this.orderRepo = orderRepo;
        this.birthdayCake = birthdayCake;
        this.orders = orders;
    }

    @Override
    public void executeUndo() {
        cakeRepo.add(birthdayCake.getId(), birthdayCake);

        for (Order order : orders) {
            orderRepo.add(order.getId(), order);
        }
    }

    @Override
    public void executeRedo() {
        cakeRepo.delete(birthdayCake.getId());

        for (Order order : orders) {
            orderRepo.delete(order.getId());
        }
    }
}
