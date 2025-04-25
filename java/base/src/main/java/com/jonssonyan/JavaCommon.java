package com.jonssonyan;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.Random;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaCommon {
    public static void main(String[] args) throws IOException {
        // ================ String类 - 字符串操作 ================

        // 字符串创建
        String str1 = "Hello";
        String str2 = new String("World");
        String str3 = String.join(" ", str1, str2); // Java 8 字符串连接
        System.out.println("字符串连接: " + str3); // Hello World

        // 字符串操作
        String text = "  Java Programming  ";
        System.out.println("字符串长度: " + text.length()); // 获取长度
        System.out.println("字符串裁剪: " + text.trim()); // 去除首尾空格
        System.out.println("字符串替换: " + text.replace("Java", "Python")); // 替换
        System.out.println("字符串分割: " + Arrays.toString(text.split(" "))); // 分割为数组
        System.out.println("字符串截取: " + text.substring(2, 6)); // 提取子串
        System.out.println("字符查找: " + text.indexOf("Pro")); // 查找子串位置
        System.out.println("字符串比较: " + "abc".compareTo("abd")); // 比较字符串
        System.out.println("字符串转换: " + text.toUpperCase()); // 转大写

        // StringJoiner - Java 8字符串连接器
        StringJoiner joiner = new StringJoiner(", ", "[", "]");
        joiner.add("苹果").add("香蕉").add("橙子");
        System.out.println("StringJoiner示例: " + joiner); // [苹果, 香蕉, 橙子]

        // String.join - Java 8字符串静态连接方法
        String joined = String.join("-", "2025", "04", "25");
        System.out.println("String.join示例: " + joined); // 2025-04-25

        // ================ 包装类 - 基本类型的对象封装 ================

        // Integer - 整数包装类
        Integer int1 = 100;
        Integer int2 = Integer.valueOf(200);
        int primitiveInt = int1.intValue(); // 转为基本类型
        System.out.println("整数解析: " + Integer.parseInt("123")); // 字符串解析为整数
        System.out.println("二进制表示: " + Integer.toBinaryString(10)); // 转为二进制
        System.out.println("十六进制表示: " + Integer.toHexString(255)); // 转为十六进制
        System.out.println("整数比较: " + Integer.compare(10, 20)); // 比较整数

        // Double - 浮点数包装类
        Double double1 = 123.45;
        Double double2 = Double.valueOf("67.89");
        System.out.println("浮点数解析: " + Double.parseDouble("3.14")); // 字符串解析为浮点数
        System.out.println("是否为NaN: " + Double.isNaN(double1)); // 检查是否为非数字

        // Boolean - 布尔包装类
        Boolean bool1 = Boolean.TRUE;
        Boolean bool2 = Boolean.valueOf("false");
        System.out.println("布尔解析: " + Boolean.parseBoolean("true")); // 字符串解析为布尔值

        // Character - 字符包装类
        Character char1 = 'A';
        System.out.println("是否字母: " + Character.isLetter(char1)); // 检查是否为字母
        System.out.println("是否数字: " + Character.isDigit('9')); // 检查是否为数字
        System.out.println("转为小写: " + Character.toLowerCase('B')); // 转为小写

        // ================ 数学相关类 ================

        // Math - 数学运算
        System.out.println("绝对值: " + Math.abs(-10.5)); // 绝对值
        System.out.println("四舍五入: " + Math.round(3.7)); // 四舍五入
        System.out.println("向上取整: " + Math.ceil(3.1)); // 向上取整
        System.out.println("向下取整: " + Math.floor(3.9)); // 向下取整
        System.out.println("最大值: " + Math.max(10, 20)); // 最大值
        System.out.println("最小值: " + Math.min(10, 20)); // 最小值
        System.out.println("幂运算: " + Math.pow(2, 3)); // 幂运算
        System.out.println("平方根: " + Math.sqrt(16)); // 平方根
        System.out.println("随机数: " + Math.random()); // 0.0-1.0之间的随机数

        // BigDecimal - 高精度计算
        BigDecimal bd1 = new BigDecimal("123.456");
        BigDecimal bd2 = new BigDecimal("7.89");
        System.out.println("BigDecimal加法: " + bd1.add(bd2)); // 加法
        System.out.println("BigDecimal减法: " + bd1.subtract(bd2)); // 减法
        System.out.println("BigDecimal乘法: " + bd1.multiply(bd2)); // 乘法
        System.out.println("BigDecimal除法: " + bd1.divide(bd2, 2, RoundingMode.HALF_UP)); // 除法
        System.out.println("BigDecimal比较: " + bd1.compareTo(bd2)); // 比较

        // Random - 随机数生成
        Random random = new Random();
        System.out.println("随机整数: " + random.nextInt(100)); // 0-99之间的随机整数
        System.out.println("随机布尔值: " + random.nextBoolean()); // 随机布尔值
        System.out.println("随机浮点数: " + random.nextDouble()); // 0.0-1.0之间的随机浮点数

        // ThreadLocalRandom - Java 7引入的线程安全随机数
        ThreadLocalRandom threadRandom = ThreadLocalRandom.current();
        System.out.println("线程安全随机整数: " + threadRandom.nextInt(10, 20)); // 10-19之间的随机整数
        System.out.println("线程安全随机长整数: " + threadRandom.nextLong(1000, 2000)); // 1000-1999之间的随机长整数

        // ================ 日期时间类 ================

        // 旧的日期API
        Date now = new Date();
        System.out.println("当前日期时间: " + now); // 当前日期时间

        SimpleDateFormat oldFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("格式化日期: " + oldFormatter.format(now)); // 格式化日期输出

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 5); // 当前日期加5天
        System.out.println("5天后: " + oldFormatter.format(calendar.getTime()));

        // Java 8日期时间API
        LocalDate today = LocalDate.now();
        System.out.println("当前日期: " + today); // 当前日期

        LocalTime time = LocalTime.now();
        System.out.println("当前时间: " + time); // 当前时间

        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println("当前日期时间: " + dateTime); // 当前日期时间

        // 日期时间格式化
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("格式化日期时间: " + dateTime.format(formatter)); // 格式化输出

        // 日期计算
        LocalDate futureDate = today.plusDays(7); // 当前日期加7天
        System.out.println("7天后: " + futureDate);
        System.out.println("是否是闰年: " + futureDate.isLeapYear()); // 检查是否闰年

        // 日期比较
        Period period = Period.between(today, futureDate);
        System.out.println("日期差: " + period.getDays() + "天"); // 计算日期差

        // 时间比较
        LocalTime laterTime = time.plusHours(2);
        Duration duration = Duration.between(time, laterTime);
        System.out.println("时间差: " + duration.toMinutes() + "分钟"); // 计算时间差

        // 时区处理
        ZonedDateTime tokyoTime = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
        System.out.println("东京时间: " + tokyoTime.format(formatter));

        // 固定时刻点
        Instant instant = Instant.now();
        System.out.println("当前时刻: " + instant); // UTC时间戳

        // 日期解析
        LocalDate parsedDate = LocalDate.parse("2025-04-25");
        System.out.println("解析的日期: " + parsedDate);

        // 时间单位
        LocalDateTime nextWeek = dateTime.plus(1, ChronoUnit.WEEKS);
        System.out.println("一周后: " + nextWeek.format(formatter));

        // ================ 格式化类 ================

        // DecimalFormat - 数字格式化
        DecimalFormat df = new DecimalFormat("#,###.##");
        System.out.println("格式化数字: " + df.format(12345.678)); // 12,345.68

        // 货币格式化
        DecimalFormat currencyFormatter = new DecimalFormat("¤ #,###.00");
        System.out.println("格式化货币: " + currencyFormatter.format(9876.54)); // ¤ 9,876.54

        // 百分比格式化
        DecimalFormat percentFormatter = new DecimalFormat("#.##%");
        System.out.println("格式化百分比: " + percentFormatter.format(0.1234)); // 12.34%

        // ================ 系统相关类 ================

        // System - 系统操作
        System.out.println("系统属性-Java版本: " + System.getProperty("java.version")); // 获取Java版本
        System.out.println("系统属性-操作系统: " + System.getProperty("os.name")); // 获取操作系统名称
        System.out.println("系统属性-用户目录: " + System.getProperty("user.home")); // 获取用户目录
        System.out.println("当前时间毫秒数: " + System.currentTimeMillis()); // 获取当前时间毫秒数
        System.out.println("环境变量PATH: " + System.getenv("PATH")); // 获取环境变量

        // Runtime - 运行时环境
        Runtime runtime = Runtime.getRuntime();
        System.out.println("可用处理器数量: " + runtime.availableProcessors()); // 可用处理器数量
        System.out.println("JVM总内存: " + runtime.totalMemory() / (1024 * 1024) + "MB"); // JVM总内存
        System.out.println("JVM可用内存: " + runtime.freeMemory() / (1024 * 1024) + "MB"); // JVM可用内存

        // ================ 实用工具类 ================

        // UUID - 通用唯一标识符
        UUID uuid = UUID.randomUUID();
        System.out.println("UUID示例: " + uuid); // 随机UUID

        // Objects - 对象工具类
        String nullValue = null;
        String notNullValue = "jonssonyan";
        System.out.println("空值判断: " + Objects.isNull(nullValue)); // 检查是否为null
        System.out.println("非空值判断: " + Objects.nonNull(notNullValue)); // 检查是否不为null
        System.out.println("默认值替换: " + Objects.toString(nullValue, "默认值")); // null替换为默认值
        System.out.println("哈希码计算: " + Objects.hash("jonssonyan", 2025, 4, 25)); // 计算组合哈希码

        // Arrays - 数组工具类
        int[] numbers = {5, 3, 8, 1, 7, 2};
        Arrays.sort(numbers); // 数组排序
        System.out.println("排序后数组: " + Arrays.toString(numbers)); // 数组内容转字符串

        String[] fruits = new String[3];
        Arrays.fill(fruits, "苹果"); // 填充数组
        System.out.println("填充数组: " + Arrays.toString(fruits));

        int[] numbersCopy = Arrays.copyOf(numbers, numbers.length); // 数组复制
        System.out.println("复制的数组: " + Arrays.toString(numbersCopy));

        System.out.println("二分查找: " + Arrays.binarySearch(numbers, 7)); // 二分查找

        // ================ 正则表达式 ================

        // Pattern和Matcher - 正则表达式
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher("jonssonyan@example.com");
        System.out.println("邮箱格式是否正确: " + matcher.matches()); // 检查邮箱格式

        String text1 = "联系方式: 电话 123-456-7890, 邮箱 contact@example.com";
        Pattern phonePattern = Pattern.compile("\\d{3}-\\d{3}-\\d{4}");
        Matcher phoneMatcher = phonePattern.matcher(text1);
        while (phoneMatcher.find()) {
            System.out.println("找到电话号码: " + phoneMatcher.group()); // 提取电话号码
        }

        // String的正则表达式方法
        String text2 = "Java,Python,C++,JavaScript";
        String[] languages = text2.split(","); // 使用正则分割
        System.out.println("分割结果: " + Arrays.toString(languages));

        String formatted = "JavaPROGRAMMINGuage".replaceAll("[A-Z]+", "_$0_"); // 替换匹配项
        System.out.println("正则替换: " + formatted); // Java_PROGRAMMING_uage

        // ================ 文件操作类 ================

        // File - 传统文件操作
        File file = new File("example.txt");
        System.out.println("文件是否存在: " + file.exists()); // 检查文件是否存在
        System.out.println("是否为目录: " + file.isDirectory()); // 检查是否为目录
        System.out.println("文件大小: " + (file.exists() ? file.length() + "字节" : "文件不存在")); // 获取文件大小
        System.out.println("文件绝对路径: " + file.getAbsolutePath()); // 获取绝对路径

        // Java NIO Files - Java 7引入的新文件API
        if (!Files.exists(Paths.get("temp.txt"))) {
            Files.createFile(Paths.get("temp.txt")); // 创建文件
            System.out.println("创建了temp.txt文件");
        }

        Files.write(Paths.get("temp.txt"), "Hello World".getBytes()); // 写入文件
        System.out.println("文件内容: " + new String(Files.readAllBytes(Paths.get("temp.txt")))); // 读取文件

        // Files工具方法
        System.out.println("文件大小(NIO): " + Files.size(Paths.get("temp.txt")) + "字节"); // 文件大小
        System.out.println("是否可读: " + Files.isReadable(Paths.get("temp.txt"))); // 检查文件是否可读

        // 最后清理
        Files.delete(Paths.get("temp.txt")); // 删除文件
        System.out.println("已删除temp.txt文件");

        // ================ 区域和国际化 ================

        // Locale - 区域设置
        Locale defaultLocale = Locale.getDefault();
        System.out.println("默认区域: " + defaultLocale.getDisplayName()); // 获取默认区域

        Locale chinaLocale = Locale.CHINA;
        System.out.println("中国区域: " + chinaLocale.getDisplayCountry()); // 获取国家名称

        // 使用特定区域的格式化
        SimpleDateFormat chineseFormatter = new SimpleDateFormat("yyyy年MM月dd日 EEEE", Locale.CHINESE);
        System.out.println("中文格式日期: " + chineseFormatter.format(new Date()));
    }
}