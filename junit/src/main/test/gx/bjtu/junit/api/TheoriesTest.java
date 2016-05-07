package gx.bjtu.junit.api;

import gx.bjtu.junit.iface.Between;
import gx.bjtu.junit.model.NameSupplier;
import org.junit.experimental.theories.*;
import org.junit.experimental.theories.suppliers.TestedOn;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jean on 16-3-27.
 */
@RunWith(Theories.class)
public class TheoriesTest {

    /**
     * http://my.oschina.net/pangyangyang/blog/144495
     * http://www.cnblogs.com/wavky/p/JUnit4_Theory.html
     * <p/>
     * Theories继承自BlockJUnit4ClassRunner，提供了除Parameterized之外的另一种参数测试解决方案——似乎更强大。
     * Theories不再需要使用带有参数的Constructor而是接受有参的测试方法，修饰的注解也从@Test变成了@Theory，
     * 而参数的提供则变成了使用@DataPoint或者@Datapoints来修饰的变量，两者的唯一不同是前者代表一个数据后者代表一组数据。
     * Theories会尝试所有类型匹配的参数作为测试方法的入参（有点排列组合的意思）。
     * <p/>
     * 除此以外Theories还可以支持自定义数据提供的方式，需要继承JUnit的ParameterSupplier类。
     */

    @DataPoints
    public static String[] names = {"Tony", "Jim"};
    @DataPoints
    public static int[] ages = {10, 20};

    @Theory
    public void testMethod(String name, int age) {
        System.out.println(String.format("%s's age is %s", name, age));
    }

    // JUnit中自带一个默认的 Parameter Supplier 实现：@TestedOn(ints = int[])
    @Theory
    public void testMethod1(String name, @TestedOn(ints = {0, 1}) int age) {
        System.out.println(String.format("%s's age is %s", name, age));
    }

    // 自定义Parameter Supplier 实现，注解传参数
    @Theory
    public void testMethod2(String name, @Between(last = 3) int age) {
        System.out.println(String.format("%s's age is %s", name, age));
    }

    // 自定义Parameter Supplier 实现，固定的实参数据集
    @Theory
    public void testMethod3(@ParametersSuppliedBy(NameSupplier.class) String name, @TestedOn(ints = {0, -1}) int age) {
        System.out.println(String.format("%s's age is %s", name, age));
    }
}

