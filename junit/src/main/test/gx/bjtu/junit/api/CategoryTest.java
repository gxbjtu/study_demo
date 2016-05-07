package gx.bjtu.junit.api;

import gx.bjtu.junit.iface.BehaviorTest;
import org.junit.experimental.categories.Categories;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by jean on 16-3-27.
 */

@RunWith(Categories.class)
@Categories.IncludeCategory(BehaviorTest.class)
@Suite.SuiteClasses(Junit4BasicTest.class)
public class CategoryTest {

    /**
     * Category：Category同样继承自Suit，Category似乎是Suit的加强版，它和Suit一样提供了将若干测试用例类组织成一组的能力，
     * 除此以外它可以对各个测试用例进行分组，使你有机会只选择需要的部分用例。
     * 举个例子Person有获取age和name的方法也有talk和walk方法，前者用于获取属性后者是Person的行为，Category使我们可以只运行属性测试，反之亦然。
     *
     * @Categories.IncludeCategory({BehaviorTest.class, ...})  运行某些类
     * @ExcludeCategory(BehaviorTest.class)  注解用于排除用例
     */
}
