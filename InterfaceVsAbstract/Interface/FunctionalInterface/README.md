# Java Functional Interfaces

A comprehensive guide to understanding Functional Interfaces, the foundation of functional programming in Java 8 and beyond.

---

## 🛑 What is a Functional Interface?

A **Functional Interface** is an interface that contains **exactly one abstract method (SAM - Single Abstract Method)**. While it can have multiple `default` or `static` methods, it must have only one method that requires implementation.

### The `@FunctionalInterface` Annotation
This is an informative annotation used to:
1.  **Validate:** Ensures the interface meets the SAM criteria.
2.  **Communicate:** Signals to other developers that the interface is intended for use with Lambda expressions.

---

## 🛠️ Core Built-in Interfaces

Java provides a standard set of functional interfaces in the `java.util.function` package to handle common programming scenarios.

| Category | Interface | Abstract Method | Logic |
| :--- | :--- | :--- | :--- |
| **Check** | `Predicate<T>` | `boolean test(T t)` | Takes an object, returns a boolean. |
| **Transform** | `Function<T, R>` | `R apply(T t)` | Takes type T, returns type R. |
| **Consume** | `Consumer<T>` | `void accept(T t)` | Takes an object, returns nothing. |
| **Supply** | `Supplier<T>` | `T get()` | Takes nothing, returns an object. |
| **Operate** | `UnaryOperator<T>` | `T apply(T t)` | Transform where input/output types match. |



---

## 💻 Code Examples

### 1. Custom Functional Interface
You can define your own interface to model specific business logic.

```java
@FunctionalInterface
public interface StringFormatter {
    String format(String input);
    
    // Default methods are allowed
    default void log(String input) {
        System.out.println("Processing: " + input);
    }
}
```
### 2. Implementation via Lambda
Functional interfaces allow you to treat code as data. Instead of creating a separate class file or a bulky anonymous inner class, you provide the implementation inline.

```java
public class Demo {
    public static void main(String[] args) {
        // Implementing the custom interface using a Lambda Expression
        StringFormatter upperCase = (str) -> str.toUpperCase();
        
        upperCase.log("hello world"); // Calling the default method
        System.out.println(upperCase.format("hello world")); // Output: HELLO WORLD
    }
}
```

## 🔄 Relationship with Lambda Expressions

A Lambda expression is essentially the concrete implementation of the **Single Abstract Method (SAM)** defined in a Functional Interface. The Java compiler uses **Type Inference** to match the lambda's parameters and return type to the interface's method signature.



### Anatomy of the Connection
* **The Target Type:** The functional interface type to which the lambda is being assigned.
* **The Parameter List:** Must match the interface method's arguments in count and type.
* **The Body:** The logic that fulfills the method's return requirement.

---

## 🌊 Functional Interfaces in the Stream API

The most common real-world application of these interfaces is within the **Java Stream API**. They act as the "instruction set" for processing data collections.



```java
List<String> names = Arrays.asList("Alice", "Bob", "Charlie", "David");

names.stream()
    .filter(name -> name.startsWith("C"))        // Uses Predicate: boolean test(T t)
    .map(String::length)                         // Uses Function: R apply(T t)
    .forEach(System.out::println);               // Uses Consumer: void accept(T t)
```

## 💡 More Practical Examples

Functional interfaces shine when you move beyond simple one-liners. Here are a few ways they are applied in robust Java applications.

### A. Chaining Predicates for Complex Logic
The `Predicate<T>` interface includes default methods like `and()`, `or()`, and `negate()`, allowing you to build complex filters dynamically.

```java
Predicate<String> startsWithA = s -> s.startsWith("A");
Predicate<String> isShort = s -> s.length() < 5;

List<String> names = Arrays.asList("Alice", "Ace", "Alex", "Andrew");

// Combining filters: Starts with A AND length is less than 5
names.stream()
     .filter(startsWithA.and(isShort))
     .forEach(System.out::println); // Output: Ace, Alex
```
### B. Using `BiFunction` for Multi-Input Operations
When a single input isn't enough, `BiFunction<T, U, R>` allows you to pass two different types and return a third.

