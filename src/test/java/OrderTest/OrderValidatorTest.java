package OrderTest;

import Domain.Order;
import Service.BirthdayCakeService;
import Validation.OrderValidator;
import Validation.ValidatorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderValidatorTest {
    private OrderValidator validator;

    @BeforeEach
    public void setUp() {
        BirthdayCakeService cakeService = new BirthdayCakeService();
        cakeService.addBirthdayCake(12, "chocolate", 10, 20.0);
        cakeService.addBirthdayCake(15, "vanilla", 12, 30.0);

        validator = new OrderValidator(cakeService);
    }

    @Test
    public void testValidOrder() {
        Order order = new Order("1", "John-Doe", "phone", "01/01/2023", Arrays.asList(1, 2));

        assertDoesNotThrow(() -> validator.validate(order));
    }

    @Test
    public void testInvalidOrderId() {
        Order order = new Order("-1", "John-Doe", "phone", "01/01/2023", List.of(1));

        ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate(order));
        assertEquals("Invalid order ID.\n", exception.getMessage());
    }

    @Test
    public void testInvalidCustomerName() {
        Order order = new Order("1", "john", "phone", "01/01/2023", List.of(1));

        ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate(order));
        assertEquals("Invalid customer name.\n", exception.getMessage());
    }

    @Test
    public void testInvalidCustomerContact() {
        Order order = new Order("1", "John-Doe", "unknown", "01/01/2023", List.of(1));

        ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate(order));
        assertEquals("Invalid customer contact.\n", exception.getMessage());
    }

    @Test
    public void testInvalidOrderDate() {
        Order order = new Order("1", "John-Doe", "phone", "2023-01-01", List.of(1));  // wrong date format

        ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate(order));
        assertEquals("Invalid order date.\n", exception.getMessage());
    }

    @Test
    public void testNullOrderDate() {
        Order order = new Order("1", "John-Doe", "phone", null, List.of(1));

        ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate(order));
        assertEquals("Invalid order date.\n", exception.getMessage());
    }

    @Test
    public void testInvalidCakeIds() {
        Order order = new Order("1", "John-Doe", "phone", "01/01/2023", List.of(999));

        ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate(order));
        assertEquals("Invalid cake ID's.\n", exception.getMessage());
    }

    @Test
    public void testEmptyCakeIds() {
        Order order = new Order("1", "John-Doe", "phone", "01/01/2023", Collections.emptyList());

        ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate(order));
        assertEquals("Invalid cake ID's.\n", exception.getMessage());
    }

    @Test
    public void testValidCustomerNameWithHyphen() {
        Order order = new Order("1", "Alice-Smith", "phone", "01/01/2023", List.of(1));

        assertDoesNotThrow(() -> validator.validate(order));
    }

    @Test
    public void testInvalidCustomerNameWithNumbers() {
        Order order = new Order("1", "Alice123", "phone", "01/01/2023", List.of(1));

        ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate(order));
        assertEquals("Invalid customer name.\n", exception.getMessage());
    }

    @Test
    public void testInvalidCustomerNameWithNull() {
        Order order = new Order("1", null, "phone", "01/01/2023", List.of(1));

        ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate(order));
        assertEquals("Invalid customer name.\n", exception.getMessage());
    }

    @Test
    public void testValidCustomerNameWithoutHyphen() {
        Order order = new Order("1", "Alice", "phone", "01/01/2023", List.of(1));

        assertDoesNotThrow(() -> validator.validate(order));
    }

    @Test
    public void testInvalidOrderDateNonExistentDate() {
        Order order = new Order("1", "John-Doe", "phone", "32/01/2023", List.of(1));

        ValidatorException exception = assertThrows(ValidatorException.class, () -> validator.validate(order));
        assertEquals("Invalid order date.\n", exception.getMessage());
    }
}
