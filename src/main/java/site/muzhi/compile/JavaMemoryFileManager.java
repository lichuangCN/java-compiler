package site.muzhi.compile;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.security.SecureClassLoader;

/**
 * @author lichuang
 * @date 2021/04/29
 * @description 处理编译后的结果
 * <p>
 * 提供编译结果存储和编译类的加载
 */
public class JavaMemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    /**
     * 存储编译后的代码
     */
    private MemoryOutputJavaFileObject javaClassObject;

    protected JavaMemoryFileManager(JavaFileManager fileManager) {
        super(fileManager);
    }

    /**
     * 返回编译后的类
     * <p>
     * 返回一个匿名的SecureClassLoader，加载由JavaCompiler编译后，保存在JavaClassObject对象中的byte数组
     *
     * @param location
     * @return
     */
    @Override
    public ClassLoader getClassLoader(Location location) {
        return new SecureClassLoader() {
            @Override
            protected Class<?> findClass(String name) throws ClassNotFoundException {
                // 获取编译后的文件
                byte[] bytes = javaClassObject.getBytes();
                // 将字节数组转换为类Class的实例
                return super.defineClass(name, bytes, 0, bytes.length);
            }
        };
    }

    /**
     * 给编译器提供JavaClassObject，编译器会将编译结果写进此对象，即调用此对象的openOutputStream()方法，将结果写入到此输出流
     *
     * @param location
     * @param className
     * @param kind
     * @param sibling
     * @return
     * @throws IOException
     */
    @Override
    public JavaFileObject getJavaFileForOutput(Location location, String className, JavaFileObject.Kind kind, FileObject sibling) throws IOException {
        if (kind == JavaFileObject.Kind.CLASS) {
            this.javaClassObject = new MemoryOutputJavaFileObject(className);
            return this.javaClassObject;
        } else {
            return super.getJavaFileForOutput(location, className, kind, sibling);
        }
    }
}
