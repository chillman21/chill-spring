package club.chillman.inject;

import club.chillman.core.BeanContainer;
import club.chillman.inject.annotation.Autowired;
import club.chillman.util.ClassUtil;
import club.chillman.util.ValidationUtil;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;


/**
 * @author NIU
 * @createTime 2020/8/10 2:21
 */
@Slf4j
public class DependencyInjector {
    private BeanContainer beanContainer;
    private static final Class<Autowired> AUTO_WIRED = Autowired.class;

    public DependencyInjector() {
        this.beanContainer = BeanContainer.getInstance();
    }

    /**
     * 执行IOC
     */
    public void doIoc() {
        //1.遍历Bean容器中所有的Class对象
        if (ValidationUtil.isEmpty(beanContainer.getClasses())) {
            log.warn("empty class set in Bean Container");
            return;
        }
        for (Class<?> clazz : beanContainer.getClasses()) {
            //2.遍历Class对象的所有成员变量
            Field[] fields = clazz.getDeclaredFields();
            if (ValidationUtil.isEmpty(fields)) {
                continue;
            }
            for (Field field : fields) {
                //3.找出被 Autowired标记的成员变量
                if (field.isAnnotationPresent(Autowired.class)) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    String autowiredValue = autowired.value();
                    //4.获取这些成员变量的类型
                    Class<?> fieldClass = field.getType();
                    //5.获取这些成员变量的类型在容器里对应的实例
                    Object fieldInstance = getFieldInstance(fieldClass, autowiredValue);
                    if (fieldInstance == null) {
                        throw new RuntimeException("unable to inject relevant type," +
                                "target fieldClass is :" + fieldClass.getName() +"<-"+ autowiredValue);
                    } else {
                        //6.通过反射将对应的成员变量实例注入到成员变量所在类的实例里
                        Object targetBean = beanContainer.getBean(clazz);
                        ClassUtil.setField(field, targetBean, fieldInstance, true);
                    }
                }
            }

        }

    }

    /**
     * 根据Class在BeanContainer里获取其实例或者实现类对象
     * @param fieldClass 成员变量class对象
     * @return 成员变量实例或者实现类对象
     */
    private Object getFieldInstance(Class<?> fieldClass, String autowiredValue) {
        Object fieldValue = beanContainer.getBean(fieldClass);
        if (fieldValue != null) {
            return fieldValue;
        } else {
            Class<?> implementClass = getImplementClass(fieldClass, autowiredValue);
            return (implementClass != null) ? beanContainer.getBean(implementClass) : null;
        }

    }

    /**
     * 获取接口的实现类
     * @param interfaceClass 接口类型
     * @param autowiredValue
     * @return 接口的实现类Class对象
     */
    private Class<?> getImplementClass(Class<?> interfaceClass, String autowiredValue) {
        Set<Class<?>> classSet = beanContainer.getClassesBySuper(interfaceClass);
        if (!ValidationUtil.isEmpty(classSet)) {
            if (ValidationUtil.isEmpty(autowiredValue)) {
                return handleEmptyAutowiredValue(interfaceClass, classSet);
            } else {
                for (Class<?> clazz : classSet) {
                    if (autowiredValue.equals(clazz.getSimpleName())) {
                        return clazz;
                    }
                }
            }
        }
        return null;
    }

    private Class<?> handleEmptyAutowiredValue(Class<?> interfaceClass,Set<Class<?>> classSet) {
        if (classSet.size() == 1) {
            return classSet.iterator().next();
        } else {
            //如果多于两个实现类并且用户并未指定实现类名称，则抛出异常
            log.error(interfaceClass.getName() + "impl class more than 1");
            throw new RuntimeException("multiple implemented classes for "
                    + interfaceClass.getName() + "please set @Autowired's value to pick one");
        }
    }

}
