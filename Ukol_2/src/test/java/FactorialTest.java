import cz.fel.cvut.ts1.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
public class FactorialTest {
    @Test
    public void factorialTest() {
        Assertions.assertEquals(2, Main.factorialRecursive(2));
        Assertions.assertEquals(720, Main.factorialIterative(6));
        Assertions.assertEquals(120, Main.factorialIterative(5));
        Assertions.assertEquals(2, Main.factorialIterative(5));
    }
}