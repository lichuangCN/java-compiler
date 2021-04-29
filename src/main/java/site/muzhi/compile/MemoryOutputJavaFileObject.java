package site.muzhi.compile;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * @author lichuang
 * @date 2021/04/29
 * @description 存储源代码编译后的字节码文件
 */
public class MemoryOutputJavaFileObject extends SimpleJavaFileObject {
    /**
     * Compiler编译后的byte数据会存在这个ByteArrayOutputStream对象中，
     * 后面可以取出，加载到JVM中。
     */
    private ByteArrayOutputStream byteArrayOutputStream;

    protected MemoryOutputJavaFileObject(String className) {
         // string:///com/example/compile/Example.class
        super(URI.create("string:///" + className.replaceAll("\\.", "/") + Kind.CLASS.extension), Kind.CLASS);
        this.byteArrayOutputStream = new ByteArrayOutputStream();
    }

    /**
     * 重写父类SimpleJavaFileObject中的方法
     * <p>
     * 编译器完成编译后，会调用此方法，
     * 并且将编译结果输出到该OutputStream中，即可以获取编译后的结果
     *
     * @return
     * @throws IOException
     */
    @Override
    public OutputStream openOutputStream() throws IOException {
        return this.byteArrayOutputStream;
    }

    /**
     * FileManager通过此方法获取编译后的文件，并加载到JVM中
     *
     * @return
     */
    public byte[] getBytes() {
        return this.byteArrayOutputStream.toByteArray();
    }
}
