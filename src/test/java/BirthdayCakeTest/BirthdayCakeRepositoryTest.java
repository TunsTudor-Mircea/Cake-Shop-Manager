package BirthdayCakeTest;

import Domain.BirthdayCake;
import Repository.EntityRepositories.BirthdayCakeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class BirthdayCakeRepositoryTest {

    private BirthdayCakeRepository repository;
    private BirthdayCake cake1;
    private BirthdayCake cake2;

    @BeforeEach
    void setUp() {
        repository = new BirthdayCakeRepository();
        cake1 = new BirthdayCake("1", 8, "chocolate", 5, 20.0);
        cake2 = new BirthdayCake("2", 10, "vanilla", 3, 25.0);

        repository.addBirthdayCake(cake1);
        repository.addBirthdayCake(cake2);
    }

    @Test
    void testAddBirthdayCake() {
        BirthdayCake cake3 = new BirthdayCake("3", 12, "strawberry", 4, 30.0);
        repository.addBirthdayCake(cake3);

        assertEquals(3, repository.getBirthdayCakes().size(), "Repository should contain 3 cakes after adding one more.");
        assertEquals(cake3, repository.getBirthdayCakeById(cake3.getId()), "Added cake should be retrievable by ID.");
    }

    @Test
    void testRemoveBirthdayCake() {
        repository.removeBirthdayCake(1);

        assertEquals(1, repository.getBirthdayCakes().size(), "Repository should contain 1 cake after removing one.");
        assertNull(repository.getBirthdayCakeById("1"), "Removed cake should not be retrievable by ID.");
    }

    @Test
    void testGetBirthdayCakes() {
        assertEquals(2, repository.getBirthdayCakes().size(), "Repository should initially contain 2 cakes.");
        assertTrue(repository.getBirthdayCakes().contains(cake1), "Repository should contain cake1.");
        assertTrue(repository.getBirthdayCakes().contains(cake2), "Repository should contain cake2.");
    }

    @Test
    void testUpdateBirthdayCake() {
        cake1.setPrice(22.0);
        repository.updateBirthdayCake(cake1);

        Optional<BirthdayCake> updatedCake = repository.getBirthdayCakeById(cake1.getId());
        assertNotNull(updatedCake, "Updated cake should be retrievable by ID.");
    }

    @Test
    void testGetBirthdayCakeById() {
        Optional<BirthdayCake> retrievedCake = repository.getBirthdayCakeById("1");

        assertNotNull(retrievedCake, "Cake with ID '1' should be retrievable.");
        assertEquals(cake1, retrievedCake, "Retrieved cake should be cake1.");
    }
}
