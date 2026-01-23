# Java Streams API

The **Java Streams API**, introduced in Java 8, is a powerful framework for processing sequences of elements (collections, arrays, or I/O resources) in a functional and declarative manner.

---

## 🌊 What is a Stream?
A stream is **not** a data structure; it does not store data. Instead, it is a pipeline of computational steps that convey elements from a source through various stages to a final result.

### Key Characteristics:
* **Declarative:** You describe *what* to do, not *how* to do it.
* **Pipelining:** Most operations return a new stream, allowing operations to be chained.
* **Internal Iteration:** Unlike a `for-each` loop (external iteration), the Stream API manages the iteration for you.
* **Lazy Evaluation:** Intermediate operations are not executed until a terminal operation is invoked.


---

## 🛠️ Stream Pipeline Stages

A stream pipeline consists of three distinct parts:

| Stage | Purpose | Examples |
| :--- | :--- | :--- |
| **Source** | The data origin. | `list.stream()`, `Arrays.stream()`, `Files.lines()` |
| **Intermediate** | Transforms the stream into another stream. | `filter()`, `map()`, `sorted()`, `distinct()` |
| **Terminal** | Produces a result or side-effect; closes the stream. | `collect()`, `forEach()`, `reduce()`, `count()` |

---

## 💻 Core Operations & Examples

### 1. Filtering and Mapping
This is the most common use case: selecting specific items and transforming them.

```java
List<String> names = Arrays.asList("Reflection", "Collection", "Stream", "Structure");

List<String> result = names.stream()
    .filter(s -> s.startsWith("S"))         // Filter: Only strings starting with 'S'
    .map(String::toUpperCase)              // Map: Transform to uppercase
    .collect(Collectors.toList());         // Terminal: Collect into a list

// Result: ["STREAM", "STRUCTURE"]
```

### 2. Reducing (Folding)
Reduction operations take a sequence of elements and combine them into a single summary result (like a sum, maximum, or average) by repeatedly applying a combining operation.


```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// reduce(Identity, Accumulator)
int sum = numbers.stream()
    .reduce(0, (a, b) -> a + b); 

System.out.println(sum); // Output: 15
```
### 3. Slicing and Sorting
Streams allow you to skip elements, limit the size of the result, or sort the data with minimal syntax. This is particularly useful for pagination or finding top/bottom results.

```java
List<String> fruits = Arrays.asList("Apple", "Banana", "Cherry", "Banana", "Elderberry");

fruits.stream()
    .distinct()                            // Removes "Banana" duplicate
    .sorted(Comparator.reverseOrder())     // Sorts Z to A
    .skip(1)                               // Skips the first element (the highest)
    .limit(2)                              // Takes the next two elements
    .forEach(System.out::println);
```

## 💡 Advanced Practical Examples

Beyond basic filtering, the Stream API provides sophisticated tools for complex data manipulation and analysis.

### A. FlatMap: Flattening Nested Collections
`flatMap` is used when each element in a stream can be transformed into another stream of elements. It "flattens" multiple streams into a single one.


```java
List<List<String>> nestedList = Arrays.asList(
    Arrays.asList("Java", "Spring"),
    Arrays.asList("Docker", "Kubernetes"),
    Arrays.asList("AWS", "Azure")
);

// Flattening nested lists into a single list of strings
List<String> techStack = nestedList.stream()
    .flatMap(List::stream)                 // Converts Stream<List<String>> to Stream<String>
    .map(String::toUpperCase)
    .collect(Collectors.toList()); 

// Result: ["JAVA", "SPRING", "DOCKER", "KUBERNETES", "AWS", "AZURE"]
```
### B. Grouping and Counting (Analytics)
Using `Collectors.groupingBy` allows you to perform SQL-style "GROUP BY" operations directly on your collections. This is often paired with "downstream collectors" like `counting()` or `summingInt()`.


```java
List<String> items = Arrays.asList("apple", "apple", "banana", "apple", "orange", "banana");

// Grouping by string and counting occurrences
Map<String, Long> inventoryCount = items.stream()
    .collect(Collectors.groupingBy(
        Function.identity(),               // Use the element itself as the key
        Collectors.counting()              // Aggregate the count as the value
    ));

// Result: {apple=3, banana=2, orange=1}
```
### C. Matching Operations (Short-circuiting)
Short-circuiting operations are high-performance tools that stop processing the stream as soon as the result is determined. This means the API does not need to examine every single element in the source once the outcome is certain, saving significant CPU cycles on large datasets.


```java
List<Integer> scores = Arrays.asList(85, 92, 45, 88, 76);

// Returns true only if ALL elements match the predicate
boolean allPassed = scores.stream().allMatch(s -> s >= 50);   // false (one failed)

// Returns true if AT LEAST ONE element matches the predicate
boolean anyExcellent = scores.stream().anyMatch(s -> s > 90); // true (92 exists)

// Returns true if NO elements match the predicate
boolean nonePerfect = scores.stream().noneMatch(s -> s == 100); // true
```
### D. Finding Specific Elements

Unlike a traditional loop that requires manual break statements and risky null checks, these operations return an **Optional**. This container object provides a type-safe way to handle cases where a match might not exist, preventing the dreaded `NullPointerException`.


```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

// Returns an Optional containing the first element that matches the filter
Optional<String> match = names.stream()
    .filter(name -> name.startsWith("C"))
    .findFirst();

// Handle the result safely
match.ifPresent(name -> System.out.println("Found: " + name));

// Or provide a default value if not found
String result = match.orElse("No match found");
```

## 🚀 Why Use Streams?

- **Conciseness**: Replaces nested loops and complex if-else logic with a flat, readable pipeline.
- **Declarative Style**: You specify what you want to happen (filtering, sorting) rather than how to manage the low-level iteration.
- **Lazy Evaluation**: Intermediate operations are not performed until the terminal operation is called. This allows the JVM to skip unnecessary work—for example, in a `findFirst()` operation, it stops as soon as it finds a match.
- **Easy Parallelism**: Leverage multi-core processors by simply changing `.stream()` to `.parallelStream()`.

## ⚠️ Important Rules & Constraints

- **One-Time Use**: A stream is consumed once a terminal operation is called. You cannot reuse a stream object; doing so results in an `IllegalStateException`.
- **Non-Interference**: Do not modify the underlying data source (e.g., `list.add()` or `list.remove()`) during stream execution, as it may lead to a `ConcurrentModificationException`.
- **Statelessness**: Lambdas used in streams should be stateless. If a lambda relies on a mutable external variable, parallel execution may produce unpredictable or corrupted results.
- **Primitive Specialization**: Use `IntStream`, `LongStream`, or `DoubleStream` when working with numbers to avoid the performance overhead of Autoboxing (converting `int` to `Integer`).

## 🛠️ Common Collectors

The `Collectors` class is the primary way to terminate a stream and package the results into a useful format.

| Collector | Purpose |
|-----------|---------|
| `toList()` / `toSet()` | Standard conversion back into Collections |
| `joining(", ")` | Concatenates elements into a single String with a delimiter |
| `groupingBy(f)` | Creates a Map based on a classification function (similar to SQL GROUP BY) |
| `partitioningBy(p)` | Splits a stream into two groups (True/False) based on a Predicate |
| `summarizingInt(f)` | Returns count, sum, min, average, and max in a single stats object |

