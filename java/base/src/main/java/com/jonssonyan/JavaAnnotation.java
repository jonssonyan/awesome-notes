package com.jonssonyan;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaAnnotation {
    public static void main(String[] args) throws Exception {
        // ================ 基本注解用法 - 元数据标记 ================

        // 获取类上的注解
        Class<?> clazz = AnnotatedClass.class;
        AClassInfo classInfo = clazz.getAnnotation(AClassInfo.class);
        System.out.println("类注解信息: 作者=" + classInfo.author() +
                ", 版本=" + classInfo.version()); // 读取注解属性值

        // 获取方法上的注解
        Method method = clazz.getMethod("annotatedMethod", String.class);
        AMethodInfo methodInfo = method.getAnnotation(AMethodInfo.class);
        System.out.println("方法注解信息: 描述=" + methodInfo.description() +
                ", 重要性=" + methodInfo.importance()); // 读取注解属性值

        // 检查注解是否存在
        System.out.println("方法是否标记为过时: " +
                method.isAnnotationPresent(Deprecated.class)); // 检查是否有特定注解

        // 获取参数注解
        Annotation[][] paramAnnotations = method.getParameterAnnotations();
        System.out.println("第一个参数的注解数量: " + paramAnnotations[0].length);
        for (Annotation annotation : paramAnnotations[0]) {
            if (annotation instanceof ParamInfo) {
                ParamInfo paramInfo = (ParamInfo) annotation;
                System.out.println("参数注解信息: " + paramInfo.value());
            }
        }

        // 获取所有注解
        Annotation[] annotations = method.getAnnotations();
        System.out.println("方法上所有注解: " + Arrays.toString(annotations));

        // ================ 内置注解 - Java提供的标准注解 ================

        // @Override - 表示方法重写父类方法
        SubClass subClass = new SubClass();
        subClass.overriddenMethod(); // 调用重写的方法

        // @Deprecated - 标记已过时的元素
        subClass.deprecatedMethod(); // 使用已过时的方法(会有警告)

        // @SuppressWarnings - 抑制编译器警告
        subClass.suppressWarningMethod(); // 抑制了未检查转换警告的方法

        // @FunctionalInterface - Java 8引入，标记函数式接口
        MyFunctionalInterface lambda = s -> System.out.println("Lambda: " + s);
        lambda.process("测试函数式接口"); // 使用函数式接口

        // ================ 元注解 - 注解的注解 ================

        // @Target - 指定注解应用的位置
        System.out.println("@AClassInfo的目标: " + Arrays.toString(AClassInfo.class.getAnnotation(Target.class).value()));

        // @Retention - 指定注解保留的阶段
        System.out.println("@AClassInfo的保留策略: " + AClassInfo.class.getAnnotation(Retention.class).value());

        // @Documented - 注解是否包含在JavaDoc中
        System.out.println("@AClassInfo是否文档化: " + AClassInfo.class.isAnnotationPresent(Documented.class));

        // @Inherited - 注解是否被子类继承
        System.out.println("@AClassInfo是否可继承: " + AClassInfo.class.isAnnotationPresent(Inherited.class));

        // @Repeatable - Java 8引入，允许在同一元素上多次使用同一注解
        Method repeatableMethod = RepeatableClass.class.getMethod("repeatableMethod");
        Schedules schedules = repeatableMethod.getAnnotation(Schedules.class);
        System.out.println("@Schedule注解数量: " + schedules.value().length);
        for (Schedule schedule : schedules.value()) {
            System.out.println("定时任务: 时间=" + schedule.time() + ", 天=" + schedule.day());
        }

        // ================ Java 8注解增强 - 类型注解 ================

        // @Target(ElementType.TYPE_USE) - 可用于任何类型使用的位置
        TypeAnnotatedClass<@TypeParam String> typeObj = new TypeAnnotatedClass<>();
        System.out.println("类型参数注解演示: " + typeObj.getClass().getTypeParameters()[0].getAnnotations().length);

        // ================ 注解处理器 - 运行时处理注解 ================

        // 自定义注解处理
        AnnotationProcessor processor = new AnnotationProcessor();
        processor.processClass(AnnotatedClass.class);
    }

    // 自定义注解处理器
    static class AnnotationProcessor {
        public void processClass(Class<?> clazz) {
            // 处理类上的注解
            if (clazz.isAnnotationPresent(AClassInfo.class)) {
                AClassInfo info = clazz.getAnnotation(AClassInfo.class);
                System.out.println("处理类: " + clazz.getSimpleName() +
                        ", 作者: " + info.author());
            }

            // 处理方法上的注解
            for (Method method : clazz.getDeclaredMethods()) {
                if (method.isAnnotationPresent(AMethodInfo.class)) {
                    AMethodInfo info = method.getAnnotation(AMethodInfo.class);
                    System.out.println("处理方法: " + method.getName() +
                            ", 描述: " + info.description());
                }
            }
        }
    }
}

