package com.jonssonyan;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class JavaThread {
    public static void main(String[] args) throws Exception {
        // ================ 基本线程创建与管理 ================

        // Thread类 - 创建线程的基本方式
        Thread thread1 = new Thread(() -> {
            System.out.println("线程1正在运行: " + Thread.currentThread().getName());
        });
        thread1.start(); // 启动线程
        thread1.join();  // 等待线程结束

        // Runnable接口 - 分离任务和线程
        Runnable task = () -> System.out.println("Runnable任务执行");
        new Thread(task).start();

        // ================ Callable和Future - 可返回结果的任务 ================

        // Callable - 带返回值的任务
        Callable<String> callable = () -> {
            Thread.sleep(1000);
            return "Callable任务结果";
        };

        // Future - 获取异步计算结果
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> future = executor.submit(callable);
        System.out.println("Future结果: " + future.get()); // 阻塞等待结果

        // ================ Java 8 并发增强功能 ================

        // CompletableFuture - 支持组合式异步编程
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            return "第一阶段";
        }).thenApply(result -> {
            return result + " -> 第二阶段";
        }).thenApply(result -> {
            return result + " -> 完成";
        });
        System.out.println("CompletableFuture结果: " + cf.get());

        // 并行流 - 基于Fork/Join框架的数据并行处理
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        long count = numbers.parallelStream()
                .filter(n -> n % 2 == 0)
                .count();
        System.out.println("并行流处理-偶数个数: " + count);

        // ================ 线程池 ================

        // 固定大小线程池
        ExecutorService fixedPool = Executors.newFixedThreadPool(2);
        for (int i = 0; i < 5; i++) {
            final int taskId = i;
            fixedPool.execute(() -> System.out.println("固定线程池-任务" + taskId +
                    " 由线程" + Thread.currentThread().getName() + "执行"));
        }

        // 缓存线程池 - 按需创建线程
        ExecutorService cachedPool = Executors.newCachedThreadPool();

        // 定时任务线程池
        ScheduledExecutorService scheduledPool = Executors.newScheduledThreadPool(1);
        scheduledPool.schedule(() -> System.out.println("3秒后执行"),
                3, TimeUnit.SECONDS);
        scheduledPool.scheduleAtFixedRate(() -> System.out.println("每2秒执行一次"),
                0, 2, TimeUnit.SECONDS);

        // ================ 线程同步机制 ================

        // synchronized关键字 - 内置锁
        synchronized (JavaThread.class) {
            System.out.println("进入同步代码块");
        }

        // Lock接口 - 显式锁
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            System.out.println("获取显式锁");
        } finally {
            lock.unlock(); // 必须在finally块中释放
        }

        // ReadWriteLock - 读写锁
        ReadWriteLock rwLock = new ReentrantReadWriteLock();
        rwLock.readLock().lock();  // 多个读线程可同时获取
        rwLock.readLock().unlock();

        // 等待/通知机制
        Object monitor = new Object();
        synchronized (monitor) {
            monitor.wait(1000);  // 等待通知或超时
            monitor.notify();    // 通知等待的线程
        }

        // ================ 线程安全集合 ================

        // ConcurrentHashMap - 线程安全的HashMap
        ConcurrentHashMap<String, String> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("key", "value");

        // CopyOnWriteArrayList - 写时复制List
        CopyOnWriteArrayList<String> copyOnWriteList = new CopyOnWriteArrayList<>();
        copyOnWriteList.add("安全添加");

        // ================ 线程同步工具类 ================

        // CountDownLatch - 等待多个操作完成
        CountDownLatch latch = new CountDownLatch(2);
        new Thread(() -> {
            System.out.println("CountDownLatch任务1完成");
            latch.countDown();
        }).start();
        new Thread(() -> {
            System.out.println("CountDownLatch任务2完成");
            latch.countDown();
        }).start();
        latch.await();  // 等待计数为0

        // CyclicBarrier - 线程同步屏障
        CyclicBarrier barrier = new CyclicBarrier(2, () ->
                System.out.println("CyclicBarrier - 所有线程到达屏障点"));

        // Semaphore - 信号量控制访问
        Semaphore semaphore = new Semaphore(3);  // 3个许可证
        semaphore.acquire();  // 获取许可
        semaphore.release();  // 释放许可

        // 关闭线程池
        executor.shutdown();
        fixedPool.shutdown();
        cachedPool.shutdown();
        scheduledPool.shutdown();
    }
}
