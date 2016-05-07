package gx.bjtu.junit.api;

import gx.bjtu.junit.model.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ParameterTest {

    private String expectedId;
    private String targetId;
    private String expectedName;
    private String targetName;

    Book book = null;

    // @Parameters注解，参数化测试，用于对同一测试用例测试一组数据。该方法用来存放测试数据
    // 这里参数就是通过Constructor的参数传入的
    @Parameters
    public static Collection Result() {
        return Arrays.asList(new Object[][]{
                {"002", "001", "JSP", "ASP"},
                {"001", "001", "ASP", "ASP"}
        });
    }

    public ParameterTest(String expectedId, String targetId, String expectedName, String targetName) {
        this.expectedId = expectedId;
        this.targetId = targetId;
        this.expectedName = expectedName;
        this.targetName = targetName;
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

    @Test
    public void caseId() {
        book.setId(targetId); //设置id属性的值
        //使用Assert查看id属性的值
        assertEquals(expectedId, book.getId());
        System.out.println("id属性被测试！");
    }

    @Test
    public void caseNames() {
        book.setName(targetName); //设置name属性的值
        //使用Assert查看name属性的值
        assertEquals(expectedName, book.getName());
        System.out.println("name属性被测试！");
    }
}