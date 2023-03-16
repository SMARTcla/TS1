import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.*;
import cz.fel.cvut.ts1.Calculator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;

public class CalculatorTest {
    @ParameterizedTest(name="{0} plus {1} shoud be equal {2}")
    @CsvSource({"1,2,3", "5,5,10", "-5,5,0"})
    public void test_calc_1(int a, int b, int result){
        Assertions.assertEquals(result, new Calculator().add(a, b));
    }


    @ParameterizedTest(name="{0} minus {1} shoud be equal {2}")
    @CsvSource({"1,2,-1", "5,5,0", "5,-5,10"})
    public void test_calc_2(int a, int b, int result){
        Assertions.assertEquals(result, new Calculator().substract(a, b));
    }


    @ParameterizedTest(name="{0} multiply {1} shoud be equal {2}")
    @CsvSource({"1,2,2", "12,12,144", "0,-5,0"})
    public void test_calc_3(int a, int b, int result){
        Assertions.assertEquals(result, new Calculator().multiply(a, b));
    }


    @ParameterizedTest(name="{0} divide {1} shoud be equal {2}")
    @CsvSource({"2,1,2", "12,12,1", "3,0,Jmenovatel nemuze byt 0", "60, 5, 12"})
    public void test_calc_4(int a, int b, String result){
        Assertions.assertEquals(result, new Calculator().divide(a, b));
    }
}