// ================ 自定义注解定义 ================

// 类级别注解
@Target(ElementType.TYPE) // 只能应用于类型(类、接口、枚举等)
@Retention(RetentionPolicy.RUNTIME) // 运行时保留
@Documented // 包含在JavaDoc中
@Inherited // 允许子类继承
@interface AClassInfo {
    String author() default "jonssonyan"; // 注解属性，带默认值

    String date() default "2025-04-25";

    String version() default "1.0";
}

// 方法级别注解
@Target(ElementType.METHOD) // 只能应用于方法
@Retention(RetentionPolicy.RUNTIME) // 运行时保留
@interface AMethodInfo {
    String description();

    int importance() default 1; // 带默认值的属性

    String[] tags() default {}; // 数组类型的属性
}

// 参数级别注解
@Target(ElementType.PARAMETER) // 只能应用于方法参数
@Retention(RetentionPolicy.RUNTIME)
@interface ParamInfo {
    String value(); // 单个属性可以使用value命名，简化使用
}

// 带有默认属性值的注解
@Target({ElementType.FIELD, ElementType.METHOD}) // 可以应用于字段和方法
@Retention(RetentionPolicy.RUNTIME)
@interface Default {
    String value() default "默认值";
}

// Java 8可重复注解
@Repeatable(Schedules.class) // 标记为可重复注解
@interface Schedule {
    String time();

    String day();
}

// 容器注解(用于可重复注解)
@Retention(RetentionPolicy.RUNTIME)
@interface Schedules {
    Schedule[] value();
}

// Java 8类型注解
@Target(ElementType.TYPE_USE) // TYPE_USE允许注解用于任何类型的使用位置
@Retention(RetentionPolicy.RUNTIME)
@interface TypeParam {
}

// ================ 注解使用示例类 ================

@AClassInfo(author = "jonssonyan", version = "2.0")
class AnnotatedClass {

    @Default // 使用默认值
    private String field;

    @AMethodInfo(description = "示例方法", tags = {"test", "example"})
    public void annotatedMethod(@ParamInfo("输入参数") String input) {
        System.out.println("执行注解方法: " + input);
    }

    @Deprecated // 标记为过时
    public void deprecatedMethod() {
        System.out.println("此方法已过时");
    }
}

// 演示@Override注解
class SubClass extends AnnotatedClass {

    @Override // 确保方法确实重写了父类方法
    public void annotatedMethod(String input) {
        System.out.println("子类重写方法: " + input);
    }

    @Deprecated
    public void deprecatedMethod() {
        System.out.println("子类的过时方法");
    }

    @SuppressWarnings("unchecked") // 抑制未检查转换警告
    public void suppressWarningMethod() {
        List list = new ArrayList();
        list.add("无泛型");
        System.out.println("抑制警告方法");
    }

    public void overriddenMethod() {
        System.out.println("重写的方法");
    }
}

// 演示可重复注解
class RepeatableClass {

    @Schedule(time = "10:00", day = "周一")
    @Schedule(time = "14:30", day = "周三")
    @Schedule(time = "16:00", day = "周五")
    public void repeatableMethod() {
        System.out.println("可重复注解方法");
    }
}

// 演示函数式接口注解
@FunctionalInterface
interface MyFunctionalInterface {
    void process(String input); // 只能有一个抽象方法
}

// 演示类型注解
class TypeAnnotatedClass<T> {

    public void typeAnnotatedMethod(@TypeParam String param) {
        @TypeParam String localVar = param; // 局部变量上的类型注解
        System.out.println("类型注解方法: " + localVar);
    }

    public @TypeParam String returnTypeAnnotated() { // 返回类型上的注解
        return "返回值";
    }
}