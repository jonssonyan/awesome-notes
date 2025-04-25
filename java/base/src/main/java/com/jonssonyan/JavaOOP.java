package com.jonssonyan;

public class JavaOOP {
    public static void main(String[] args) {
        // ================ 类与对象 - 面向对象的基本概念 ================

        // 类的实例化 - 创建对象
        Person person = new Person("张三", 25);
        System.out.println("对象访问示例: " + person.getName()); // 访问属性
        person.setAge(26); // 修改属性
        person.sayHello(); // 调用方法

        // 构造器重载 - 多种方式创建对象
        Student student1 = new Student(); // 默认构造器
        Student student2 = new Student("李四", 20, "S10086"); // 带参构造器

        // ================ 继承 - 代码复用的机制 ================

        // 继承关系 - 子类继承父类
        Student student = new Student("王五", 18, "S12345");
        student.sayHello(); // 调用继承的方法
        student.study(); // 调用子类特有方法

        // 方法重写 - 子类重新实现父类方法
        System.out.println("方法重写示例: " + student.getInfo()); // 调用重写的方法

        // 多态 - 父类引用指向子类对象
        Person personRef = new Student("赵六", 22, "S67890");
        personRef.sayHello(); // 动态绑定，调用Student的实现
        // personRef.study(); // 编译错误，父类引用无法调用子类特有方法

        // 类型转换
        if (personRef instanceof Student) {
            Student studentObj = (Student) personRef; // 向下转型
            studentObj.study(); // 可以调用子类特有方法
        }

        // ================ 抽象类与接口 - 抽象与实现分离 ================

        // 抽象类 - 不能实例化，可以包含抽象方法和具体方法
        // Shape shape = new Shape(); // 编译错误，抽象类不能实例化
        Shape circle = new Circle(5.0);
        System.out.println("抽象类示例: 圆的面积 = " + circle.calculateArea());

        // 接口 - 定义行为规范
        Drawable drawable = new Circle(3.0);
        drawable.draw(); // 调用接口方法

        // Java 8接口默认方法
        Printable printable = new Document("报告");
        printable.print(); // 实现的接口方法
        printable.printToConsole(); // 接口默认方法

        // 函数式接口 - 只有一个抽象方法的接口，可用Lambda表达式
        Calculator add = (a, b) -> a + b;
        System.out.println("函数式接口示例: 3 + 5 = " + add.calculate(3, 5));

        // ================ 封装 - 隐藏内部实现 ================

        // 访问修饰符 - 控制访问级别
        BankAccount account = new BankAccount("1001", 1000.0);
        account.deposit(500.0); // 公开方法
        // account.balance = 2000.0; // 编译错误，私有字段不能直接访问
        System.out.println("封装示例: 账户余额 = " + account.getBalance());

        // ================ 静态成员 - 类级别的属性和方法 ================

        // 静态方法和变量
        System.out.println("静态变量示例: PI = " + MathUtils.PI);
        System.out.println("静态方法示例: 5的平方 = " + MathUtils.square(5));

        // 静态代码块 - 类加载时执行
        StaticDemo.printInfo(); // 会先执行静态块，再执行方法

        // ================ 内部类 - 嵌套类定义 ================

        // 成员内部类
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        inner.display();

        // 静态内部类
        Outer.StaticNested staticNested = new Outer.StaticNested();
        staticNested.display();

        // 局部内部类
        outer.processLocal();

        // 匿名内部类
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("匿名内部类示例: 任务执行中");
            }
        };
        runnable.run();

        // Java 8 Lambda表达式替代匿名内部类
        Runnable lambdaRunnable = () -> System.out.println("Lambda表达式示例: 更简洁的任务执行");
        lambdaRunnable.run();
    }
}

// 封装示例
class Person {
    private String name;
    private int age;

    public Person(String name, int age) {
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

    public String getInfo() {
        return "姓名: " + name + ", 年龄: " + age;
    }
}

// 继承示例
class Student extends Person {
    private String studentId;

    public Student() {
        super("未命名", 0);
    }

    public Student(String name, int age, String studentId) {
        super(name, age);
        this.studentId = studentId;
    }

    public void study() {
        System.out.println(getName() + "正在学习");
    }

    @Override
    public String getInfo() {
        return super.getInfo() + ", 学号: " + studentId;
    }
}

// 抽象类示例
abstract class Shape {
    public abstract double calculateArea();

    public void displayType() {
        System.out.println("这是一个形状");
    }
}

// 抽象类实现
class Circle extends Shape implements Drawable {
    private double radius;

    public Circle(double radius) {
        this.radius = radius;
    }

    @Override
    public double calculateArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public void draw() {
        System.out.println("绘制一个半径为" + radius + "的圆");
    }
}

// 接口示例
interface Drawable {
    void draw();
}

// Java 8 接口默认方法
interface Printable {
    void print();

    default void printToConsole() {
        System.out.println("正在控制台打印");
    }

    static void printVersion() {
        System.out.println("打印接口版本 1.0");
    }
}

// 接口实现
class Document implements Printable {
    private String name;

    public Document(String name) {
        this.name = name;
    }

    @Override
    public void print() {
        System.out.println("打印文档: " + name);
    }
}

// 函数式接口
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}

// 封装示例
class BankAccount {
    private String accountNumber;
    private double balance;

    public BankAccount(String accountNumber, double initialBalance) {
        this.accountNumber = accountNumber;
        this.balance = initialBalance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
        }
    }

    public boolean withdraw(double amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
}

// 静态成员示例
class MathUtils {
    public static final double PI = 3.14159;

    public static int square(int num) {
        return num * num;
    }

    private MathUtils() {
    } // 私有构造器，防止实例化
}

// 静态代码块示例
class StaticDemo {
    static {
        System.out.println("静态代码块示例: 类加载时执行");
    }

    public static void printInfo() {
        System.out.println("静态方法执行");
    }
}

// 内部类示例
class Outer {
    private int outerVar = 10;

    // 成员内部类
    class Inner {
        void display() {
            System.out.println("成员内部类示例: 外部变量值 = " + outerVar);
        }
    }

    // 静态内部类
    static class StaticNested {
        void display() {
            System.out.println("静态内部类示例: 不需要外部类实例");
        }
    }

    void processLocal() {
        int localVar = 20; // 事实上的final变量

        // 局部内部类
        class LocalInner {
            void display() {
                System.out.println("局部内部类示例: 局部变量值 = " + localVar);
            }
        }

        LocalInner local = new LocalInner();
        local.display();
    }
}