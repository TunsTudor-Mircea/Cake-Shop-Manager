package BirthdayCakeTest;

import Domain.BirthdayCake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BirthdayCakeTest {

    private BirthdayCake cake;

    @BeforeEach
    void setUp() {
        cake = new BirthdayCake("cake1", 8, "chocolate", 5, 20.0);
    }

    @Test
    void testGetId() {
        assertEquals("cake1", cake.getId(), "ID should be 'cake1'");
    }

    @Test
    void testSetId() {
        cake.setId("newCakeId");
        assertEquals("newCakeId", cake.getId(), "ID should be 'newCakeId'");
    }

    @Test
    void testGetSize() {
        assertEquals(8, cake.getSize(), "Size should be 8");
    }

    @Test
    void testSetSize() {
        cake.setSize(10);
        assertEquals(10, cake.getSize(), "Size should be updated to 10");
    }

    @Test
    void testGetFlavour() {
        assertEquals("chocolate", cake.getFlavour(), "Flavour should be 'chocolate'");
    }

    @Test
    void testSetFlavour() {
        cake.setFlavour("vanilla");
        assertEquals("vanilla", cake.getFlavour(), "Flavour should be updated to 'vanilla'");
    }

    @Test
    void testGetCandles() {
        assertEquals(5, cake.getCandles(), "Candles should be 5");
    }

    @Test
    void testSetCandles() {
        cake.setCandles(7);
        assertEquals(7, cake.getCandles(), "Candles should be updated to 7");
    }

    @Test
    void testGetPrice() {
        assertEquals(20.0, cake.getPrice(), "Price should be 20.0");
    }

    @Test
    void testSetPrice() {
        cake.setPrice(30.0);
        assertEquals(30.0, cake.getPrice(), "Price should be updated to 30.0");
    }

    @Test
    void testSetPriceWithNegativeValue() {
        cake.setPrice(-10.0);
        assertEquals(0.0, cake.getPrice(), "Price should be set to 0.0 when given a negative value");
    }

    @Test
    void testToString() {
        String expected = "Birthday Cake cake1; size: 8; chocolate flavour; 5 candles; price: 20.0";
        assertEquals(expected, cake.toString(), "toString output is incorrect");
    }
}
