package my.test.inject;

import club.chillman.core.BeanContainer;
import club.chillman.demo.controller.frontend.MainPageController;
import club.chillman.demo.service.combine.HeadLineShopCategoryCombineService;
import club.chillman.inject.DependencyInjector;
import org.junit.jupiter.api.Test;

/**
 * @author NIU
 * @createTime 2020/8/10 3:34
 */
public class DependencyInjectTest {
    @Test
    public void doIocTest() {
        BeanContainer beanContainer = BeanContainer.getInstance();
        beanContainer.loadBeans("club.chillman");
        System.out.println(beanContainer.isLoaded());
        MainPageController controller = beanContainer.getBean(MainPageController.class);
        System.out.println(controller.getHeadLineShopCategoryCombineService());
        new DependencyInjector().doIoc();
        System.out.println(controller.getHeadLineShopCategoryCombineService());

    }
}
