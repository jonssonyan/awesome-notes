package com.jonssonyan;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Properties;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.LinkedBlockingQueue;

public class JavaCollection {
    public static void main(String[] args) {
        // ================ List接口 - 有序集合，允许重复元素 ================

        // ArrayList - 基于动态数组的实现，随机访问快，插入删除慢
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Apple");
        arrayList.add("Banana");
        arrayList.add("Cherry");
        arrayList.add("Apple"); // 允许重复
        System.out.println("ArrayList访问示例: " + arrayList.get(1)); // 索引访问
        arrayList.remove(0); // 移除元素
        arrayList.set(0, "Blueberry"); // 修改元素

        // LinkedList - 基于双向链表的实现，随机访问慢，插入删除快
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("Dog");
        linkedList.add("Cat");
        linkedList.addFirst("Lion"); // 在开头添加
        linkedList.addLast("Tiger"); // 在末尾添加
        linkedList.removeFirst(); // 移除开头
        linkedList.removeLast(); // 移除末尾

        // Vector - 线程安全的ArrayList
        Vector<String> vector = new Vector<>();
        vector.add("Red");
        vector.add("Green");
        vector.add("Blue");

        // Stack - Vector的子类，实现LIFO堆栈
        Stack<String> stack = new Stack<>();
        stack.push("First");
        stack.push("Second");
        System.out.println("Stack顶部: " + stack.peek());
        String popped = stack.pop(); // 返回并移除顶部

        // ================ Set接口 - 不允许重复元素 ================

        // HashSet - 基于哈希表实现，无序
        Set<String> hashSet = new HashSet<>();
        hashSet.add("Apple");
        hashSet.add("Banana");
        hashSet.add("Apple"); // 被忽略，不允许重复
        System.out.println("HashSet大小: " + hashSet.size()); // 只有2个元素

        // LinkedHashSet - 基于链表和哈希表实现，保持插入顺序
        Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("One");
        linkedHashSet.add("Two");
        linkedHashSet.add("Three");

        // TreeSet - 基于红黑树实现，有序集合
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("Zebra");
        treeSet.add("Ant");
        treeSet.add("Dog");
        System.out.println("TreeSet首个元素: " + treeSet.first()); // 按字母排序，输出Ant

        // ================ Map接口 - 键值对映射 ================

        // HashMap - 基于哈希表实现，无序
        Map<Integer, String> hashMap = new HashMap<>();
        hashMap.put(1, "One");
        hashMap.put(2, "Two");
        hashMap.put(3, "Three");
        System.out.println("HashMap值: " + hashMap.get(2));
        hashMap.remove(1); // 移除键为1的项

        // LinkedHashMap - 基于链表和哈希表实现，保持插入顺序
        Map<String, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("One", 1);
        linkedHashMap.put("Two", 2);
        linkedHashMap.put("Three", 3);

        // TreeMap - 基于红黑树实现，按键排序
        TreeMap<String, Double> treeMap = new TreeMap<>();
        treeMap.put("C", 3.0);
        treeMap.put("A", 1.0);
        treeMap.put("B", 2.0);
        System.out.println("TreeMap首个键: " + treeMap.firstKey()); // 输出A

        // Hashtable - 线程安全的HashMap
        Hashtable<String, Integer> hashtable = new Hashtable<>();
        hashtable.put("A", 1);
        hashtable.put("B", 2);

        // Properties - Hashtable的子类，用于处理属性文件
        Properties properties = new Properties();
        properties.setProperty("username", "admin");
        properties.setProperty("password", "123456");
        System.out.println("属性: " + properties.getProperty("username"));

        // ================ Queue接口 - 队列，通常FIFO ================

        // LinkedList实现Queue
        Queue<String> queue = new LinkedList<>();
        queue.offer("First"); // 添加元素
        queue.offer("Second");
        System.out.println("队首: " + queue.peek()); // 查看队首
        String polled = queue.poll(); // 返回并移除队首

        // PriorityQueue - 基于优先级堆的实现
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(5);
        priorityQueue.offer(1);
        priorityQueue.offer(3);
        System.out.println("优先队列首个元素: " + priorityQueue.peek()); // 输出1（最小值）

        // ================ Deque接口 - 双端队列 ================

        // ArrayDeque - 基于动态数组的双端队列
        Deque<String> arrayDeque = new ArrayDeque<>();
        arrayDeque.offerFirst("First");
        arrayDeque.offerLast("Last");
        System.out.println("第一个: " + arrayDeque.peekFirst());
        System.out.println("最后一个: " + arrayDeque.peekLast());

        // ================ 并发集合 - 线程安全的集合 ================

        // ConcurrentHashMap - 线程安全的HashMap
        ConcurrentMap<String, Integer> concurrentMap = new ConcurrentHashMap<>();
        concurrentMap.put("One", 1);
        concurrentMap.put("Two", 2);

        // CopyOnWriteArrayList - 线程安全的ArrayList
        CopyOnWriteArrayList<String> copyOnWriteList = new CopyOnWriteArrayList<>();
        copyOnWriteList.add("Safe");
        copyOnWriteList.add("Thread");

        // CopyOnWriteArraySet - 线程安全的Set
        CopyOnWriteArraySet<String> copyOnWriteSet = new CopyOnWriteArraySet<>();
        copyOnWriteSet.add("Safe");
        copyOnWriteSet.add("Thread");

        // ConcurrentLinkedQueue - 线程安全的队列
        ConcurrentLinkedQueue<String> concurrentQueue = new ConcurrentLinkedQueue<>();
        concurrentQueue.offer("Safe");
        concurrentQueue.offer("Thread");

        // BlockingQueue接口 - 支持阻塞操作的队列
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
        try {
            blockingQueue.put("Element"); // 如果队列满，会阻塞
            String element = blockingQueue.take(); // 如果队列空，会阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // ================ 遍历集合的方式 ================

        // 1. 增强for循环
        System.out.println("\n增强for循环:");
        for (String item : arrayList) {
            System.out.println(item);
        }

        // 2. 迭代器
        System.out.println("\n迭代器:");
        Iterator<String> iterator = linkedList.iterator();
        while (iterator.hasNext()) {
            String item = iterator.next();
            System.out.println(item);
            // iterator.remove(); // 安全删除当前元素
        }

        // 3. forEach方法(Java 8)
        System.out.println("\nforEach方法:");
        hashSet.forEach(item -> System.out.println(item));

        // 4. Map遍历
        System.out.println("\nMap遍历:");
        // 4.1 键值对遍历
        for (Map.Entry<Integer, String> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
        // 4.2 键遍历
        for (Integer key : hashMap.keySet()) {
            System.out.println("Key: " + key);
        }
        // 4.3 值遍历
        for (String value : hashMap.values()) {
            System.out.println("Value: " + value);
        }

        // ================ 集合工具类 ================

        // Collections - 集合工具类
        List<String> list = new ArrayList<>();
        list.add("C");
        list.add("A");
        list.add("B");

        Collections.sort(list); // 排序
        Collections.reverse(list); // 反转
        Collections.shuffle(list); // 随机排序
        String max = Collections.max(list); // 最大值
        String min = Collections.min(list); // 最小值
        Collections.fill(list, "X"); // 填充

        // 创建不可修改的集合
        List<String> unmodifiableList = Collections.unmodifiableList(new ArrayList<>());
        Set<String> unmodifiableSet = Collections.unmodifiableSet(new HashSet<>());
        Map<String, String> unmodifiableMap = Collections.unmodifiableMap(new HashMap<>());

        // 创建同步集合
        List<String> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        Set<String> synchronizedSet = Collections.synchronizedSet(new HashSet<>());
        Map<String, String> synchronizedMap = Collections.synchronizedMap(new HashMap<>());

        // Arrays工具类
        String[] array = {"Apple", "Banana", "Cherry"};
        List<String> arrayAsList = Arrays.asList(array); // 数组转List(固定大小)

        // Java 8 Stream API
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        int sum = numbers.stream()
                .filter(n -> n % 2 == 0) // 过滤
                .mapToInt(Integer::intValue) // 转换
                .sum(); // 求和
        System.out.println("\nStream API求和: " + sum);
    }
}