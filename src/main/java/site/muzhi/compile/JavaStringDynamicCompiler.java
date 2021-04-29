package site.muzhi.compile;

import javax.tools.*;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author lichuang
 * @date 2021/04/29
 * @description 自定义编译器
 */
public class JavaStringDynamicCompiler {

    private JavaCompiler compiler;
    private JavaFileManager javaFileManager;

    public JavaStringDynamicCompiler() {
        this.javaFileManager = initManager();
    }

    private JavaFileManager initManager() {
        compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector collector = new DiagnosticCollector();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(collector, null, null);
        JavaMemoryFileManager manager = new JavaMemoryFileManager(fileManager);
        return manager;
    }

    /**
     * 编译源码并获取Class对象
     *
     * @param fullName   全限定类名,如：com.example.compile.Example
     * @param sourceCode 源码字符串
     * @return
     * @throws ClassNotFoundException
     */
    public Class compile(String fullName, String sourceCode) throws ClassNotFoundException {
        MemoryInputJavaFileObject fileObject = new MemoryInputJavaFileObject(fullName, sourceCode);

        JavaCompiler.CompilationTask task = compiler.getTask(null, javaFileManager, null, null, null, Arrays.asList(fileObject));
        Boolean result = task.call();
        if (result == null || !result.booleanValue()) {
            throw new RuntimeException("Compile fail.");
        }
        return this.javaFileManager.getClassLoader(null).loadClass(fullName);
    }

    /**
     * 关闭 JavaFileManager
     *
     * @throws IOException
     */
    public void close() throws IOException {
        this.javaFileManager.close();
    }
}
