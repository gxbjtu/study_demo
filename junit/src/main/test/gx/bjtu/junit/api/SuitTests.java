package gx.bjtu.junit.api;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by jean on 16-3-24.
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(Junit4BasicTest.class)
public class SuitTests {

    /**
     * Runner：Runner是一个抽象类，是JUnit的核心组成部分。用于运行测试和通知Notifier运行的结果。JUnit使用@RunWith注解标注选用的Runner，由此实现不同测试行为。
     * BlockJUnit4ClassRunner：这个是JUnit的默认Runner，平时我们编写的JUnit不添加@RunWith注解时使用的都是这个Runner。
     * Suit：Suit就是个Runner！用来执行分布在多个类中的测试用例.
     * 写法： @SuiteClasses({BasicBookTest.class, ParameterBookTest.class})
     */
}
