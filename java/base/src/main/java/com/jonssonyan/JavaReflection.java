package com.jonssonyan;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaReflection {
    public static void main(String[] args) throws Exception {
        // ================ Class类 - 反射的基础 ================

        // 获取Class对象 - 三种方式
        Class<?> class1 = User.class; // 方式1：类字面常量
        Class<?> class2 = new User("jonssonyan", 30).getClass(); // 方式2：对象的getClass()方法
        Class<?> class3 = Class.forName("com.jonssonyan.User"); // 方式3：Class.forName()方法
        System.out.println("Class对象比较: " + (class1 == class2 && class2 == class3)); // 同一个类的Class对象相同

        // Class对象信息
        System.out.println("类名: " + class1.getName()); // 完全限定类名
        System.out.println("简单类名: " + class1.getSimpleName()); // 不含包名
        System.out.println("是否为接口: " + class1.isInterface()); // 判断是否为接口
        System.out.println("父类: " + class1.getSuperclass().getName()); // 获取父类

        // ================ 构造器 - 动态创建对象 ================

        // 获取构造器
        Constructor<?> constructor1 = class1.getConstructor(); // 获取公共无参构造器
        Constructor<?> constructor2 = class1.getConstructor(String.class, int.class); // 获取带参构造器
        Constructor<?>[] constructors = class1.getConstructors(); // 获取所有公共构造器
        System.out.println("构造器数量: " + constructors.length); // 构造器数量

        // 使用构造器创建对象
        User user1 = (User) constructor1.newInstance(); // 调用无参构造器
        User user2 = (User) constructor2.newInstance("jonssonyan", 30); // 调用带参构造器
        System.out.println("反射创建对象: " + user2.getName()); // 访问创建的对象

        // ================ 字段 - 访问对象的属性 ================

        // 获取字段
        Field nameField = class1.getDeclaredField("name"); // 获取指定字段
        Field[] fields = class1.getDeclaredFields(); // 获取所有字段(包括私有)
        System.out.println("字段数量: " + fields.length); // 字段数量

        // 访问字段值
        nameField.setAccessible(true); // 设置可访问(对私有字段必须)
        nameField.set(user1, "新名字"); // 设置字段值
        System.out.println("反射读取字段: " + nameField.get(user1)); // 获取字段值

        // 字段信息
        for (Field field : fields) {
            System.out.println("字段: " + field.getName() + ", 类型: " + field.getType().getSimpleName() + ", 修饰符: " + Modifier.toString(field.getModifiers()));
        }

        // ================ 方法 - 动态调用 ================

        // 获取方法
        Method sayHelloMethod = class1.getMethod("sayHello"); // 获取公共方法
        Method setAgeMethod = class1.getMethod("setAge", int.class); // 获取带参方法
        Method privateMethod = class1.getDeclaredMethod("privateMethod"); // 获取私有方法
        Method[] methods = class1.getDeclaredMethods(); // 获取所有方法(包括私有)

        // 调用方法
        sayHelloMethod.invoke(user1); // 调用无参方法
        setAgeMethod.invoke(user1, 25); // 调用带参方法

        // 调用私有方法
        privateMethod.setAccessible(true); // 设置可访问
        privateMethod.invoke(user1); // 调用私有方法

        // 方法信息
        System.out.println("方法数量: " + methods.length); // 方法数量
        for (Method method : methods) {
            if (!method.getName().contains("$")) { // 跳过合成方法
                System.out.println("方法: " + method.getName() + ", 返回类型: " + method.getReturnType().getSimpleName() + ", 参数数量: " + method.getParameterCount());
            }
        }

        // ================ 数组与泛型 - 反射操作 ================

        // 数组反射
        String[] strArray = (String[]) Array.newInstance(String.class, 3); // 创建数组
        Array.set(strArray, 0, "反射"); // 设置数组元素
        Array.set(strArray, 1, "创建"); // 设置数组元素
        Array.set(strArray, 2, "数组"); // 设置数组元素
        System.out.println("反射数组元素: " + Array.get(strArray, 0)); // 获取数组元素

        // 泛型反射
        List<String> stringList = new ArrayList<>();
        Class<?> listClass = stringList.getClass();
        Method addMethod = listClass.getMethod("add", Object.class);
        addMethod.invoke(stringList, "通过反射添加"); // 通过反射绕过泛型检查
        System.out.println("反射操作泛型集合: " + stringList.get(0));

        // ================ 反射与注解 - 运行时获取注解信息 ================

        // 获取类上的注解
        if (class1.isAnnotationPresent(ClassInfo.class)) {
            ClassInfo annotation = class1.getAnnotation(ClassInfo.class);
            System.out.println("类注解值: author=" + annotation.author() + ", version=" + annotation.version());
        }

        // 获取方法上的注解
        Method getUserInfoMethod = class1.getMethod("getUserInfo");
        if (getUserInfoMethod.isAnnotationPresent(MethodInfo.class)) {
            MethodInfo annotation = getUserInfoMethod.getAnnotation(MethodInfo.class);
            System.out.println("方法注解值: description=" + annotation.description() + ", importance=" + annotation.importance());
        }

        // ================ 动态代理 - 运行时生成代理对象 ================

        // JDK动态代理 - 基于接口
        UserService userService = new UserServiceImpl();
        InvocationHandler handler = new LoggingInvocationHandler(userService);

        UserService proxy = (UserService) Proxy.newProxyInstance(UserService.class.getClassLoader(), new Class<?>[]{UserService.class}, handler);

        proxy.addUser("jonssonyan"); // 调用代理对象方法

        // 获取代理类信息
        Class<?> proxyClass = proxy.getClass();
        System.out.println("代理类名: " + proxyClass.getName());
        System.out.println("代理类的接口: " + Arrays.toString(proxyClass.getInterfaces()));
    }
}

// 用于反射演示的类
@ClassInfo(author = "jonssonyan", version = "1.0")
class User {
    private String name;
    private int age;

    public User() {
        this.name = "默认名";
        this.age = 0;
    }

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age > 0) {
            this.age = age;
        }
    }

    public void sayHello() {
        System.out.println("你好，我是" + name + "，今年" + age + "岁");
    }

    @MethodInfo(description = "获取用户信息", importance = 5)
    public String getUserInfo() {
        return "用户[name=" + name + ", age=" + age + "]";
    }

    private void privateMethod() {
        System.out.println("这是私有方法");
    }
}

// 自定义注解
@Retention(RetentionPolicy.RUNTIME) // 运行时保留
@Target(ElementType.TYPE) // 用于类、接口
@interface ClassInfo {
    String author();

    String version() default "1.0";
}

@Retention(RetentionPolicy.RUNTIME) // 运行时保留
@Target(ElementType.METHOD) // 用于方法
@interface MethodInfo {
    String description();

    int importance() default 1;
}

// 接口与实现类，用于动态代理演示
interface UserService {
    void addUser(String name);

    void deleteUser(String name);
}

class UserServiceImpl implements UserService {
    @Override
    public void addUser(String name) {
        System.out.println("添加用户: " + name);
    }

    @Override
    public void deleteUser(String name) {
        System.out.println("删除用户: " + name);
    }
}

// 动态代理处理器
class LoggingInvocationHandler implements InvocationHandler {
    private final Object target;

    public LoggingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开始调用方法: " + method.getName());
        Object result = method.invoke(target, args);
        System.out.println("方法调用结束: " + method.getName());
        return result;
    }
}