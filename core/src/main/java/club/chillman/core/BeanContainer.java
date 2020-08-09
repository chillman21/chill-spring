package club.chillman.core;

import club.chillman.core.annotation.Component;
import club.chillman.core.annotation.Controller;
import club.chillman.core.annotation.Repository;
import club.chillman.core.annotation.Service;
import club.chillman.util.ClassUtil;
import club.chillman.util.ValidationUtil;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 获取Bean容器实例 线程安全、能抵御反射和序列化攻击
 *
 * @author NIU
 * @createTime 2020/8/9 19:08
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {

    /**
     * 容器是否已经加载过Bean
     */
    private boolean loaded = false;

    /**
     * 存放所有被配置/标记的目标对象的Map
     */
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap<>();


    /**
     * 是否已经加载过Bean
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * Bean实例数量
     */
    public int size() {
        return beanMap.size();
    }


    /**
     * 待加载bean的注解列表
     */
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION_LIST
            = Arrays.asList(Component.class, Controller.class, Service.class, Repository.class);


    /**
     *  获取Bean容器实例
     * @return Bean容器实例
     */
    public static BeanContainer getInstance() {
        return ContainerHolder.HOLDER.instance;
    }

    private enum ContainerHolder {
        HOLDER;
        private BeanContainer instance;
        ContainerHolder() {
            instance = new BeanContainer();
        }
    }

    /**
     * 扫描加载所有Bean
     * @param packageName 待扫描包名
     */
    public synchronized void loadBeans(String packageName) {
        //判断bean容器是否被加载过
        if (isLoaded()) {
            log.warn("BeanContainer has loaded already");
            return;
        }
        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);
        if (ValidationUtil.isEmpty(classSet)) {
            log.warn("extract nothing from packageName: " + packageName);
            return;
        }
        for (Class<?> clazz : classSet) {
            for (Class<? extends Annotation> annotation : BEAN_ANNOTATION_LIST) {
                //如果类上面标记了定义的注解
                if (clazz.isAnnotationPresent(annotation)) {
                    //将目标类本身作为Key，目标类的实例作为Value，放入到beanMap中。
                    beanMap.put(clazz, ClassUtil.newInstance(clazz, true));
                }
            }
        }
        loaded = true;
    }

    /**
     * 添加一个class对象及其实例
     * @param clazz class对象
     * @param bean  Bean实例
     * @return 原有的Bean实例，没有则返回null
     */
    public Object addBean(Class<?> clazz, Object bean) {
        return beanMap.put(clazz, bean);
    }

    /**
     *  移除一个IOC容器管理的对象
     * @param clazz class对象
     * @return 已删除的Bean的实例，没有则返回null
     */
    public Object removeBean(Class<?> clazz) {
        return beanMap.remove(clazz);
    }

    /**
     * 根据Class对象获取Bean实例
     * @param clazz Class对象
     * @return Bean实例
     */
    public <T> T getBean(Class<T> clazz) {
        return (T)beanMap.get(clazz);
    }

    /**
     * 获取容器管理的所有Class对象集合
     * @return Class对象集合
     */
    public Set<Class<?>> getClasses() {
        return beanMap.keySet();
    }

    /**
     * 获取所有Bean集合
     * @return Bean集合
     */
    public Set<Object> getBeans() {
        return new HashSet<>(beanMap.values());
    }

    /**
     * 根据注解筛选出Bean的Class集合
     * @param annotation 注解
     * @return Class集合
     */
    public Set<Class<?>> getClassesByAnnotation(Class<? extends Annotation> annotation) {
        //1.获取beanMap的所有class对象
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)) {
            log.warn("nothing in beanMap");
            return null;
        }
        //2.通过注解筛选被注解标记的class对象，并添加到classSet里
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            if (clazz.isAnnotationPresent(annotation)) {
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0 ? classSet : null;
    }

    /**
     * 通过接口或者父类获取实现类或者子类的Class集合，不包括其本身
     * @param interfaceOrClass 接口Class或者父类Class
     * @return Class集合
     */
    public Set<Class<?>> getClassesBySuper(Class<?> interfaceOrClass) {
        //1.获取beanMap的所有class对象
        Set<Class<?>> keySet = getClasses();
        if (ValidationUtil.isEmpty(keySet)) {
            log.warn("nothing in beanMap");
            return null;
        }
        //2.判断keySet中的元素是否是传入的接口或者类的子类，如果是，就将其添加到classSet中
        Set<Class<?>> classSet = new HashSet<>();
        for (Class<?> clazz : keySet) {
            //isAssignableFrom 判断宿主是否是clazz的父类/接口/本身
            if (interfaceOrClass.isAssignableFrom(clazz) && !clazz.equals(interfaceOrClass)) {
                classSet.add(clazz);
            }
        }
        return classSet.size() > 0 ? classSet : null;
    }
}
