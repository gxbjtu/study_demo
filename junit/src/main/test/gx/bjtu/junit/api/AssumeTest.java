package gx.bjtu.junit.api;

import org.junit.Assume;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

/**
 * Created by jean on 16-3-27.
 */
@RunWith(Theories.class)
public class AssumeTest {

    @DataPoints
    public static String[] names = {"LiLei", "HanMeiMei"};

    @DataPoints
    public static int[] ages = {10, -2, 12};

    @Theory
    public void printAge(String name, int age) {
        Assume.assumeTrue(age > 0);
        System.out.println(String.format("%s's Name is %s.", name, age));
    }


    /**
     * Assert是JUnit提供的断言类，用于常用的测试结果验证。提供的功能和方法都比较简单实用，这里只用列表简单介绍：
     * AssertTrue、AssertFalse：结果的true、false。
     * AssertThat：使用Matcher做自定义的校验。
     * AssertEquals、AssertNotEquals：判断两个对象是否相等。
     * AssertNull、AssertNotNull：判断对象是否为空。
     * AssertSame：判断两个对象是否为同一个，不同于equals这里是使用“==”判断。
     * AssertArrayEquals：判断两个数组是否相等。
     */
}
