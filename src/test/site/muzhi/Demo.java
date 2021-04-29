package site.muzhi;

import site.muzhi.compile.JavaStringDynamicCompiler;

/**
 * @author lichuang
 * @date 2021/04/29
 * @description
 */
public class Demo {
    public static void main(String[] args) throws Exception {
        StringBuilder src = new StringBuilder();
        src.append("package site.muzhi.compile;");
        src.append("public class DynaClass {\n");
        src.append("    public String toString() {\n");
        src.append("        return \"Hello, I am \" + ");
        src.append("this.getClass().getSimpleName();\n");
        src.append("    }\n");
        src.append("}\n");

        String fullName = "site.muzhi.compile.DynaClass";

        JavaStringDynamicCompiler compiler = new JavaStringDynamicCompiler();
        Class aClass = compiler.compile(fullName, src.toString());

        Object obj = aClass.getConstructor().newInstance();
        System.out.println(obj);
        compiler.close();
    }


    /*
     * 输出结果
     * Hello, I am DynaClass
     *
     * Process finished with exit code 0
     */
}
