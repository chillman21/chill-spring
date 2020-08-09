package club.chillman.demo.core.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author NIU
 * @createTime 2020/8/9 15:17
 */
@Slf4j
public class ClassUtil {
    public static final String FILE_PROTOCOL = "file";
    /**
     * 获取包下类集合
     *
     * @param packageName 包名
     * @return 类集合
     */
    public static Set<Class<?>> extractPackageClass(String packageName) {
        //1.获取到类的加载器
        ClassLoader classLoader = getClassLoader();
        //2.通过类加载器获取加载到的资源
        //ClassLoader可以加载任何一个在classpath上存在的资源文件
        URL url = classLoader.getResource(packageName.replace(".", "/"));
        if (url == null) {
            log.warn("Cannot get resource from target package:" + packageName);
            return null;
        }
        //3.依据不同的资源类型，采取不同的方式获取资源的集合
        Set<Class<?>> classSet = null;
        //过滤出文件类型的资源
        if (url.getProtocol().equalsIgnoreCase(FILE_PROTOCOL)) {
            classSet = new CopyOnWriteArraySet<>();
            File packageDirectory = new File(url.getPath());
            extractClassFile(classSet, packageDirectory, packageName);
        }
        //TODO 此处可以加入针对其他资源处理的逻辑
        return classSet;
    }

    /**
     * 递归获取目标package里面的所有class文件(包括package里的class文件)
     * @param emptyClassSet 装载目标类的集合
     * @param fileSource 文件或目录
     * @param packageName   包名
     */
    private static void extractClassFile(Set<Class<?>> emptyClassSet, File fileSource, String packageName) {
        if (!fileSource.isDirectory()) {
            return;
        }
        //获取当前目录内所有文件和文件夹，非递归，不包含子文件/子文件夹
        File[] files = fileSource.listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                if (file.isDirectory()) {
                    return true;
                } else {
                    //获取文件的绝对路径
                    String absolutePath = file.getAbsolutePath();
                    if (absolutePath.endsWith(".class")) {
                        //如果是class文件，则直接加载
                        addToClassSet(absolutePath);
                    }
                }
                return false;
            }

            private void addToClassSet(String path) {
                //1.从class文件的绝对路径里提取出包含该package的全限定类名
                path = path.replace(File.separator, ".");
                String className = path.substring(path.indexOf(packageName));
                className = className.substring(0, className.lastIndexOf("."));
                //2.通过反射机制获取对应的Class对象并加入到classSet里
                Class<?> targetClass = loadClass(className);
                emptyClassSet.add(targetClass);
            }
        });
        if (files != null) {
            for (File f : files) {
                //递归调用
                extractClassFile(emptyClassSet, f, packageName);
            }
        }
    }

    public static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            log.error("load class error:", e);
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取当前ClassLoader
     * @return 当前ClassLoader
     */
    public static ClassLoader getClassLoader() {
        /**
         * getContextClassLoader
         * 这个方法返回线程上下文类加载器。这个加载器的类型指定的工作交给了线程创建者，
         * 创建者在创建线程之后用对应的setContextClassLoader()方法将适合的类加载器设置到线程中，
         * 那么线程中的代码就可以通过getContextClassLoader()获取到这个类加载器来加载类或者资源。
         * 如果不设置默认是系统类加载器就是 app ClassLoader()。
         */
        return Thread.currentThread().getContextClassLoader();
    }

    public static void main(String[] args) {
        extractPackageClass("club.chillman.core");
    }
}
