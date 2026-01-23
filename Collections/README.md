# Java Collections Framework: Comprehensive Method Reference

This document provides a detailed list of methods for the core interfaces and classes within the `java.util` package.

---

## 1. The Collection Interface (`java.util.Collection`)
All `List`, `Set`, and `Queue` implementations share these methods.

### Query Operations
* `int size()`: Returns the number of elements.
* `boolean isEmpty()`: Returns true if the collection is empty.
* `boolean contains(Object o)`: Checks if a specific element exists.
* `Iterator<E> iterator()`: Returns an iterator over the elements.
* `Object[] toArray()`: Returns an array containing all elements.
* `<T> T[] toArray(T[] a)`: Returns an array containing all elements; the runtime type of the returned array is that of the specified array.

### Modification Operations
* `boolean add(E e)`: Adds an element (optional).
* `boolean remove(Object o)`: Removes a single instance of an element.
* `void clear()`: Removes all elements.

### Bulk Operations
* `boolean containsAll(Collection<?> c)`: Checks if all elements in `c` are present.
* `boolean addAll(Collection<? extends E> c)`: Adds all elements from `c`.
* `boolean removeAll(Collection<?> c)`: Removes all elements that are also in `c`.
* `boolean removeIf(Predicate<? super E> filter)`: Removes elements that satisfy the given predicate.
* `boolean retainAll(Collection<?> c)`: Retains only elements found in `c`.

### Example Implementation
```java
import java.util.*;

public class BasicCollection {
    public static void main(String[] args) {
        Collection<String> items = new ArrayList<>();
        items.add("Data");
        items.add("Logic");
        System.out.println("Contains Logic: " + items.contains("Logic"));
        items.remove("Data");
        System.out.println("Size: " + items.size());
    }
}
```
---

## 2. The List Interface (`java.util.List`)
Ordered collections that allow duplicate elements.



### Positional Access
* `E get(int index)`: Returns element at index.
* `E set(int index, E element)`: Replaces element at index.
* `void add(int index, E element)`: Inserts element at index.
* `E remove(int index)`: Removes element at index.

### Search Operations
* `int indexOf(Object o)`: Returns first index of element.
* `int lastIndexOf(Object o)`: Returns last index of element.

### List Iteration & Views
* `ListIterator<E> listIterator()`: Returns a bidirectional iterator.
* `ListIterator<E> listIterator(int index)`: Starts iterator at specific index.
* `List<E> subList(int fromIndex, int toIndex)`: Returns a view of a portion of the list.

### Example Implementation
```java
import java.util.*;

public class ListDemo {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("First");
        list.add("Second");
        list.add(1, "Inserted");
        
        System.out.println("Index 1: " + list.get(1)); // Inserted
        list.sort(Comparator.reverseOrder());
    }
}
```
---

## 3. The Set Interface (`java.util.Set`)
Collections that contain no duplicate elements.

### NavigableSet / TreeSet Methods
* `E lower(E e)`: Greatest element < e.
* `E floor(E e)`: Greatest element <= e.
* `E ceiling(E e)`: Smallest element >= e.
* `E higher(E e)`: Smallest element > e.
* `E pollFirst()`: Retrieves and removes the first (lowest) element.
* `E pollLast()`: Retrieves and removes the last (highest) element.

### Example Implementation
```java
import java.util.*;

public class SetDemo {
    public static void main(String[] args) {
        NavigableSet<Integer> set = new TreeSet<>();
        set.add(100);
        set.add(10);
        set.add(50);
        
        System.out.println("Lowest: " + set.first()); // 10
        System.out.println("Higher than 10: " + set.higher(10)); // 50
    }
}
```
---

## 4. The Queue & Deque Interfaces
Designed for holding elements prior to processing.

### Queue Methods (Standard)
| Action | Throws Exception | Returns Special Value |
| :--- | :--- | :--- |
| **Insert** | `add(e)` | `offer(e)` |
| **Remove** | `remove()` | `poll()` |
| **Examine** | `element()` | `peek()` |

### Deque Methods (Double-Ended)
* `void addFirst(E e)` / `void addLast(E e)`
* `E removeFirst()` / `E removeLast()`
* `E getFirst()` / `E getLast()`
* `void push(E e)` / `E pop()` (Stack behavior)

### Example Implementation
```java
import java.util.*;

public class QueueDemo {
    public static void main(String[] args) {
        Deque<String> stack = new ArrayDeque<>();
        stack.push("Bottom");
        stack.push("Top");
        
        System.out.println("Popped: " + stack.pop()); // Top
    }
}
```

---

## 5. The Map Interface (`java.util.Map`)
Objects that map unique keys to values.



### Basic Map Operations
* `V put(K key, V value)`: Associates value with key.
* `V get(Object key)`: Returns value for key.
* `V remove(Object key)`: Removes mapping for key.
* `boolean containsKey(Object key)`: Checks if key exists.
* `boolean containsValue(Object value)`: Checks if value exists.
* `void putAll(Map<? extends K, ? extends V> m)`: Copies all mappings from `m`.

### Map Views
* `Set<K> keySet()`: Returns a Set of keys.
* `Collection<V> values()`: Returns a Collection of values.
* `Set<Map.Entry<K, V>> entrySet()`: Returns a Set of key-value pairs.

### Java 8+ Enhancements
* `V getOrDefault(Object key, V defaultValue)`: Returns value or default.
* `V putIfAbsent(K key, V value)`: Only puts if key is missing.
* `boolean remove(Object key, Object value)`: Removes only if key maps to specific value.
* `V replace(K key, V value)`: Replaces value for a key.
* `void replaceAll(BiFunction function)`: Replaces each entry's value with result of function.
* `V computeIfAbsent(K key, Function mappingFunction)`: Computes value if key is missing.
* `V merge(K key, V value, BiFunction remappingFunction)`: Merges new and old values.

### Example Implementation
```java
import java.util.*;

public class MapDemo {
    public static void main(String[] args) {
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "Alpha");
        map.put(2, "Beta");
        
        String val = map.getOrDefault(3, "Missing");
        System.out.println("Key 3: " + val);
    }
}
```

---

## 6. Collections Utility Class (`java.util.Collections`)
Static methods that operate on or return collections.

* `Collections.sort(List<T> list)`: Sorts the list.
* `Collections.reverse(List<?> list)`: Reverses the order.
* `Collections.shuffle(List<?> list)`: Randomizes elements.
* `Collections.synchronizedList(List<T> list)`: Returns thread-safe list.
* `Collections.unmodifiableList(List<? extends T> list)`: Returns read-only list.
* `Collections.frequency(Collection<?> c, Object o)`: Counts occurrences of `o`.

