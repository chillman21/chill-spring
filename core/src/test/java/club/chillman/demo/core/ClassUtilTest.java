package club.chillman.demo.core;

import club.chillman.demo.core.util.ClassUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

/**
 * @author NIU
 * @createTime 2020/8/9 17:25
 */
public class ClassUtilTest {
    @DisplayName("提取目标类的方法")
    @Test
    public void extractPackageClassTest() {
        Set<Class<?>> classSet = ClassUtil.extractPackageClass("club.chillman.demo.entity");
        System.out.println(classSet);
        Assertions.assertEquals(4,classSet.size());
    }
}
