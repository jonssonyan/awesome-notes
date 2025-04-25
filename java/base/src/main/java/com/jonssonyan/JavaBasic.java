package com.jonssonyan;

public class JavaBasic {
    public static void main(String[] args) {
        // 输出语句
        System.out.println("Hello World");  // 打印并换行
        System.out.print("Hello ");         // 打印不换行
        System.out.printf("Number: %d\n", 10); // 格式化打印

        // 注释
        // 单行注释
        /* 多行
           注释 */
        /** 文档注释
         * @author Author
         */

        // 基本数据类型
        byte by = 127;             // 8位有符号整数，-128到127
        short sh = 32767;          // 16位有符号整数，-32768到32767
        int i = 2147483647;        // 32位有符号整数，-2^31到2^31-1
        long l = 9223372036854775807L; // 64位有符号整数，需要L后缀
        float f = 3.14F;           // 32位浮点数，需要F后缀
        double d = 3.14159;        // 64位浮点数
        char ch = 'A';             // 16位Unicode字符
        boolean bool = true;       // 布尔值，true或false

        // 包装类
        Byte byObj = 127;
        Short shObj = 32767;
        Integer iObj = 100;
        Long lObj = 100L;
        Float fObj = 3.14F;
        Double dObj = 3.14;
        Character chObj = 'A';
        Boolean boolObj = true;

        // 变量与常量
        int number;        // 声明变量
        number = 10;       // 赋值
        int anotherNumber = 20; // 声明并初始化
        final double PI = 3.14159; // 常量，不可修改

        // 字符串
        String greeting = "Hello";
        String name = "World";
        String message = greeting + " " + name; // 字符串连接
        int length = greeting.length();         // 字符串长度
        char firstChar = greeting.charAt(0);    // 获取字符
        String sub = greeting.substring(1, 3);  // 子字符串
        boolean contains = greeting.contains("el"); // 包含判断

        // 数组
        int[] numbers = new int[5];      // 声明并创建数组
        numbers[0] = 1;                  // 赋值
        int[] primes = {2, 3, 5, 7, 11}; // 声明并初始化
        int arrayLength = primes.length; // 获取数组长度

        // 多维数组
        int[][] matrix = new int[3][3];
        matrix[0][0] = 1;
        int[][] jaggedArray = {{1, 2}, {3, 4, 5}, {6}};

        // 运算符
        int a = 10, b = 3;
        int sum = a + b;      // 加法
        int diff = a - b;     // 减法
        int product = a * b;  // 乘法
        int quotient = a / b; // 整数除法
        int remainder = a % b; // 取余
        a++;                  // 自增
        b--;                  // 自减

        // 比较运算符
        boolean isEqual = (a == b);       // 等于
        boolean isNotEqual = (a != b);    // 不等于
        boolean isGreater = (a > b);      // 大于
        boolean isLessEqual = (a <= b);   // 小于等于

        // 逻辑运算符
        boolean andResult = (true && false); // 与
        boolean orResult = (true || false);  // 或
        boolean notResult = !true;           // 非

        // 位运算符
        int bitwiseAnd = a & b;    // 按位与
        int bitwiseOr = a | b;     // 按位或
        int bitwiseXor = a ^ b;    // 按位异或
        int bitwiseNot = ~a;       // 按位取反
        int leftShift = a << 2;    // 左移
        int rightShift = a >> 2;   // 右移(有符号)
        int zeroRightShift = a >>> 2; // 右移(无符号)

        // 条件语句
        if (a > b) {
            System.out.println("a is greater");
        } else if (a == b) {
            System.out.println("a equals b");
        } else {
            System.out.println("a is smaller");
        }

        // switch语句
        switch (a) {
            case 1:
                System.out.println("One");
                break;
            case 2:
                System.out.println("Two");
                break;
            default:
                System.out.println("Other number");
        }

        // 三元运算符
        String result = (a > b) ? "a is greater" : "a is not greater";

        // 循环
        // for循环
        for (int j = 0; j < 5; j++) {
            System.out.println(j);
        }

        // while循环
        int k = 0;
        while (k < 5) {
            System.out.println(k);
            k++;
        }

        // do-while循环
        int m = 0;
        do {
            System.out.println(m);
            m++;
        } while (m < 5);

        // for-each循环
        for (int num : primes) {
            System.out.println(num);
        }

        // 控制语句
        for (int n = 0; n < 10; n++) {
            if (n == 3) continue; // 跳过当前迭代
            if (n == 8) break;    // 终止循环
            System.out.println(n);
        }
    }

    // 方法定义
    public static int add(int a, int b) {
        return a + b;
    }

    // 方法重载
    public static double add(double a, double b) {
        return a + b;
    }

    // 可变参数
    public static int sum(int... numbers) {
        int total = 0;
        for (int num : numbers) {
            total += num;
        }
        return total;
    }
}