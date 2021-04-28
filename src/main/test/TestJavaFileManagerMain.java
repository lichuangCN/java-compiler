import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;
import java.util.Arrays;

/**
 * @author lichuang
 * @date 2021/04/28
 * @description
 */
public class TestJavaFileManagerMain {

    public static void main(String[] args) {
        String fullFileName = "".replaceAll("\\.", java.io.File.separator) + "Calculator.java";
        System.out.println(fullFileName);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> files = fileManager.getJavaFileObjectsFromStrings(Arrays.asList(fullFileName));
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, files);

        Boolean result = task.call();
        System.out.println(result == true ? "success" : "fail");


    }
}
