package Service;

import Domain.*;
import Filter.AbstractFilter;
import Repository.Base.FilteredRepository;
import Repository.Base.MemoryRepository;
import Threads.BirthdayCakeMergeSortTask;
import Threads.BirthdayCakeUpdaterExecutor;
import Threads.BirthdayCakeUpdaterThread;
import Validation.CakeValidator;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ForkJoinPool;

public class BirthdayCakeService {
    private final MemoryRepository<String, BirthdayCake> repository;
    private final MemoryRepository<String, Order> orderRepository;
    private final CakeValidator validator = new CakeValidator();
    private final ActionManager actionManager;

    public BirthdayCakeService() {
        repository = new MemoryRepository<>();
        orderRepository = new MemoryRepository<>();
        actionManager = new ActionManager();
    }

    public BirthdayCakeService(MemoryRepository<String, BirthdayCake> repository, MemoryRepository<String, Order> orders) {
        this.repository = repository;
        this.orderRepository = orders;
        this.actionManager = new ActionManager();
    }

    public void addBirthdayCake(int size, String flavour, int candles, double price) {
        String id = repository.getNextId();
        BirthdayCake cake = new BirthdayCake(id, size, flavour, candles, price);
        validator.validate(cake);
//        repository.add(cake.getId(), cake);
        ActionAdd<String, BirthdayCake> action = new ActionAdd<>(repository, cake, id);
        actionManager.executeAction(action);
    }

    public void removeBirthdayCake(int id) {
        Optional<BirthdayCake> cake = repository.findById(String.valueOf(id));

        if (cake.isEmpty()) {
            throw new RuntimeException("Cake not found");
        }

        BirthdayCake cakeToRemove = cake.get();

        List<Order> relatedOrders = orderRepository.getAll().stream()
                .filter(order -> order.hasCake(id))
                .toList();

        ActionRemoveCakeAndOrder action = new ActionRemoveCakeAndOrder(repository, orderRepository, cakeToRemove, relatedOrders);
        actionManager.executeAction(action);
    }

    public Optional<BirthdayCake> getBirthdayCakeById(int id) {
        return repository.findById(String.valueOf(id));
    }

    public void updateBirthdayCake(int id, int size, String flavour, int candles, double price) {
        Optional<BirthdayCake> cake = repository.findById(String.valueOf(id));

        if (cake.isEmpty()) {
            throw new RuntimeException("Cake not found");
        }

        BirthdayCake cakeToUpdate = cake.get();
        BirthdayCake newCake = new BirthdayCake(Integer.toString(id), size, flavour, candles, price);
        validator.validate(newCake);

        ActionUpdate<String, BirthdayCake> action = new ActionUpdate<>(repository, cakeToUpdate, newCake, String.valueOf(id));
        actionManager.executeAction(action);
    }

    public List<BirthdayCake> getAllBirthdayCakes() {
        return repository.getAll();
    }

    // Filter function
    public List<BirthdayCake> filterCakes(AbstractFilter<BirthdayCake> filter) {
        FilteredRepository<BirthdayCake> filteredRepo = new FilteredRepository<>(filter);
        repository.getAll().forEach(cake -> filteredRepo.add(cake.getId(), cake));

        return filteredRepo.getAll();
    }

    public List<BirthdayCake> filterBirthdayCakesByFlavour(String flavour) {
        // Filters birthday cakes by flavour.
        return filterCakes(cake -> cake.getFlavour().equals(flavour));
    }

    public List<BirthdayCake> filterBirthdayCakesByPrice(double price) {
        // Filters birthday cakes by price.
        return filterCakes(cake -> cake.getPrice() == price);
    }

    public void increasePriceByPercent(int cakeId, double percent) {
        // Increases the price of a cake, given its id.
        Optional<BirthdayCake> cake = getBirthdayCakeById(cakeId);
        if (cake.isEmpty())
            throw new RuntimeException("Invalid id.\n");

        cake.get().setPrice(cake.get().getPrice() + percent * cake.get().getPrice());
    }

    public void decreasePriceByPercent(int cakeId, double percent) {
        // Decreases the price of a cake, given its id.
        Optional<BirthdayCake> cake = getBirthdayCakeById(cakeId);
        if (cake.isEmpty())
            throw new RuntimeException("Invalid id.\n");

        cake.get().setPrice(cake.get().getPrice() - percent * cake.get().getPrice());
    }

    public int countCakesByFlavour(String flavour) {
        // Returns the number of Cakes of a given Flavour.
        return repository.getAll().stream()
                .filter(cake -> cake.getFlavour().equals(flavour))
                .toList()
                .size();
    }

    public double getAverageNumberOfCandles() {
        // Returns the Average Number of Candles.
        return repository.getAll().stream()
                .mapToInt(BirthdayCake::getCandles)
                .average()
                .orElse(0);
    }

    public double getAverageCakePrice() {
        // Returns the Average Price of a Cake.
        return repository.getAll().stream()
                .mapToDouble(BirthdayCake::getPrice)
                .average()
                .orElse(0);
    }

    public List<BirthdayCake> filterCakesLargerThan8() {
        return repository.getAll().stream()
                .filter(x -> x.getSize() > 8)
                .toList();
    }


    // 1. Traditional Threads for Bulk Update
    public void bulkUpdateCakesTraditional(int numberOfThreads, String newFlavour) {
        List<BirthdayCake> cakes = repository.getAll();
        int subsetSize = cakes.size() / numberOfThreads;

        for (int i = 0; i < numberOfThreads; i++) {
            int start = i * subsetSize;
            int end = (i == numberOfThreads - 1) ? cakes.size() : (i + 1) * subsetSize;
            List<BirthdayCake> subset = cakes.subList(start, end);

            // Start a new thread for each subset
            new BirthdayCakeUpdaterThread(subset, newFlavour).start();
        }
    }

    // 2. ExecutorService for Bulk Update
    public void bulkUpdateCakesExecutor(int numberOfThreads, String newFlavour) {
        List<BirthdayCake> cakes = repository.getAll();
        BirthdayCakeUpdaterExecutor executor = new BirthdayCakeUpdaterExecutor(numberOfThreads);

        // Update cakes using ExecutorService
        executor.updateCakes(cakes, newFlavour);
    }

    // 3. Fork/Join Framework for Sorting Cakes
    public List<BirthdayCake> sortCakesByIdMultiThreaded() {
        List<BirthdayCake> cakes = repository.getAll();
        ForkJoinPool pool = new ForkJoinPool();

        // Use merge sort task to sort the cakes
        BirthdayCakeMergeSortTask task = new BirthdayCakeMergeSortTask(cakes);
        return pool.invoke(task);
    }

    public void undo() {
        actionManager.undo();
    }

    public void redo() {
        actionManager.redo();
    }
}
