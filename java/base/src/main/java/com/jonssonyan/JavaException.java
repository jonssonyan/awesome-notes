package com.jonssonyan;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;

public class JavaException {
    public static void main(String[] args) {
        // ================ 异常体系 - Java异常层次结构 ================

        // Throwable - 所有异常和错误的父类
        Throwable throwable = new Exception("示例异常");
        System.out.println("异常消息: " + throwable.getMessage()); // 获取异常信息
        System.out.println("异常类名: " + throwable.getClass().getName()); // 获取异常类型

        // 检查型异常(Checked Exception) - 编译时必须处理的异常
        try {
            FileReader reader = new FileReader("不存在的文件.txt");
        } catch (FileNotFoundException e) {
            System.out.println("检查型异常示例: " + e.getMessage());
        }

        // 运行时异常(Unchecked Exception) - 编译时可以不处理
        try {
            int result = 10 / 0; // 会抛出ArithmeticException
        } catch (ArithmeticException e) {
            System.out.println("运行时异常示例: " + e.getMessage());
        }

        // ================ try-catch-finally - 基本异常处理结构 ================

        // 基本try-catch结构
        try {
            String str = null;
            str.length(); // 会抛出NullPointerException
        } catch (NullPointerException e) {
            System.out.println("捕获异常: " + e.getClass().getSimpleName());
            e.printStackTrace(); // 打印异常堆栈跟踪
        }

        // 多重catch块 - 捕获不同类型异常
        try {
            int[] array = new int[5];
            array[10] = 50; // 会抛出ArrayIndexOutOfBoundsException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("数组越界异常");
        } catch (Exception e) {
            System.out.println("其他异常");
        }

        // finally块 - 无论是否发生异常都会执行
        FileInputStream fis = null;
        try {
            fis = new FileInputStream("test.txt");
            // 文件处理代码
        } catch (IOException e) {
            System.out.println("IO异常: " + e.getMessage());
        } finally {
            try {
                if (fis != null) {
                    fis.close(); // 确保资源释放
                }
            } catch (IOException e) {
                System.out.println("关闭文件时发生异常");
            }
        }

        // ================ try-with-resources - 自动资源管理 ================

        // 自动关闭资源 - Java 7引入，Java 8改进
        try (
                FileReader reader = new FileReader("config.properties");
                BufferedReader br = new BufferedReader(reader)
        ) {
            String line = br.readLine();
            System.out.println("读取的内容: " + line);
        } catch (IOException e) {
            System.out.println("try-with-resources示例: " + e.getMessage());
        } // 不需要finally块，资源会自动关闭

        // 使用已存在的资源 - Java 9特性，仅作参考
        // Scanner scanner = new Scanner(System.in);
        // try (scanner) {
        //     String input = scanner.nextLine();
        // }

        // ================ 抛出异常 - throws和throw ================

        // throws关键字 - 声明方法可能抛出的异常
        try {
            readFile("data.txt");
        } catch (IOException e) {
            System.out.println("调用方捕获异常: " + e.getMessage());
        }

        // throw关键字 - 手动抛出异常
        try {
            validateAge(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("参数验证异常: " + e.getMessage());
        }

        // ================ 自定义异常 - 创建应用特定异常 ================

        // 自定义检查型异常
        try {
            withdraw(100, 200);
        } catch (InsufficientFundsException e) {
            System.out.println("自定义检查型异常: " + e.getMessage());
            System.out.println("缺少金额: " + e.getAmount());
        }

        // 自定义运行时异常
        try {
            String username = "ad";
            if (username.length() < 3) {
                throw new InvalidUsernameException("用户名太短");
            }
        } catch (InvalidUsernameException e) {
            System.out.println("自定义运行时异常: " + e.getMessage());
        }

        // ================ 异常链 - 异常包装与传递 ================

        // 包装低级异常 - 保留原始异常信息
        try {
            connectToDatabase();
        } catch (ServiceException e) {
            System.out.println("异常链示例 - 高级异常: " + e.getMessage());
            System.out.println("异常链示例 - 原因: " + e.getCause().getMessage());
        }

        // ================ Java 8异步异常处理 ================

        // CompletableFuture异常处理
        CompletableFuture<String> future = CompletableFuture
                .supplyAsync(() -> {
                    if (Math.random() > 0.5) {
                        throw new RuntimeException("异步操作失败");
                    }
                    return "操作成功";
                })
                .exceptionally(ex -> {
                    System.out.println("CompletableFuture异常处理: " + ex.getMessage());
                    return "默认值";
                });

        System.out.println("异步结果: " + future.join());

        // ================ 最佳实践 - 异常处理建议 ================

        // 优先捕获具体异常
        try {
            riskyOperation();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("处理数组索引异常");
        } catch (NumberFormatException e) {
            System.out.println("处理数字格式异常");
        } catch (RuntimeException e) {
            System.out.println("处理其他运行时异常");
        } catch (Exception e) {
            System.out.println("处理所有其他异常");
        }

        // 不要捕获Throwable
        try {
            // 一些可能抛出错误的代码
            riskyOperation();
        } catch (Exception e) { // 捕获Exception而不是Throwable
            System.out.println("异常处理最佳实践示例");
        }
    }

    // throws示例 - 声明方法可能抛出的异常
    public static String readFile(String fileName) throws IOException {
        if (!new File(fileName).exists()) {
            throw new FileNotFoundException("文件不存在: " + fileName);
        }
        return "文件内容";
    }

    // throw示例 - 手动抛出异常
    public static void validateAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("年龄不能为负数: " + age);
        }
    }

    // 自定义检查型异常
    public static void withdraw(double balance, double amount) throws InsufficientFundsException {
        if (amount > balance) {
            throw new InsufficientFundsException(amount - balance);
        }
    }

    // 异常链示例
    public static void connectToDatabase() throws ServiceException {
        try {
            // 尝试连接数据库
            throw new SQLException("数据库连接失败");
        } catch (SQLException e) {
            // 包装并重新抛出高级异常
            throw new ServiceException("服务操作失败", e);
        }
    }

    // 可能抛出多种异常的方法
    public static void riskyOperation() {
        if (Math.random() < 0.3) {
            throw new ArrayIndexOutOfBoundsException("数组越界");
        } else if (Math.random() < 0.6) {
            throw new NumberFormatException("数字格式错误");
        } else if (Math.random() < 0.9) {
            throw new RuntimeException("其他运行时异常");
        }
    }
}

// 自定义检查型异常
class InsufficientFundsException extends Exception {
    private double amount;

    public InsufficientFundsException(double amount) {
        super("资金不足，还需要: " + amount + "元");
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}

// 自定义运行时异常
class InvalidUsernameException extends RuntimeException {
    public InvalidUsernameException(String message) {
        super(message);
    }
}

// 服务异常 - 用于异常链示范
class ServiceException extends Exception {
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}