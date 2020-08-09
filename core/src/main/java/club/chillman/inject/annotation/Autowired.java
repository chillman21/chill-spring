package club.chillman.inject.annotation;

import java.lang.annotation.*;

/**
 * Autowired目前仅支持成员变量注入
 * TODO 未来支持构造方法、工厂方法注入
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Autowired {
    String value() default "";
}
