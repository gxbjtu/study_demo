package gx.bjtu.junit.model;

/**
 * Created by jean on 16-3-24.
 */
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public int minus(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public int divide(int a, int b) throws Exception {
        if (0 == b) {
            throw new Exception("除数不能为零");
        }
        return a / b;
    }
}
