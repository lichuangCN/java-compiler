import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;

/**
 * @author lichuang
 * @date 2021/04/28
 * @description
 */
public class TestCompileMain {
    public static void main(String[] args) throws IOException {
        //File f = new File("Test.java");
        //System.out.println(f == null);
        JavaCompiler javaCompiler = ToolProvider.getSystemJavaCompiler();
        //
        int result = javaCompiler.run(null, null, null, "/Users/lichuang/Devs/learn-code/java-compiler/src/main/test/Test.java");
        System.out.println(result == 0 ? "编译成功" : "编译失败");

        // java 命令，空参数，所在文件夹
        Process process = Runtime.getRuntime().exec("java Test", null, new File("/Users/lichuang/Devs/learn-code/java-compiler/src/main/test/"));

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String s;
        while ((s = reader.readLine()) != null) {
            System.out.println(s);
        }
    }
}
