package gx.bjtu.junit.api;

import gx.bjtu.junit.model.Calculator;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * junit 4 之前的测试写法
 */
public class Junit3Test extends TestCase {

    private Calculator cal;

    public void setUp() throws Exception {
        super.setUp();
        cal = new Calculator();
    }

    public void tearDown() throws Exception {
        super.tearDown();
    }

    public void testAdd() {
        int result = cal.add(1, 2);
        Assert.assertEquals(3, result);
    }

    public void testMinus() {
        int result = cal.minus(5, 2);
        Assert.assertEquals(3, result);
    }

    public void testMultiply() {
        int result = cal.multiply(4, 2);
        Assert.assertEquals(8, result);
    }

    public void testDivide() {
        int result = 0;
        try {
            result = cal.divide(10, 5);
        } catch (Exception e) {
            e.printStackTrace();
            // 我们期望result = cal.divide(10,5);正常执行；如果进入到catch中说明失败；
            // 所以我们加上fail。
            Assert.fail();// 如果这行没有执行。说明这部分正确。
        }
        Assert.assertEquals(2, result);
    }
}