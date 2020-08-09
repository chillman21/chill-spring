package my.test.core;

import club.chillman.core.BeanContainer;
import club.chillman.core.annotation.Controller;
import club.chillman.demo.controller.frontend.MainPageController;
import club.chillman.demo.service.solo.HeadLineService;
import org.junit.jupiter.api.*;

import java.util.Set;

/**
 * @author NIU
 * @createTime 2020/8/9 19:57
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BeanContainerTest {
    private static BeanContainer beanContainer;
    @BeforeAll //只执行一次
    static void init() {
        beanContainer = BeanContainer.getInstance();
    }
    @Order(1)
    @DisplayName("加载目标类及其实例到BeanContainer：loadBeanTest")
    @Test
    public void loadBeansTest() {
        Assertions.assertEquals(false, beanContainer.isLoaded());
        beanContainer.loadBeans("club.chillman");
        System.out.println(beanContainer.size());
        System.out.println(beanContainer.isLoaded());
    }
    @Order(2)
    @DisplayName("根据类获取其实例")
    @Test
    public void getBeanTest() {
        MainPageController controller = beanContainer.getBean(MainPageController.class);
        controller.print();
        System.out.println(beanContainer.getClassesByAnnotation(Controller.class).size());
        Set<Class<?>> classSet = beanContainer.getClassesBySuper(HeadLineService.class);
        System.out.println(classSet);
    }
}
