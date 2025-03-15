package BirthdayCakeTest;

import Domain.BirthdayCake;
import Validation.ValidatorException;
import Validation.CakeValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CakeValidatorTest {

    private CakeValidator validator;
    private BirthdayCake validCake;

    @BeforeEach
    public void setUp() {
        validator = new CakeValidator();
        validCake = new BirthdayCake("1", 20, "chocolate", 10, 500); // Sample valid cake
    }

    // Test valid BirthdayCake object
    @Test
    public void testValidCake() {
        assertDoesNotThrow(() -> validator.validate(validCake));
    }

    // Test invalid ID (null ID)
    @Test
    public void testInvalidId_Null() {
        BirthdayCake cake = new BirthdayCake(null, 20, "chocolate", 10, 500);
        assertThrows(ValidatorException.class, () -> validator.validate(cake));
    }

    // Test invalid ID (negative or zero ID)
    @Test
    public void testInvalidId_NonPositive() {
        BirthdayCake cake = new BirthdayCake("0", 20, "chocolate", 10, 500);
        BirthdayCake finalCake = cake;
        assertThrows(ValidatorException.class, () -> validator.validate(finalCake));

        cake = new BirthdayCake("-5", 20, "chocolate", 10, 500);
        BirthdayCake finalCake1 = cake;
        assertThrows(ValidatorException.class, () -> validator.validate(finalCake1));
    }

    // Test invalid size (out of range)
    @Test
    public void testInvalidSize() {
        BirthdayCake cake = new BirthdayCake("1", -1, "chocolate", 10, 500);
        BirthdayCake finalCake = cake;
        assertThrows(ValidatorException.class, () -> validator.validate(finalCake));

        cake = new BirthdayCake("1", 101, "chocolate", 10, 500);
        BirthdayCake finalCake1 = cake;
        assertThrows(ValidatorException.class, () -> validator.validate(finalCake1));
    }

    // Test invalid flavour (null or unsupported flavour)
    @Test
    public void testInvalidFlavour_Null() {
        BirthdayCake cake = new BirthdayCake("1", 20, null, 10, 500);
        assertThrows(ValidatorException.class, () -> validator.validate(cake));
    }

    @Test
    public void testInvalidFlavour_Unsupported() {
        BirthdayCake cake = new BirthdayCake("1", 20, "blueberry", 10, 500);
        assertThrows(ValidatorException.class, () -> validator.validate(cake));
    }

    // Test invalid candle number (out of range)
    @Test
    public void testInvalidCandles() {
        BirthdayCake cake = new BirthdayCake("1", 20, "chocolate", 0, 500);
        BirthdayCake finalCake = cake;
        assertThrows(ValidatorException.class, () -> validator.validate(finalCake));

        cake = new BirthdayCake("1", 20, "chocolate", 123, 500);
        BirthdayCake finalCake1 = cake;
        assertThrows(ValidatorException.class, () -> validator.validate(finalCake1));
    }

    // Test invalid price (out of range)
    @Test
    public void testInvalidPrice() {
        BirthdayCake cake = new BirthdayCake("1", 20, "chocolate", 10, -1);
        BirthdayCake finalCake = cake;
        assertThrows(ValidatorException.class, () -> validator.validate(finalCake));

        cake = new BirthdayCake("1", 20, "chocolate", 10, 10001);
        BirthdayCake finalCake1 = cake;
        assertThrows(ValidatorException.class, () -> validator.validate(finalCake1));
    }
}
