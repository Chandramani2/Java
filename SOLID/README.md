# SOLID Principles in Java


This repository demonstrates the **SOLID** design principles using Java. These principles, introduced by Robert C. Martin (Uncle Bob), are intended to make software designs more understandable, flexible, and maintainable.

## Table of Contents
1. [Single Responsibility Principle (SRP)](#1-single-responsibility-principle-srp)
2. [Open/Closed Principle (OCP)](#2-openclosed-principle-ocp)
3. [Liskov Substitution Principle (LSP)](#3-liskov-substitution-principle-lsp)
4. [Interface Segregation Principle (ISP)](#4-interface-segregation-principle-isp)
5. [Dependency Inversion Principle (DIP)](#5-dependency-inversion-principle-dip)

---

## 1. Single Responsibility Principle (SRP)
**Definition:** A class should have only one reason to change. It should have only one job.

### ❌ Bad Example
Here, the `Book` class is handling both business logic (name/text) and presentation logic (printing). If we want to change how it prints, we have to modify the `Book` class.

```java
public class Book {
    private String name;
    private String text;

    // ... constructors and getters

    // Violates SRP: Formatting logic allows print capability mixed with business logic
    public void printTextToConsole() {
        System.out.println(text);
    }
}
```

### ✅ Good Example
We separate the concerns. The `Book` handles data, and the `BookPrinter` handles printing.

```java
public class Book {
    private String name;
    private String text;
    // ... constructors and getters
}

public class BookPrinter {
    // Handles the printing logic exclusively
    public void printTextToConsole(Book book) {
        System.out.println(book.getText());
    }
}
```

---

## 2. Open/Closed Principle (OCP)
**Definition:** Software entities (classes, modules, functions, etc.) should be **open for extension**, but **closed for modification**.

### ❌ Bad Example
If we want to add a new shape (e.g., Triangle), we have to modify the `AreaCalculator` class, risking bugs in existing code.

```java
public class AreaCalculator {
    public double calculateArea(Object shape) {
        if (shape instanceof Circle) {
            return Math.PI * ((Circle) shape).getRadius() * ((Circle) shape).getRadius();
        } else if (shape instanceof Rectangle) {
            return ((Rectangle) shape).getLength() * ((Rectangle) shape).getWidth();
        }
        return 0;
    }
}
```

### ✅ Good Example
We use an interface. To add a Triangle, we create a new class implementing `Shape`. The `AreaCalculator` stays untouched.

```java
public interface Shape {
    double calculateArea();
}

public class Circle implements Shape {
    public double radius;
    public double calculateArea() { return Math.PI * radius * radius; }
}

public class Rectangle implements Shape {
    public double length;
    public double width;
    public double calculateArea() { return length * width; }
}

public class AreaCalculator {
    public double calculateArea(Shape shape) {
        return shape.calculateArea();
    }
}
```

---

## 3. Liskov Substitution Principle (LSP)
**Definition:** Subtypes must be substitutable for their base types without altering the correctness of the program.

### ❌ Bad Example
A generic `Bird` class has a `fly()` method. `Ostrich` extends `Bird` but cannot fly. Calling `fly()` on an `Ostrich` throws an exception, breaking the parent's contract.

```java
public class Bird {
    public void fly() { System.out.println("Flying..."); }
}

public class Ostrich extends Bird {
    @Override
    public void fly() {
        throw new UnsupportedOperationException("Ostriches can't fly!");
    }
}
```

### ✅ Good Example
We separate the hierarchy. Not all birds can fly, so `fly()` shouldn't be in the base `Bird` class.

```java
public class Bird { /* Common bird traits */ }

public interface FlyingBird {
    void fly();
}

public class Sparrow extends Bird implements FlyingBird {
    public void fly() { System.out.println("Sparrow flying..."); }
}

public class Ostrich extends Bird {
    // Ostrich remains a Bird, but is not forced to implement fly()
}
```

---

## 4. Interface Segregation Principle (ISP)
**Definition:** Clients should not be forced to depend upon interfaces that they do not use.

### ❌ Bad Example
A generic `Worker` interface forces a `Robot` to implement `eat()`, which is irrelevant and logically wrong for a robot.

```java
public interface Worker {
    void work();
    void eat();
}

public class Robot implements Worker {
    public void work() { /* Working */ }
    public void eat() { 
        // Forced implementation 
        throw new UnsupportedOperationException(); 
    }
}
```

### ✅ Good Example
Split the interface into specific roles.

```java
public interface Workable {
    void work();
}

public interface Eatable {
    void eat();
}

public class Human implements Workable, Eatable {
    public void work() { /* ... */ }
    public void eat() { /* ... */ }
}

public class Robot implements Workable {
    public void work() { /* ... */ }
    // No need to implement eat()
}
```

---

## 5. Dependency Inversion Principle (DIP)
**Definition:** High-level modules should not depend on low-level modules. Both should depend on abstractions.

### ❌ Bad Example
The `Switch` (High-level) depends directly on the `LightBulb` (Low-level). If we want to switch on a Fan, we have to rewrite the `Switch` class.

```java
public class LightBulb {
    public void turnOn() { System.out.println("LightBulb on"); }
    public void turnOff() { System.out.println("LightBulb off"); }
}

public class Switch {
    private LightBulb bulb; // Hard dependency

    public Switch(LightBulb bulb) {
        this.bulb = bulb;
    }

    public void operate() {
        bulb.turnOn();
    }
}
```

### ✅ Good Example
The `Switch` depends on a `Switchable` interface. It doesn't care if it's a Bulb, a Fan, or a TV.

```java
public interface Switchable {
    void turnOn();
    void turnOff();
}

public class LightBulb implements Switchable {
    public void turnOn() { System.out.println("LightBulb on"); }
    public void turnOff() { System.out.println("LightBulb off"); }
}

public class Fan implements Switchable {
    public void turnOn() { System.out.println("Fan on"); }
    public void turnOff() { System.out.println("Fan off"); }
}

public class Switch {
    private Switchable device; // Depends on abstraction

    public Switch(Switchable device) {
        this.device = device;
    }

    public void operate() {
        device.turnOn();
    }
}
```

---

### Conclusion
By adhering to these principles, Java developers can create systems that are more robust, testable, and easier to extend over time.