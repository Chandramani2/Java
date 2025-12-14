# Interfaces in Java: A Complete Guide

## 1. What is an Interface?

An **Interface** in Java is a reference type, similar to a class, that can contain *only* constants, method signatures, default methods, static methods, and nested types. It is a blueprint of a class that specifies **what** a class must do, but not **how** it does it.

* **Abstraction:** It provides 100% abstraction (before Java 8).
* **Multiple Inheritance:** It allows Java to support multiple inheritance of *type*.
* **Loose Coupling:** It decouples the implementation from the definition.

---

## 2. Types of Interfaces in Java

Java interfaces can be categorized into three main types based on their structure and usage.

### A. Normal (Standard) Interface
These are the most common interfaces containing multiple abstract methods. Any class implementing this interface must provide implementations for all its methods.

**Example:**
```java
interface Vehicle {
    void start();
    void stop();
}

class Car implements Vehicle {
    public void start() { System.out.println("Car started"); }
    public void stop() { System.out.println("Car stopped"); }
}
```

### B. Functional Interface (Java 8+)
A **Functional Interface** is an interface that contains **exactly one abstract method**. They can contain any number of default or static methods. They are the basis for **Lambda Expressions**.

* **Annotation:** `@FunctionalInterface` (Optional but recommended).
* **Examples:** `Runnable`, `Callable`, `Comparable`, `ActionListener`.

**Example:**
```java
@FunctionalInterface
interface Calculator {
    int calculate(int a, int b);
}

// Usage with Lambda
public class Main {
    public static void main(String[] args) {
        Calculator add = (a, b) -> a + b;
        System.out.println("Sum: " + add.calculate(5, 10));
    }
}
```

### C. Marker Interface (Tagging Interface)
A **Marker Interface** is an interface that contains **no methods or constants**. It is empty.
* **Purpose:** It instructs the JVM or a framework to treat the object in a special way (add metadata).
* **Examples:**
    * `java.io.Serializable`: Tells JVM the object can be serialized.
    * `java.lang.Cloneable`: Tells JVM the object is allowed to be cloned.
    * `java.rmi.Remote`: Marks an object as accessible remotely.

**Example:**
```java
class Student implements Cloneable {
    // This class is now legally cloneable by the JVM
}
```

---

## 3. Evolution of Interfaces (Java 7 vs 8 vs 9)

Interfaces have evolved significantly to support modern programming patterns.

| Feature | Java 7 (and older) | Java 8 | Java 9 (and newer) |
| :--- | :--- | :--- | :--- |
| **Variables** | `public static final` | `public static final` | `public static final` |
| **Abstract Methods** | Yes | Yes | Yes |
| **Default Methods** | No | **Yes** (`default` keyword) | Yes |
| **Static Methods** | No | **Yes** | Yes |
| **Private Methods** | No | No | **Yes** |

---

## 4. Modern Interface Features (Explained)

### A. Default Methods (Java 8)
Before Java 8, adding a new method to an interface would break all classes implementing it. **Default methods** allow you to add new functionality to interfaces without breaking existing implementations.

```java
interface Phone {
    void call();
    
    // Default method - backward compatibility
    default void message() {
        System.out.println("Sent text message");
    }
}
```

### B. Static Methods (Java 8)
Interfaces can now have helper methods defined as `static`. These cannot be overridden by implementing classes.

```java
interface Utility {
    static boolean isNull(String str) {
        return str == null || str.isEmpty();
    }
}

// Usage
boolean check = Utility.isNull("Hello");
```

### C. Private Methods (Java 9)
Java 9 allows private methods in interfaces. This allows you to share common code between multiple `default` methods without exposing that helper logic to the outside world.

```java
interface Database {
    default void logInfo(String msg) {
        log(msg, "INFO");
    }

    default void logError(String msg) {
        log(msg, "ERROR");
    }

    // Private helper method used by default methods above
    private void log(String msg, String type) {
        System.out.println("[" + type + "] " + msg);
    }
}
```

---

## 5. Interface vs. Abstract Class

| Feature | Interface | Abstract Class |
| :--- | :--- | :--- |
| **Inheritance** | A class can implement **multiple** interfaces. | A class can extend only **one** abstract class. |
| **State** | Cannot hold state (variables are `final`). | Can hold state (instance variables). |
| **Constructors** | No constructors allowed. | Can have constructors. |
| **Access Modifiers** | Methods are implicitly `public`. | Methods can be `public`, `protected`, or `private`. |
| **Usage** | Defines a "Contract" or "Capability" (e.g., `Runnable`). | Defines a common "Identity" (e.g., `Animal` -> `Dog`). |

---

## 6. Real-World Use Cases

1.  **Plugins/APIs:** An application defines an interface (e.g., `PaymentGateway`), and vendors (PayPal, Stripe) implement it. The app doesn't care which vendor is used, only that the interface contract is met.
2.  **Mocking in Tests:** Interfaces make unit testing easy. You can replace a real `DatabaseService` with a `MockDatabaseService` that implements the same interface.
3.  **Multiple Inheritance:** Since Java doesn't support multiple class inheritance, interfaces solve the "Diamond Problem" by allowing a class to inherit behavior from multiple sources.