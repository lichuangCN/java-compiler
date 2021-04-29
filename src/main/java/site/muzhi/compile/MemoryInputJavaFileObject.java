package site.muzhi.compile;

import javax.tools.SimpleJavaFileObject;
import java.io.IOException;
import java.net.URI;

/**
 * @author lichuang
 * @date 2021/04/29
 * @description 存储源代码
 */
public class MemoryInputJavaFileObject extends SimpleJavaFileObject {

    /**
     * java源代码
     */
    private CharSequence sourceCode;

    /**
     * @param className  全限定类名，如com.example.compile.Example
     * @param sourceCode 源码字符串
     */
    protected MemoryInputJavaFileObject(String className, String sourceCode) {
        //  // string:///com/example/compile/Example.java
        super(URI.create("string:///" + className.replaceAll("\\.", "/") + Kind.SOURCE.extension), Kind.SOURCE);
        this.sourceCode = sourceCode;
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
        return sourceCode;
    }
}