```java
// BiFunction<Input1, Input2, Output>
BiFunction<Integer, Integer, String> sumToString = (a, b) -> "Result: " + (a + b);

System.out.println(sumToString.apply(10, 20)); // Output: Result: 30
```
### C. Using `Supplier` for Lazy Generation
Suppliers are ideal for "on-demand" logic. They are frequently used in factory patterns, mock data generation, or expensive logging operations where you only want the code to run if a specific condition is met.

```java
// Logic is defined but NOT executed yet
Supplier<LocalDateTime> currentTime = () -> {
    System.out.println("Fetching current timestamp...");
    return LocalDateTime.now();
};

// The time is captured only when .get() is called
System.out.println("Wait for it...");
System.out.println("Now: " + currentTime.get());
```

### D. Custom Functional Interface for Business Logic
While the built-in interfaces in `java.util.function` cover most scenarios, creating your own improves code readability and domain modeling by giving the operation a meaningful name.

```java
@FunctionalInterface
interface TaxCalculator {
    double calculate(double amount, double rate);
}

// Using the custom interface to represent different tax strategies
TaxCalculator standardTax = (amount, rate) -> amount * rate;
TaxCalculator luxuryTax = (amount, rate) -> (amount * rate) + (amount * 0.05);

System.out.println("Standard: " + standardTax.calculate(100.0, 0.10)); // 10.0
System.out.println("Luxury: " + luxuryTax.calculate(100.0, 0.10));   // 15.0
```

## 🚀 Why Use Them?

* **Reduced Boilerplate:** It eliminates the "Vertical Drift" (excessive lines of code) caused by anonymous inner classes, making the codebase significantly cleaner.
* **Higher-Order Functions:** You can pass behavior as a parameter. This allows for the **Strategy Pattern** where the logic is decided at runtime rather than being hardcoded into a class.
* **Deferred Execution:** Logic is defined at the declaration stage but executed only when specifically invoked. This is highly efficient for conditional logging or complex calculations.
* **Parallelism Ready:** Functional interfaces are the engine of the Stream API, which makes transitioning from sequential to parallel processing (`parallelStream()`) a matter of changing a single method call.

---

## ⚠️ Important Constraints & Rules



1.  **Variable Capture (Effectively Final):** Lambdas can access variables from the enclosing scope, but those variables must be **effectively final**. This means if you assign a value to a local variable, you cannot change it later if that variable is used inside a lambda.
2.  **Scope and `this`:** A lambda expression does **not** define a new scope. Therefore, the keyword `this` inside a lambda refers to the instance of the class where the lambda is defined, not a hidden "lambda object."

[Image comparison of Java Lambda Scope vs Anonymous Inner Class Scope]

3.  **Exception Handling:** Standard functional interfaces like `Consumer` or `Function` do not have `throws` clauses for checked exceptions. You must either:
    * Handle the exception inside the lambda with a `try-catch` block.
    * Create a custom functional interface that declares the exception.
4.  **Object Method Overriding:** Methods from `java.lang.Object` (like `toString()` or `equals()`) can be declared in a functional interface without breaking the SAM (Single Abstract Method) rule.

---

## 🛠️ Method References
When a lambda expression simply calls an existing method without any additional logic, you can use **Method References** (`::`) for even greater conciseness.

| Reference Type | Lambda Syntax | Method Reference Syntax |
| :--- | :--- | :--- |
| **Static Method** | `(s) -> Integer.parseInt(s)` | `Integer::parseInt` |
| **Instance Method (Specific)** | `(s) -> System.out.println(s)` | `System.out::println` |
| **Instance Method (Arbitrary)**| `(s) -> s.toLowerCase()` | `String::toLowerCase` |
| **Constructor** | `() -> new ArrayList<>()` | `ArrayList::new` |

---
