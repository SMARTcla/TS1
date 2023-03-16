package cz.fel.cvut.ts1;

public class Calculator {
    public int add(int a, int b){
        return a + b;
    }
    public int substract(int a, int b){
        return a - b;
    }
    public int multiply(int a, int b){
        return a * b;
    }
    public String divide(int a, int b){
        if(b==0){
            return "Jmenovatel nemuze byt 0";
        }
        return Integer.toString(a / b);
    }
}