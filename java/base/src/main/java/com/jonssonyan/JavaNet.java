package com.jonssonyan;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JavaNet {
    public static void main(String[] args) throws Exception {
        // ================ Socket通信 - TCP连接 ================

        // 创建服务器 - 在单独线程中启动
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                ServerSocket serverSocket = new ServerSocket(8080);
                System.out.println("服务器启动在端口8080...");
                Socket clientSocket = serverSocket.accept(); // 阻塞等待客户端连接

                // 读取客户端数据
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String clientMessage = in.readLine();
                System.out.println("从客户端收到: " + clientMessage);

                // 发送响应给客户端
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.println("服务器收到消息: " + clientMessage);

                // 关闭资源
                in.close();
                out.close();
                clientSocket.close();
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // 等待服务器启动
        Thread.sleep(1000);

        // 创建客户端
        try (Socket socket = new Socket("localhost", 8080)) {
            // 发送数据到服务器
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println("Hello, Server! 当前时间: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            // 接收服务器响应
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("服务器响应: " + in.readLine());
        }

        // ================ URL处理 - HTTP请求 ================

        // URL对象 - 表示一个统一资源定位符
        URL url = new URL("https://httpbin.org/get");
        URLConnection connection = url.openConnection();

        // 读取URL内容
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String line;
            StringBuilder content = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                content.append(line);
            }
            System.out.println("URL内容示例: " + content.substring(0, Math.min(50, content.length())) + "...");
        }

        // HttpURLConnection - 发送HTTP GET请求
        URL getUrl = new URL("https://httpbin.org/get?user=jonssonyan");
        HttpURLConnection getConnection = (HttpURLConnection) getUrl.openConnection();
        getConnection.setRequestMethod("GET");

        int responseCode = getConnection.getResponseCode();
        System.out.println("HTTP GET响应码: " + responseCode);

        // HttpURLConnection - 发送HTTP POST请求
        URL postUrl = new URL("https://httpbin.org/post");
        HttpURLConnection postConnection = (HttpURLConnection) postUrl.openConnection();
        postConnection.setRequestMethod("POST");
        postConnection.setRequestProperty("Content-Type", "application/json");
        postConnection.setDoOutput(true);

        // 写入请求体
        String jsonInputString = "{\"name\":\"jonssonyan\",\"date\":\"2025-04-25\"}";
        try (OutputStream os = postConnection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // 读取响应
        try (BufferedReader br = new BufferedReader(new InputStreamReader(postConnection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            System.out.println("HTTP POST响应示例: " + response.substring(0, Math.min(50, response.length())) + "...");
        }

        // ================ UDP通信 - 无连接传输 ================

        // 在单独线程中启动UDP服务器
        executor.submit(() -> {
            try {
                DatagramSocket serverSocket = new DatagramSocket(9090);
                byte[] receiveData = new byte[1024];

                // 接收数据包
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket); // 阻塞等待

                String message = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("UDP服务器收到: " + message);

                // 发送响应
                InetAddress clientAddress = receivePacket.getAddress();
                int clientPort = receivePacket.getPort();
                String responseMsg = "UDP服务器确认收到";
                byte[] sendData = responseMsg.getBytes();

                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
                serverSocket.send(sendPacket);

                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // 等待UDP服务器启动
        Thread.sleep(1000);

        // UDP客户端
        try (DatagramSocket clientSocket = new DatagramSocket()) {
            InetAddress serverAddress = InetAddress.getByName("localhost");
            byte[] sendData = "Hello UDP Server!".getBytes();

            // 发送数据包
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, 9090);
            clientSocket.send(sendPacket);

            // 接收响应
            byte[] receiveData = new byte[1024];
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            clientSocket.receive(receivePacket);

            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("UDP客户端收到响应: " + response);
        }

        // ================ NIO - 非阻塞IO ================

        // 在单独线程中启动NIO服务器
        executor.submit(() -> {
            try {
                // 创建选择器和服务器通道
                Selector selector = Selector.open();
                ServerSocketChannel serverChannel = ServerSocketChannel.open();
                serverChannel.bind(new InetSocketAddress(7070));
                serverChannel.configureBlocking(false); // 设为非阻塞
                serverChannel.register(selector, SelectionKey.OP_ACCEPT);

                System.out.println("NIO服务器启动在端口7070...");

                while (true) {
                    selector.select(); // 阻塞直到有事件发生

                    Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                    while (keyIterator.hasNext()) {
                        SelectionKey key = keyIterator.next();
                        keyIterator.remove();

                        // 处理连接请求
                        if (key.isAcceptable()) {
                            ServerSocketChannel server = (ServerSocketChannel) key.channel();
                            SocketChannel client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                        }

                        // 读取数据
                        if (key.isReadable()) {
                            SocketChannel client = (SocketChannel) key.channel();
                            ByteBuffer buffer = ByteBuffer.allocate(1024);
                            int bytesRead = client.read(buffer);

                            if (bytesRead > 0) {
                                buffer.flip();
                                String message = StandardCharsets.UTF_8.decode(buffer).toString();
                                System.out.println("NIO服务器收到: " + message);

                                // 回复客户端
                                ByteBuffer responseBuffer = ByteBuffer.wrap("NIO服务器确认收到".getBytes());
                                client.write(responseBuffer);
                                client.close();
                                break; // 示例中只处理一个连接
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // 等待NIO服务器启动
        Thread.sleep(1000);

        // NIO客户端
        try (SocketChannel clientChannel = SocketChannel.open()) {
            clientChannel.connect(new InetSocketAddress("localhost", 7070));

            // 发送消息
            ByteBuffer buffer = ByteBuffer.wrap("Hello NIO Server!".getBytes(StandardCharsets.UTF_8));
            clientChannel.write(buffer);

            // 接收响应
            ByteBuffer responseBuffer = ByteBuffer.allocate(1024);
            int bytesRead = clientChannel.read(responseBuffer);
            responseBuffer.flip();

            if (bytesRead > 0) {
                String response = StandardCharsets.UTF_8.decode(responseBuffer).toString();
                System.out.println("NIO客户端收到响应: " + response);
            }
        }

        // ================ 异步网络IO - Java 8 CompletableFuture ================

        // 异步HTTP请求
        CompletableFuture<String> asyncRequest = CompletableFuture.supplyAsync(() -> {
            try {
                URL asyncUrl = new URL("https://httpbin.org/delay/1"); // 延迟1秒的API
                HttpURLConnection connection1 = (HttpURLConnection) asyncUrl.openConnection();
                connection1.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return "请求失败: " + e.getMessage();
            }
        });

        // 处理异步结果
        asyncRequest.thenApply(response -> "处理响应: " + response.substring(0, Math.min(30, response.length())) + "...").thenAccept(System.out::println).exceptionally(ex -> {
            System.err.println("异步请求异常: " + ex.getMessage());
            return null;
        });

        // 等待异步操作完成
        Thread.sleep(2000);

        // ================ 网络工具类 ================

        // InetAddress - 表示IP地址
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println("本机地址: " + localHost.getHostAddress());

        // 解析主机名
        InetAddress[] addresses = InetAddress.getAllByName("www.example.com");
        System.out.println("example.com的IP地址: " + addresses[0].getHostAddress());

        // NetworkInterface - 获取网络接口信息 (Java 8兼容写法)
        List<String> interfaceNames = new ArrayList<>();
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface ni = networkInterfaces.nextElement();
            interfaceNames.add(ni.getName());
        }
        System.out.println("网络接口: " + String.join(", ", interfaceNames.subList(0, Math.min(3, interfaceNames.size()))) + (interfaceNames.size() > 3 ? "..." : ""));

        // 检查网络可达性
        boolean reachable = InetAddress.getByName("www.example.com").isReachable(5000); // 超时5秒
        System.out.println("example.com是否可达: " + reachable);

        // 关闭线程池
        executor.shutdown();
        System.out.println("网络编程示例执行完毕!");
    }
}