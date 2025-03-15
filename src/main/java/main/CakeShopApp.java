package main;

import Domain.BirthdayCake;
import Domain.Order;
import Repository.Base.MemoryRepository;
import Repository.Base.RepositoryFactory;
import Service.BirthdayCakeService;
import Service.OrderService;
import UI.UserInterface;

public class CakeShopApp {
    public static void main(String[] args) {
        try {
            RepositoryFactory.Repositories repositories = RepositoryFactory.createRepositories();
            MemoryRepository<String, BirthdayCake> cakeRepository = repositories.cakeRepository;
            MemoryRepository<String, Order> orderRepository = repositories.orderRepository;

            BirthdayCakeService cakeService = new BirthdayCakeService(cakeRepository, orderRepository);
            OrderService orderService = new OrderService(orderRepository, cakeService);

            UserInterface userInterface = new UserInterface(cakeService, orderService);

            userInterface.show();
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
