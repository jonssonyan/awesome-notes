package com.jonssonyan;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * Java 泛型示例
 * 这个类展示了 Java 泛型的主要特性和用法
 */
public class JavaGenerics {
    public static void main(String[] args) {
        // ================ 基本泛型类用法 ================
        System.out.println("===== 基本泛型类用法 =====");

        // 创建泛型类实例，指定具体类型
        Box<String> stringBox = new Box<>();
        stringBox.set("Hello Generics");
        System.out.println("字符串盒子内容: " + stringBox.get());

        Box<Integer> integerBox = new Box<>();
        integerBox.set(100);
        System.out.println("整数盒子内容: " + integerBox.get());

        // ================ 泛型方法 ================
        System.out.println("\n===== 泛型方法 =====");

        // 调用泛型方法，编译器会根据参数类型推断泛型类型
        String[] strArray = {"Apple", "Banana", "Cherry"};
        Integer[] intArray = {1, 2, 3, 4, 5};

        List<String> strList = ArrayUtils.arrayToList(strArray);
        System.out.println("字符串数组转列表: " + strList);

        List<Integer> intList = ArrayUtils.arrayToList(intArray);
        System.out.println("整数数组转列表: " + intList);

        // ================ 类型边界 ================
        System.out.println("\n===== 类型边界 =====");

        // 使用有边界的泛型方法
        System.out.println("数值比较: " + Comparator.compareNumbers(10.5, 10.2));

        // ================ 通配符 ================
        System.out.println("\n===== 通配符 =====");

        List<Integer> integerList = Arrays.asList(1, 2, 3);
        List<Double> doubleList = Arrays.asList(1.1, 2.2, 3.3);
        List<String> stringList = Arrays.asList("a", "b", "c");

        // 使用无界通配符
        System.out.println("打印整数列表:");
        WildcardDemo.printList(integerList);

        System.out.println("打印双精度列表:");
        WildcardDemo.printList(doubleList);

        System.out.println("打印字符串列表:");
        WildcardDemo.printList(stringList);

        // 使用上界通配符
        System.out.println("\n数值列表总和: " + WildcardDemo.sumOfList(integerList));
        System.out.println("双精度列表总和: " + WildcardDemo.sumOfList(doubleList));
        // 下面这行会导致编译错误，因为String不是Number的子类
        // WildcardDemo.sumOfList(stringList);

        // ================ 泛型与继承 ================
        System.out.println("\n===== 泛型与继承 =====");

        Box<Number> numberBox = new Box<>();
        numberBox.set(100);
        // 演示协变性问题 - 如果不用通配符，以下代码无法工作
        // Box<Integer> intBox = numberBox; // 编译错误！

        // ================ 类型擦除 ================
        System.out.println("\n===== 类型擦除 =====");

        Box<String> box1 = new Box<>();
        Box<Integer> box2 = new Box<>();

        System.out.println("box1的类: " + box1.getClass().getName());
        System.out.println("box2的类: " + box2.getClass().getName());
        System.out.println("box1和box2类相同? " + (box1.getClass() == box2.getClass()));

        // ================ 泛型函数接口 ================
        System.out.println("\n===== 泛型函数接口 =====");

        // Java 8 中的函数接口使用泛型
        Function<String, Integer> stringLength = s -> s.length();
        System.out.println("字符串长度函数: " + stringLength.apply("Hello"));

        // 自定义泛型函数接口
        Transformer<String, Integer> lengthTransformer = s -> s.length();
        System.out.println("字符串长度变换器: " + lengthTransformer.transform("Generics"));

        // ================ 泛型实践 - Pair类 ================
        System.out.println("\n===== 泛型实践 - Pair类 =====");

        Pair<String, Integer> person = new Pair<>("Alice", 25);
        System.out.println("人员信息: " + person.getKey() + ", " + person.getValue());
    }
}

/**
 * 泛型类示例
 *
 * @param <T> 盒子中存储的对象类型
 */
class Box<T> {
    private T t;

    public void set(T t) {
        this.t = t;
    }

    public T get() {
        return t;
    }
}

/**
 * 泛型方法工具类
 */
class ArrayUtils {
    /**
     * 泛型方法示例：将数组转换为ArrayList
     *
     * @param <T>   数组元素类型
     * @param array 要转换的数组
     * @return 包含数组所有元素的ArrayList
     */
    public static <T> List<T> arrayToList(T[] array) {
        List<T> list = new ArrayList<>();
        for (T item : array) {
            list.add(item);
        }
        return list;
    }
}

/**
 * 类型边界示例类
 */
class Comparator {
    /**
     * 带有上界通配符的泛型方法
     *
     * @param <T> 必须是Number的子类
     * @param a   第一个数
     * @param b   第二个数
     * @return 两数比较结果
     */
    public static <T extends Number & Comparable<T>> int compareNumbers(T a, T b) {
        return a.compareTo(b);
    }
}

/**
 * 通配符示例类
 */
class WildcardDemo {
    /**
     * 使用无界通配符
     *
     * @param list 任意类型的列表
     */
    public static void printList(List<?> list) {
        for (Object elem : list) {
            System.out.print(elem + " ");
        }
        System.out.println();
    }

    /**
     * 使用上界通配符
     *
     * @param list 数值类型列表
     * @return 列表元素总和
     */
    public static double sumOfList(List<? extends Number> list) {
        double sum = 0.0;
        for (Number n : list) {
            sum += n.doubleValue();
        }
        return sum;
    }

    /**
     * 使用下界通配符示例
     *
     * @param list 整数或其父类型的列表
     */
    public static void addNumbers(List<? super Integer> list) {
        for (int i = 1; i <= 5; i++) {
            list.add(i);
        }
    }
}

/**
 * 自定义泛型函数接口
 *
 * @param <T> 输入类型
 * @param <R> 输出类型
 */
@FunctionalInterface
interface Transformer<T, R> {
    R transform(T t);
}

/**
 * 泛型实践 - 键值对类
 *
 * @param <K> 键的类型
 * @param <V> 值的类型
 */
class Pair<K, V> {
    private K key;
    private V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }
}