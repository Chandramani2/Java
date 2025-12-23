# Java Data Structures & Collections Cheatsheet

This document covers the essential Data Structures in Java, their primary methods, and a quick-reference performance guide.

---

## 1. List Interface
Lists are ordered collections. They maintain the insertion order and allow duplicate elements.

### ArrayList
* **Implementation:** Resizable array.
* **Best for:** Random access and storing data that doesn't change size frequently.

### LinkedList
* **Implementation:** Doubly-linked list.
* **Best for:** Frequent insertions/deletions at the beginning or end.



### Common Methods (List)
| Method | Description |
| :--- | :--- |
| `add(E e)` | Appends element to the end. |
| `add(int index, E e)` | Inserts element at specific index. |
| `get(int index)` | Returns element at index. |
| `set(int index, E e)` | Replaces element at index. |
| `remove(int index)` | Removes element at index. |
| `size()` | Returns total elements. |
| `contains(Object o)` | Checks if element exists ($O(n)$). |

---

## 2. Set Interface
Sets are collections that contain **no duplicate elements**.

### HashSet
* **Performance:** $O(1)$ for basic operations.
* **Ordering:** No guarantee of order.

### TreeSet
* **Performance:** $O(\log n)$.
* **Ordering:** Sorted order (Natural or via Comparator).

### Common Methods (Set)
| Method | Description |
| :--- | :--- |
| `add(E e)` | Adds only if not present (returns `false` if duplicate). |
| `remove(Object o)` | Removes the element. |
| `contains(Object o)` | Checks if element exists ($O(1)$ for HashSet). |
| `isEmpty()` | Checks if set is empty. |

---

## 3. Map Interface
Maps store data in **Key-Value** pairs. Keys must be unique.



### HashMap
* **Performance:** $O(1)$ average.
* **Ordering:** Unordered.

### TreeMap
* **Performance:** $O(\log n)$.
* **Ordering:** Sorted by Key.

### Common Methods (Map)
| Method | Description |
| :--- | :--- |
| `put(K key, V value)` | Adds or updates a key-value pair. |
| `get(Object key)` | Returns value or `null`. |
| `containsKey(Object key)` | Returns `true` if key exists. |
| `remove(Object key)` | Removes the entry for the key. |
| `keySet()` | Returns a Set of all keys. |
| `values()` | Returns a Collection of all values. |
| `getOrDefault(key, default)`| Returns value or the provided default if key is missing. |

---

## 4. Queue & Deque
Used for holding elements prior to processing.

### PriorityQueue
* Elements are ordered by priority (natural or custom).
* **Note:** Head is the *least* element.

### ArrayDeque
* Faster than `Stack` class for LIFO operations.
* Faster than `LinkedList` for FIFO operations.

### Common Methods
| Operation | Throws Exception | Returns Special Value |
| :--- | :--- | :--- |
| **Insert** | `add(e)` | `offer(e)` |
| **Remove** | `remove()` | `poll()` |
| **Examine** | `element()` | `peek()` |

---

## 5. Performance Cheatsheet (Big O)

| Data Structure | Access | Search | Insertion | Deletion |
| :--- | :--- | :--- | :--- | :--- |
| **ArrayList** | $O(1)$ | $O(n)$ | $O(n)$ | $O(n)$ |
| **LinkedList** | $O(n)$ | $O(n)$ | $O(1)$ | $O(1)$ |
| **HashMap** | N/A | $O(1)$ | $O(1)$ | $O(1)$ |
| **TreeMap** | N/A | $O(\log n)$ | $O(\log n)$ | $O(\log n)$ |
| **HashSet** | N/A | $O(1)$ | $O(1)$ | $O(1)$ |

---

## 6. Code Example: Frequency Map
A common use case using `HashMap` and `List`.

```java
import java.util.*;

public class Main {
    public static void main(String[] args) {
        String text = "apple banana apple cherry banana apple";
        String[] words = text.split(" ");

        // Using HashMap to count frequency
        Map<String, Integer> counts = new HashMap<>();
        for (String word : words) {
            counts.put(word, counts.getOrDefault(word, 0) + 1);
        }

        // Sorting the keys using a List
        List<String> sortedKeys = new ArrayList<>(counts.keySet());
        Collections.sort(sortedKeys);

        System.out.println(counts); // {banana=2, apple=3, cherry=1}
    }
}
```

# Java Data Structures & Collections: Extended Guide

This document provides a deep dive into Java's data structures, including implementation details, common methods, and Big O complexity.

