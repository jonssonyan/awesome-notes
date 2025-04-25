package com.jonssonyan;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Scanner;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class JavaIO {
    public static void main(String[] args) throws Exception {
        // ==================== 字节流 ====================

        // InputStream - 字节输入流的抽象基类

        // FileInputStream - 从文件读取字节
        try (FileInputStream fis = new FileInputStream("input.txt")) {
            byte[] buffer = new byte[1024];
            int bytesRead = fis.read(buffer);
            System.out.println("读取到 " + bytesRead + " 个字节");
        }

        // ByteArrayInputStream - 从字节数组读取
        byte[] data = "Hello Java IO".getBytes();
        try (ByteArrayInputStream bais = new ByteArrayInputStream(data)) {
            int byteData;
            while ((byteData = bais.read()) != -1) {
                System.out.print((char) byteData);
            }
        }

        // BufferedInputStream - 为输入流提供缓冲功能
        try (BufferedInputStream bis = new BufferedInputStream(
                new FileInputStream("input.txt"))) {
            byte[] buffer = new byte[1024];
            int bytesRead = bis.read(buffer);
            System.out.println("使用缓冲读取到 " + bytesRead + " 个字节");
        }

        // DataInputStream - 读取基本数据类型
        try (DataInputStream dis = new DataInputStream(
                new FileInputStream("data.bin"))) {
            int intValue = dis.readInt();
            double doubleValue = dis.readDouble();
            boolean boolValue = dis.readBoolean();
        }

        // ObjectInputStream - 读取对象
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream("object.dat"))) {
            Object obj = ois.readObject();
            System.out.println("读取对象: " + obj);
        }

        // OutputStream - 字节输出流的抽象基类

        // FileOutputStream - 向文件写入字节
        try (FileOutputStream fos = new FileOutputStream("output.txt")) {
            String text = "Hello, Java IO!";
            fos.write(text.getBytes());
        }

        // FileOutputStream - 追加模式
        try (FileOutputStream fos = new FileOutputStream("output.txt", true)) {
            String text = "\nAppended text";
            fos.write(text.getBytes());
        }

        // ByteArrayOutputStream - 写入到字节数组
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            baos.write("Hello ByteArray".getBytes());
            byte[] byteArray = baos.toByteArray();
            System.out.println(new String(byteArray));
        }

        // BufferedOutputStream - 为输出流提供缓冲功能
        try (BufferedOutputStream bos = new BufferedOutputStream(
                new FileOutputStream("buffered.txt"))) {
            String text = "使用缓冲提高写入效率";
            bos.write(text.getBytes());
        }

        // DataOutputStream - 写入基本数据类型
        try (DataOutputStream dos = new DataOutputStream(
                new FileOutputStream("data.bin"))) {
            dos.writeInt(123);
            dos.writeDouble(45.67);
            dos.writeBoolean(true);
        }

        // ObjectOutputStream - 写入对象
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream("object.dat"))) {
            oos.writeObject(new SerializablePerson("小明", 25));
        }

        // 压缩流
        // GZIP压缩
        try (GZIPOutputStream gzos = new GZIPOutputStream(
                new FileOutputStream("file.gz"));
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(gzos))) {
            writer.write("压缩文本内容");
        }

        // GZIP解压
        try (GZIPInputStream gzis = new GZIPInputStream(
                new FileInputStream("file.gz"));
             BufferedReader reader = new BufferedReader(
                     new InputStreamReader(gzis))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("解压数据: " + line);
            }
        }

        // ==================== 字符流 ====================

        // Reader - 字符输入流的抽象基类

        // FileReader - 从文件读取字符
        try (FileReader fr = new FileReader("reader.txt")) {
            char[] buffer = new char[1024];
            int charsRead = fr.read(buffer);
            System.out.println(new String(buffer, 0, charsRead));
        }

        // BufferedReader - 为字符输入流提供缓冲区
        try (BufferedReader br = new BufferedReader(
                new FileReader("reader.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println("行数据: " + line);
            }
        }

        // InputStreamReader - 字节流到字符流的桥梁
        try (InputStreamReader isr = new InputStreamReader(
                new FileInputStream("input.txt"), StandardCharsets.UTF_8)) {
            char[] buffer = new char[1024];
            int charsRead = isr.read(buffer);
            System.out.println(new String(buffer, 0, charsRead));
        }

        // StringReader - 从字符串读取
        try (StringReader sr = new StringReader("Hello StringReader")) {
            char[] buffer = new char[1024];
            int charsRead = sr.read(buffer);
            System.out.println(new String(buffer, 0, charsRead));
        }

        // Writer - 字符输出流的抽象基类

        // FileWriter - 向文件写入字符
        try (FileWriter fw = new FileWriter("writer.txt")) {
            fw.write("FileWriter示例");
        }

        // FileWriter - 追加模式
        try (FileWriter fw = new FileWriter("writer.txt", true)) {
            fw.write("\n追加内容");
        }

        // BufferedWriter - 为字符输出流提供缓冲区
        try (BufferedWriter bw = new BufferedWriter(
                new FileWriter("buffered_writer.txt"))) {
            bw.write("使用缓冲提高写入效率");
            bw.newLine(); // 写入一个换行符
            bw.write("新的一行");
        }

        // OutputStreamWriter - 字符流到字节流的桥梁
        try (OutputStreamWriter osw = new OutputStreamWriter(
                new FileOutputStream("output.txt"), StandardCharsets.UTF_8)) {
            osw.write("使用指定编码写入字符");
        }

        // StringWriter - 写入到字符串
        try (StringWriter sw = new StringWriter()) {
            sw.write("Hello StringWriter");
            String result = sw.toString();
            System.out.println(result);
        }

        // PrintWriter - 格式化输出
        try (PrintWriter pw = new PrintWriter(new FileWriter("print.txt"))) {
            pw.println("第一行");
            pw.printf("格式化数字: %d, 字符串: %s%n", 123, "Hello");
            pw.println("最后一行");
        }

        // ==================== Scanner类 ====================

        // 从文件中读取
        try (Scanner scanner = new Scanner(new File("input.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                System.out.println("Scanner读取: " + line);
            }
        }

        // 从字符串中读取
        Scanner strScanner = new Scanner("1 2 3 4 5");
        while (strScanner.hasNextInt()) {
            int num = strScanner.nextInt();
            System.out.println("数字: " + num);
        }

        // 使用定界符
        Scanner delimiterScanner = new Scanner("apple,banana,orange");
        delimiterScanner.useDelimiter(",");
        while (delimiterScanner.hasNext()) {
            System.out.println("项目: " + delimiterScanner.next());
        }

        // ==================== NIO (New I/O) ====================

        // Path - 文件路径
        Path path = Paths.get("example.txt");
        System.out.println("文件名: " + path.getFileName());
        System.out.println("绝对路径: " + path.toAbsolutePath());

        // Files - 文件操作工具类

        // 写入文件
        Files.write(Paths.get("nio_write.txt"),
                "NIO写入示例".getBytes(StandardCharsets.UTF_8));

        // 追加内容
        Files.write(Paths.get("nio_write.txt"),
                "\n追加内容".getBytes(StandardCharsets.UTF_8),
                StandardOpenOption.APPEND);

        // 读取所有行
        List<String> lines = Files.readAllLines(Paths.get("nio_write.txt"));
        for (String line : lines) {
            System.out.println("NIO读取: " + line);
        }

        // 读取所有字节
        byte[] bytes = Files.readAllBytes(Paths.get("nio_write.txt"));
        System.out.println("字节内容: " + new String(bytes, StandardCharsets.UTF_8));

        // 创建目录
        Files.createDirectories(Paths.get("nio_dir/subdir"));

        // 复制文件
        Files.copy(Paths.get("nio_write.txt"),
                Paths.get("nio_copy.txt"),
                StandardCopyOption.REPLACE_EXISTING);

        // 移动/重命名文件
        Files.move(Paths.get("nio_copy.txt"),
                Paths.get("nio_moved.txt"),
                StandardCopyOption.REPLACE_EXISTING);

        // 删除文件
        Files.deleteIfExists(Paths.get("nio_moved.txt"));

        // 文件通道
        try (FileChannel sourceChannel = FileChannel.open(Paths.get("nio_write.txt"),
                StandardOpenOption.READ);
             FileChannel destChannel = FileChannel.open(Paths.get("channel_copy.txt"),
                     StandardOpenOption.CREATE, StandardOpenOption.WRITE)) {
            sourceChannel.transferTo(0, sourceChannel.size(), destChannel);
        }

        // 文件访问和属性
        boolean exists = Files.exists(Paths.get("nio_write.txt"));
        boolean isDir = Files.isDirectory(Paths.get("nio_dir"));
        long size = Files.size(Paths.get("nio_write.txt"));
        System.out.println("文件存在: " + exists + ", 是目录: " + isDir + ", 大小: " + size);
    }
}

class SerializablePerson implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private int age;

    public SerializablePerson(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{name='" + name + "', age=" + age + "}";
    }
}