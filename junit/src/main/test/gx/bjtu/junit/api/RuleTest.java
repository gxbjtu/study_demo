package gx.bjtu.junit.api;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.ExternalResource;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.Timeout;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by jean on 16-3-27.
 */
public class RuleTest {

    /**
     * JUnit4中包含两个注解@Rule和@ClassRule用于修饰Field或返回Rule的 Method，Rule是一组实现了TestRule接口的共享类，提供了验证、监视TestCase和外部资源管理等能力。
     * <p/>
     * Verifier: 验证测试执行结果的正确性。
     * ErrorCollector: 收集测试方法中出现的错误信息，测试不会中断，如果有错误发生测试结束后会标记失败。
     * ExpectedException: 提供灵活的异常验证功能。
     * Timeout: 用于测试超时的Rule。
     * ExternalResource: 外部资源管理。
     * TemporaryFolder: 在JUnit的测试执行前后，创建和删除新的临时目录。
     * TestWatcher: 监视测试方法生命周期的各个阶段。
     * TestName: 在测试方法执行过程中提供获取测试名字的能力。
     */

    @Rule
    public ExpectedException exp = ExpectedException.none();

    @Test
    public void expectException() {
        exp.expect(IndexOutOfBoundsException.class);
        throw new IndexOutOfBoundsException("Exception method.");
    }

    @Test
    public void expectMessage() {
        exp.expectMessage("Hello World");
        throw new RuntimeException("Hello World will throw exception.");
    }

    @Test
    public void expectCourse() {
        exp.expectCause(new BaseMatcher<IllegalArgumentException>() {
            public boolean matches(Object item) {
                return item instanceof IllegalArgumentException;
            }

            public void describeTo(Description description) {
                description.appendText("Expected Cause Error.");
            }
        });

        Throwable cause = new IllegalArgumentException("Cause Test.");
        throw new RuntimeException(cause);
    }

    @ClassRule // Timeout用@ClassRule注解会有惊喜，因为作用域变成了Class所以它可以控制整个Test Class执行的耗时，也就是累计时间
    public static Timeout to = new Timeout(10000);

    @ClassRule
    public static ExternalResource external = new ExternalResource() {
        @Override
        protected void before() throws Throwable {
            System.out.println("Perparing test data.");
        }

        @Override
        protected void after() {
            System.out.println("Cleaning test data.");
        }
    };

    @Test
    public void method1() {
        System.out.println("Test Method first executing...");
    }

    @Test
    public void method2() {
        System.out.println("Test Method second executing...");
    }

    @Test
    public void method3() {
        System.out.println("Test Method thrid executing...");
    }


    @ClassRule
    public static TemporaryFolder folderCreater = new TemporaryFolder();

    @BeforeClass
    public static void before() throws IOException {
        folderCreater.create();
        System.out.println("folder create");
    }

    public void createFolder() throws IOException {
        System.out.println("create folder...");
        File folder = folderCreater.newFolder();
        File file = folderCreater.newFile("fileName.txt");
        //do something...
    }

}