---

## 1. Map Interface (Key-Value Pairs)
Maps are used for fast lookups by associating a unique **Key** with a **Value**.

### HashMap
* **How it works:** Uses a Hashing algorithm to store data in buckets.
* **Ordering:** No guarantee of order; order can change over time.
* **Performance:** $O(1)$ for get/put (average case).



### TreeMap
* **How it works:** Implements `NavigableMap` using a Red-Black Tree.
* **Ordering:** Sorted according to natural ordering or a custom comparator.
* **Performance:** $O(\log n)$ for get/put.

### Essential Map Methods
| Method | Description |
| :--- | :--- |
| `put(K key, V value)` | Maps the specified value with the specified key. |
| `get(Object key)` | Returns the value to which the specified key is mapped. |
| `containsKey(Object key)` | Returns true if this map contains a mapping for the key. |
| `putIfAbsent(K key, V value)` | Adds the pair only if the key is not already present. |
| `replace(K key, V value)` | Replaces the entry for the specified key only if currently mapped. |
| `computeIfAbsent(K key, Function)` | Computes a value if key is not present. |

---

## 2. Set Interface (Unique Elements)
Sets prevent duplicate entries. They are mathematically modeled after the set theory.

### HashSet
* **Internal:** Backed by a `HashMap`.
* **Use Case:** When you need a unique collection and don't care about order.

### TreeSet
* **Internal:** Backed by a `TreeMap`.
* **Use Case:** When you need a unique collection that remains sorted.

### Essential Set Methods
| Method | Description |
| :--- | :--- |
| `add(E e)` | Adds the element if it is not already present. |
| `addAll(Collection c)` | Performs a **Union** of two sets. |
| `retainAll(Collection c)` | Performs an **Intersection** (keeps only common elements). |
| `containsAll(Collection c)` | Checks if one set is a **Subset** of another. |

---

## 3. Tree Structures
While Java provides `TreeMap` and `TreeSet`, you often need to implement custom trees for specific algorithms.

### Binary Search Tree (BST)
A node-based structure where the left child is less than the parent, and the right child is greater.



### Common Tree Operations
* **In-order Traversal:** Left -> Root -> Right (Produces sorted output).
* **Pre-order Traversal:** Root -> Left -> Right.
* **Post-order Traversal:** Left -> Right -> Root.

---

## 4. Advanced/Custom Data Structures

### Trie (Prefix Tree)
Used for efficient retrieval of keys in a dataset of strings (e.g., autocomplete).
* **Structure:** Each node represents a character of a word.
* **Time Complexity:** $O(L)$ where $L$ is the length of the word.



### Graph
A collection of nodes (vertices) and connections (edges).
* **Representations:** 1. **Adjacency List:** `Map<Integer, List<Integer>>` (Space efficient).
    2. **Adjacency Matrix:** `int[][]` (Fast edge lookup).

### Common Graph Algorithms
* **BFS (Breadth-First Search):** Uses a `Queue`.
* **DFS (Depth-First Search):** Uses a `Stack` or Recursion.

---

## 5. Cheat Sheet: Time Complexity Comparison

| Data Structure | Search | Insertion | Deletion | Space |
| :--- | :--- | :--- | :--- | :--- |
| **HashMap** | $O(1)$ | $O(1)$ | $O(1)$ | $O(n)$ |
| **TreeMap** | $O(\log n)$ | $O(\log n)$ | $O(\log n)$ | $O(n)$ |
| **LinkedHashMap** | $O(1)$ | $O(1)$ | $O(1)$ | $O(n)$ |
| **Binary Search Tree**| $O(\log n)$ | $O(\log n)$ | $O(\log n)$ | $O(n)$ |
| **Trie** | $O(L)$ | $O(L)$ | $O(L)$ | $O(Alphabet \times L \times n)$ |

---

## 6. Practical Example: Set Operations
```java
import java.util.*;

public class SetDemo {
    public static void main(String[] args) {
        Set<Integer> setA = new HashSet<>(Arrays.asList(1, 2, 3, 4));
        Set<Integer> setB = new HashSet<>(Arrays.asList(3, 4, 5, 6));

        // Union
        Set<Integer> union = new HashSet<>(setA);
        union.addAll(setB); // [1, 2, 3, 4, 5, 6]

        // Intersection
        Set<Integer> intersection = new HashSet<>(setA);
        intersection.retainAll(setB); // [3, 4]
        
        System.out.println("Intersection: " + intersection);
    }
}
```