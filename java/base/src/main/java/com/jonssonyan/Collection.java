package com.jonssonyan;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.Vector;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Collection {
    public static void main(String[] args) {
        // List接口 - 有序集合，允许重复元素

        // ArrayList - 基于动态数组的实现
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Apple");
        arrayList.add("Banana");
        arrayList.add("Cherry");
        arrayList.add("Apple"); // 允许重复
        System.out.println(arrayList.get(1)); // 索引访问
        arrayList.remove(0); // 移除元素
        arrayList.set(0, "Blueberry"); // 修改元素

        // 遍历List
        for (String fruit : arrayList) {
            System.out.println(fruit);
        }

        // LinkedList - 基于双向链表的实现
        LinkedList<String> linkedList = new LinkedList<>();
        linkedList.add("Dog");
        linkedList.add("Cat");
        linkedList.addFirst("Lion"); // 在头部添加
        linkedList.addLast("Tiger"); // 在尾部添加
        linkedList.removeFirst(); // 移除头部
        linkedList.removeLast(); // 移除尾部

        // Vector - 线程安全的动态数组
        Vector<String> vector = new Vector<>();
        vector.add("One");
        vector.add("Two");

        // Stack - 后进先出(LIFO)栈
        Stack<String> stack = new Stack<>();
        stack.push("First"); // 压栈
        stack.push("Second");
        stack.push("Third");
        System.out.println(stack.pop()); // 弹栈 - Third
        System.out.println(stack.peek()); // 查看栈顶 - Second

        // Set接口 - 不允许重复元素

        // HashSet - 基于哈希表的实现，无序
        Set<String> hashSet = new HashSet<>();
        hashSet.add("Apple");
        hashSet.add("Banana");
        hashSet.add("Cherry");
        hashSet.add("Apple"); // 重复元素不会被添加
        System.out.println(hashSet.size()); // 输出3

        // LinkedHashSet - 保持插入顺序的HashSet
        Set<String> linkedHashSet = new LinkedHashSet<>();
        linkedHashSet.add("One");
        linkedHashSet.add("Two");
        linkedHashSet.add("Three");

        // TreeSet - 有序Set，基于红黑树
        TreeSet<String> treeSet = new TreeSet<>();
        treeSet.add("Banana");
        treeSet.add("Apple");
        treeSet.add("Cherry");
        // 自然排序: Apple, Banana, Cherry
        System.out.println(treeSet.first()); // 最小元素 - Apple
        System.out.println(treeSet.last()); // 最大元素 - Cherry

        // 自定义排序的TreeSet
        TreeSet<Person> personSet = new TreeSet<>((p1, p2) -> p1.getAge() - p2.getAge());
        personSet.add(new Person("Alice", 30));
        personSet.add(new Person("Bob", 25));

        // Queue接口 - 队列

        // LinkedList实现Queue
        Queue<String> queue = new LinkedList<>();
        queue.offer("First"); // 添加元素
        queue.offer("Second");
        System.out.println(queue.peek()); // 查看队头 - First
        System.out.println(queue.poll()); // 移除并返回队头 - First

        // PriorityQueue - 优先级队列
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>();
        priorityQueue.offer(5);
        priorityQueue.offer(2);
        priorityQueue.offer(8);
        System.out.println(priorityQueue.poll()); // 输出最小值2

        // Deque接口 - 双端队列
        Deque<String> deque = new ArrayDeque<>();
        deque.offerFirst("First");
        deque.offerLast("Last");
        System.out.println(deque.peekFirst()); // First
        System.out.println(deque.peekLast()); // Last

        // Map接口 - 键值对映射

        // HashMap - 基于哈希表的实现
        Map<String, Integer> hashMap = new HashMap<>();
        hashMap.put("Alice", 25);
        hashMap.put("Bob", 30);
        hashMap.put("Charlie", 35);
        System.out.println(hashMap.get("Bob")); // 30
        hashMap.remove("Alice"); // 移除键值对

        // 遍历Map
        for (Map.Entry<String, Integer> entry : hashMap.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        // LinkedHashMap - 保持插入顺序的HashMap
        Map<String, Integer> linkedHashMap = new LinkedHashMap<>();
        linkedHashMap.put("One", 1);
        linkedHashMap.put("Two", 2);
        linkedHashMap.put("Three", 3);

        // TreeMap - 有序Map，基于红黑树
        TreeMap<String, Integer> treeMap = new TreeMap<>();
        treeMap.put("Banana", 2);
        treeMap.put("Apple", 1);
        treeMap.put("Cherry", 3);
        // 按键排序: Apple, Banana, Cherry
        System.out.println(treeMap.firstKey()); // Apple
        System.out.println(treeMap.lastKey()); // Cherry

        // Hashtable - 线程安全的哈希表
        Hashtable<String, Integer> hashtable = new Hashtable<>();
        hashtable.put("One", 1);
        hashtable.put("Two", 2);

        // 并发集合

        // ConcurrentHashMap - 线程安全的HashMap
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("A", 1);
        concurrentHashMap.put("B", 2);

        // CopyOnWriteArrayList - 适用于读多写少的场景
        CopyOnWriteArrayList<String> copyOnWriteList = new CopyOnWriteArrayList<>();
        copyOnWriteList.add("Item 1");
        copyOnWriteList.add("Item 2");

        // 阻塞队列
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(10);
        try {
            blockingQueue.put("Task");
            String task = blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 工具类

        // Collections - 集合工具类
        List<Integer> numbers = new ArrayList<>();
        numbers.add(3);
        numbers.add(1);
        numbers.add(5);
        Collections.sort(numbers); // 排序
        Collections.reverse(numbers); // 反转
        Collections.shuffle(numbers); // 洗牌
        int max = Collections.max(numbers); // 最大值
        int min = Collections.min(numbers); // 最小值

        // 不可修改的集合
        List<String> immutableList = Collections.unmodifiableList(arrayList);

        // Arrays - 数组工具类
        int[] intArray = {5, 2, 8, 1, 9};
        Arrays.sort(intArray); // 排序
        int index = Arrays.binarySearch(intArray, 5); // 二分查找
        List<Integer> asList = Arrays.asList(1, 2, 3); // 转换为List
    }

    // 用于示例的Person类
    static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }
    }
}
