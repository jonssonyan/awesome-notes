package com.jonssonyan;

public class JavaOOP {
    // 成员变量(字段)
    private String name;
    private int age;
    public static int count = 0; // 静态变量

    // 构造方法
    public JavaOOP() {
        // 默认构造方法
        count++;
    }

    public JavaOOP(String name, int age) {
        this.name = name;
        this.age = age;
        count++;
    }

    // 静态初始化块 - 类加载时执行
    static {
        System.out.println("Class is being loaded");
    }

    // 实例初始化块 - 每次创建对象时执行
    {
        System.out.println("Object is being created");
    }

    // Getter和Setter方法
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
        this.age = age;
    }

    // 普通方法
    public void displayInfo() {
        System.out.println("Name: " + name + ", Age: " + age);
    }

    // 静态方法
    public static void showCount() {
        System.out.println("Total objects created: " + count);
    }

    // 重写Object类的方法
    @Override
    public String toString() {
        return "JavaOOP[name=" + name + ", age=" + age + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        JavaOOP other = (JavaOOP) obj;
        return age == other.age && (name == null ? other.name == null : name.equals(other.name));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + age;
        result = prime * result + (name == null ? 0 : name.hashCode());
        return result;
    }

    // 内部类
    public class InnerClass {
        public void accessOuter() {
            System.out.println("Accessing outer class name: " + name);
        }
    }

    // 静态内部类
    public static class StaticInnerClass {
        public void display() {
            System.out.println("Static inner class cannot access non-static members directly");
            System.out.println("But can access static members: " + count);
        }
    }

    // 局部内部类示例
    public void localInnerClassDemo() {
        final String localVar = "Local Variable";

        class LocalInner {
            public void show() {
                System.out.println("Local inner class can access local final variables: " + localVar);
            }
        }

        LocalInner inner = new LocalInner();
        inner.show();
    }

    // 匿名内部类示例
    public void anonymousInnerClassDemo() {
        Runnable runner = new Runnable() {
            @Override
            public void run() {
                System.out.println("Anonymous inner class implementing Runnable");
            }
        };

        runner.run();
    }
}

// 继承
class Animal {
    protected String species;

    public Animal(String species) {
        this.species = species;
    }

    public void makeSound() {
        System.out.println("Some generic sound");
    }

    // final方法不能被重写
    public final String getSpecies() {
        return species;
    }
}

class Dog extends Animal {
    private String breed;

    public Dog(String breed) {
        super("Canine"); // 调用父类构造方法
        this.breed = breed;
    }

    // 方法重写
    @Override
    public void makeSound() {
        System.out.println("Woof!");
    }

    // 子类特有方法
    public void fetch() {
        System.out.println("Fetching the ball");
    }
}

// 抽象类和方法
abstract class Shape {
    protected String color;

    public Shape(String color) {
        this.color = color;
    }

    // 抽象方法 - 没有方法体
    public abstract double area();

    // 非抽象方法
    public String getColor() {
        return color;
    }
}

class Circle extends Shape {
    private double radius;

    public Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    // 实现抽象方法
    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

// 接口
interface Drawable {
    void draw(); // 默认为public abstract

    // 默认方法 (Java 8+)
    default void display() {
        System.out.println("Displaying in default way");
    }

    // 静态方法 (Java 8+)
    static void info() {
        System.out.println("Drawable interface");
    }
}

interface Resizable {
    void resize(double factor);
}

// 实现多个接口
class Rectangle extends Shape implements Drawable, Resizable {
    private double width;
    private double height;

    public Rectangle(String color, double width, double height) {
        super(color);
        this.width = width;
        this.height = height;
    }

    @Override
    public double area() {
        return width * height;
    }

    @Override
    public void draw() {
        System.out.println("Drawing a rectangle");
    }

    @Override
    public void resize(double factor) {
        width *= factor;
        height *= factor;
    }
}

// 枚举
enum Day {
    MONDAY("星期一"),
    TUESDAY("星期二"),
    WEDNESDAY("星期三"),
    THURSDAY("星期四"),
    FRIDAY("星期五"),
    SATURDAY("星期六"),
    SUNDAY("星期日");

    private final String chineseName;

    Day(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getChineseName() {
        return chineseName;
    }

    // 枚举也可以有方法
    public boolean isWeekend() {
        return this == SATURDAY || this == SUNDAY;
    }
}

// 单例模式
class Singleton {
    // 饿汉式
    private static final Singleton INSTANCE = new Singleton();

    private Singleton() {
        // 私有构造方法
    }

    public static Singleton getInstance() {
        return INSTANCE;
    }
}

class LazySingleton {
    // 懒汉式
    private static volatile LazySingleton instance;

    private LazySingleton() {
        // 私有构造方法
    }

    public static synchronized LazySingleton getInstance() {
        if (instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }

    // 双重检查锁定
    public static LazySingleton getInstanceDCL() {
        if (instance == null) {
            synchronized (LazySingleton.class) {
                if (instance == null) {
                    instance = new LazySingleton();
                }
            }
        }
        return instance;
    }
}

// 静态内部类方式实现单例
class StaticSingleton {
    private StaticSingleton() {
    }

    private static class SingletonHolder {
        private static final StaticSingleton INSTANCE = new StaticSingleton();
    }

    public static StaticSingleton getInstance() {
        return SingletonHolder.INSTANCE;
    }
}

// 枚举方式实现单例
enum EnumSingleton {
    INSTANCE;

    public void doSomething() {
        // 业务方法
    }
}