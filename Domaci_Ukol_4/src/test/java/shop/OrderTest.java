package shop;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest {
    ShoppingCart cart;

    @BeforeEach
    public void initTest() {
        cart = new ShoppingCart();
    }

    @Test
    public void constructorWithoutStateTest() {
        int expectedState = 0;
        String expectedCustomerName = "Kononenko Mike";
        String expectedCustomerAddress = "Praha 3";

        Order order = new Order(cart, expectedCustomerName, expectedCustomerAddress);

        assertEquals(expectedState, order.getState());
        assertEquals(expectedCustomerName, order.getCustomerName());
        assertEquals(expectedCustomerAddress, order.getCustomerAddress());
    }

    @Test
    public void constructorWithStateTest() {
        int expectedState = 2;
        String expectedCustomerName = "Kononenko Mike";
        String expectedCustomerAddress = "Praha 3";
        Order order = new Order(cart, expectedCustomerName, expectedCustomerAddress, expectedState);

        assertEquals(expectedState, order.getState());
        assertEquals(expectedCustomerName, order.getCustomerName());
        assertEquals(expectedCustomerAddress, order.getCustomerAddress());
    }

    @Test
    public void constructorWithCartNullTest() {
        assertThrows(NullPointerException.class, () -> new Order(null, "Kononenko Mike", "Praha 3"));
    }
}
