package gx.bjtu.junit.api;

import gx.bjtu.junit.iface.BehaviorTest;
import gx.bjtu.junit.model.Book;
import org.junit.*;
import org.junit.experimental.categories.Category;

import static org.junit.Assert.assertEquals;

/**
 * junit 4 之后支持的测试
 */
public class Junit4BasicTest {

    Book book = null;

    @BeforeClass
    public static void before() {
        System.out.println("beforeclass");
    }

    @AfterClass
    public static void after() {
        System.out.println("afterclass");
    }

    @Before
    public void setUp() throws Exception {
        System.out.println("测试开始！");
        book = new Book();
        System.out.println("book对象被初始化！");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("book对象将被清理！");
        book = null;
        System.out.println("测试结束！");
    }

    //测试正确
    @Test
    public void caseId() {
        book.setId("001"); //设置id属性的值为001
        //使用Assert查看id属性的值是否为001
        assertEquals("001", book.getId());
        System.out.println("id属性被测试！");
    }

    //测试错误
    @Test
    public void caseName() {
        book.setName("ASP"); //设置name属性的值为ASP
        //使用Assert查看name属性的值是否为JSP，这是个必然出现错误的测试
        assertEquals("JSP", book.getName());
        System.out.println("name属性被测试！");
    }

    //忽略
    @Ignore
    @Test
    public void caseIdName() {
        book.setId("123");
        book.setName("hello");

        assertEquals("123", book.getId());
        assertEquals("hello", book.getName());
        System.out.println("BookTest.caseIdName测试完成");
    }

    //抛出异常 @Test注解的expected参数，异常测试，用于测试是否会抛出指定的异常，若抛出则为成功，反之为失败。
    @Test(expected = ArithmeticException.class)
    public void caseException() {
        int n = 2 / 0;
        System.out.println("" + n);
    }

    //超时
    @Test(timeout = 1000)
    public void caseTimeout() {
        for (; ; ) {

        }
    }


    // Category Marks a test class or test method as belonging to one or more categories of tests.
    @Category(BehaviorTest.class)
    @Test
    public void caseBookSay() {
        String str = book.bookSay();
        assertEquals("junit test", str);
    }

}