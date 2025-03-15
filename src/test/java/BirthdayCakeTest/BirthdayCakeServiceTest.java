package BirthdayCakeTest;

import Domain.BirthdayCake;
import Repository.Base.MemoryRepository;
import Service.BirthdayCakeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BirthdayCakeServiceTest {

    private BirthdayCakeService service;

    @Test
    void testDefaultConstructor() {
        BirthdayCakeService service = new BirthdayCakeService();

        assertNotNull(service.getAllBirthdayCakes(), "Repository should be initialized and not null.");
        assertTrue(service.getAllBirthdayCakes().isEmpty(), "Repository should be empty upon initialization.");
    }

    @BeforeEach
    void setUp() {
        service = new BirthdayCakeService(new MemoryRepository<>());
    }

    @Test
    void testAddBirthdayCake() {
        service.addBirthdayCake(8, "chocolate", 5, 20.0);

        List<BirthdayCake> allCakes = service.getAllBirthdayCakes();
        assertEquals(1, allCakes.size(), "Repository should contain 1 cake after adding one.");
        assertEquals("chocolate", allCakes.get(0).getFlavour(), "Added cake should have chocolate flavour.");
    }

    @Test
    void testRemoveBirthdayCake() {
        service.addBirthdayCake(8, "chocolate", 5, 20.0);
        BirthdayCake cake = service.getAllBirthdayCakes().get(0);

        service.removeBirthdayCake(Integer.parseInt(cake.getId()));

        assertTrue(service.getAllBirthdayCakes().isEmpty(), "Repository should be empty after removing the cake.");
    }

    @Test
    void testGetBirthdayCakeById() {
        service.addBirthdayCake(8, "chocolate", 5, 20.0);
        BirthdayCake cake = service.getAllBirthdayCakes().get(0);

        BirthdayCake retrievedCake = service.getBirthdayCakeById(Integer.parseInt(cake.getId()));
        assertNotNull(retrievedCake, "Retrieved cake should not be null.");
        assertEquals(cake, retrievedCake, "Retrieved cake should match the added cake.");
    }

    @Test
    void testUpdateBirthdayCake() {
        service.addBirthdayCake(8, "chocolate", 5, 20.0);
        BirthdayCake cake = service.getAllBirthdayCakes().get(0);
        int cakeId = Integer.parseInt(cake.getId());

        service.updateBirthdayCake(cakeId, 10, "vanilla", 6, 25.0);

        BirthdayCake updatedCake = service.getBirthdayCakeById(cakeId);
        assertEquals(10, updatedCake.getSize(), "Updated cake size should be 10.");
        assertEquals("vanilla", updatedCake.getFlavour(), "Updated cake flavour should be vanilla.");
        assertEquals(6, updatedCake.getCandles(), "Updated cake candles should be 6.");
        assertEquals(25.0, updatedCake.getPrice(), "Updated cake price should be 25.0.");
    }

    @Test
    void testGetAllBirthdayCakes() {
        service.addBirthdayCake(8, "chocolate", 5, 20.0);
        service.addBirthdayCake(10, "vanilla", 3, 25.0);

        List<BirthdayCake> allCakes = service.getAllBirthdayCakes();
        assertEquals(2, allCakes.size(), "Repository should contain 2 cakes.");
    }

    @Test
    void testFilterBirthdayCakesByFlavour() {
        service.addBirthdayCake(8, "chocolate", 5, 20.0);
        service.addBirthdayCake(10, "vanilla", 3, 25.0);

        List<BirthdayCake> filteredCakes = service.filterBirthdayCakesByFlavour("chocolate");
        assertEquals(1, filteredCakes.size(), "Only one cake should match the chocolate flavour.");
        assertEquals("chocolate", filteredCakes.get(0).getFlavour(), "Filtered cake should have chocolate flavour.");
    }

    @Test
    void testFilterBirthdayCakesByPrice() {
        service.addBirthdayCake(8, "chocolate", 5, 20.0);
        service.addBirthdayCake(10, "vanilla", 3, 25.0);

        List<BirthdayCake> filteredCakes = service.filterBirthdayCakesByPrice(25.0);
        assertEquals(1, filteredCakes.size(), "Only one cake should match the price filter.");
        assertEquals("vanilla", filteredCakes.get(0).getFlavour(), "Filtered cake should be the vanilla cake.");
    }

    @Test
    void testIncreasePriceByPercent() {
        service.addBirthdayCake(8, "chocolate", 5, 20.0);
        BirthdayCake cake = service.getAllBirthdayCakes().get(0);
        int cakeId = Integer.parseInt(cake.getId());

        service.increasePriceByPercent(cakeId, 0.10);

        assertEquals(22.0, cake.getPrice(), 0.01, "Price should be increased by 10%.");
    }

    @Test
    void testDecreasePriceByPercent() {
        service.addBirthdayCake(8, "chocolate", 5, 20.0);
        BirthdayCake cake = service.getAllBirthdayCakes().get(0);
        int cakeId = Integer.parseInt(cake.getId());

        service.decreasePriceByPercent(cakeId, 0.10);

        assertEquals(18.0, cake.getPrice(), 0.01, "Price should be decreased by 10%.");
    }
}
