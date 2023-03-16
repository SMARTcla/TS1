import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.ParameterizedTest;
import org.junit.jupiter.api.CsvSource;

class CalculatorTest {
    public static void main(String[] args) {
        @ParameterizedTest
        @CsvSource({"1,2,3", "5,5,10"})
        public void test_calc_1(int a, int b, int result){
            Assertions.assertEquals(result, new Calculator().add(a, b));
        }
    }

}