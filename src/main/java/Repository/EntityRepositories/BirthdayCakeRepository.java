package Repository.EntityRepositories;

import Domain.BirthdayCake;
import Repository.Base.MemoryRepository;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

public class BirthdayCakeRepository extends MemoryRepository<String, BirthdayCake> {
    public BirthdayCakeRepository() {
        super();
    }

    public void addBirthdayCake(BirthdayCake birthdayCake) {
        add(getNextId(), birthdayCake);
    }

    public void removeBirthdayCake(int id) {
        delete(Integer.toString(id));
    }

    public List<BirthdayCake> getBirthdayCakes() {
        return new ArrayList<>(getAll());
    }

    public void updateBirthdayCake(BirthdayCake birthdayCake) {
        modify(birthdayCake.getId(), birthdayCake);
    }

    public Optional<BirthdayCake> getBirthdayCakeById(String id) {
        return findById(id);
    }

    public void generateBirthdayCakes(int n) {
        String[] FLAVOURS = {"chocolate", "vanilla", "strawberry", "lemon", "red velvet", "caramel", "coconut", "coffee"};
        Random rand = new Random();

        // Find the current maximum ID (as an integer)
        int idInt = elements.values().stream()
                .map(BirthdayCake::getId)
                .map(Integer::parseInt)
                .max(Integer::compare)
                .orElse(0);

        // Generate 'n' BirthdayCake entities
        for (int i = idInt + 1; i <= idInt + n; i++) {
            String id = String.valueOf(i);
            int size = rand.nextInt(5) + 6; // Random size between 6 and 10
            String flavour = FLAVOURS[rand.nextInt(FLAVOURS.length)];
            int candles = rand.nextInt(11) + 5; // Random candles between 5 and 15
            double price = Math.round((10 + rand.nextDouble() * 40) * 100.0) / 100.0; // Random price between 10.0 and 50.0 (rounded)

            // Create and add the BirthdayCake entity
            add(id, new BirthdayCake(id, size, flavour, candles, price));
        }
    }
}
