# Object-Oriented Programming (OOP) Principles in Java


This repository serves as a reference for the four fundamental pillars of Object-Oriented Programming (OOP) in Java: **Encapsulation**, **Inheritance**, **Polymorphism**, and **Abstraction**.

## Table of Contents
1. [Encapsulation](#1-encapsulation)
2. [Inheritance](#2-inheritance)
3. [Polymorphism](#3-polymorphism)
4. [Abstraction](#4-abstraction)

---

## 1. Encapsulation
**Definition:** The bundling of data (variables) and methods that operate on that data into a single unit (class). It also restricts direct access to some of an object's components (data hiding).

### ❌ Bad Example (No Encapsulation)
Here, the fields are `public`. Any part of the code can set invalid data (e.g., negative age).

```java
public class Person {
    public String name;
    public int age;
}

public class Main {
    public static void main(String[] args) {
        Person p = new Person();
        p.name = "John";
        p.age = -5; // 😱 Invalid state allowed!
    }
}
```

### ✅ Good Example
Fields are `private`. Access is controlled via public `getters` and `setters` which can include validation logic.

```java
public class Person {
    private String name;
    private int age;

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        if (age > 0) {
            this.age = age;
        } else {
            System.out.println("Age must be positive.");
        }
    }
    
    public int getAge() { return age; }
}
```

---

## 2. Inheritance
**Definition:** A mechanism where a new class (Child/Subclass) acquires the properties and behaviors of an existing class (Parent/Superclass). It promotes code reusability.

### Example
The `Car` class inherits the `startEngine` method from `Vehicle`, avoiding code duplication.

```java
// Parent Class
public class Vehicle {
    protected String brand = "Ford";
    
    public void startEngine() {
        System.out.println("Engine started.");
    }
}

// Child Class
public class Car extends Vehicle {
    private String modelName = "Mustang";

    public void honk() {
        System.out.println("Beep beep!");
    }
    
    public void printDetails() {
        // Accessing parent variable
        System.out.println(brand + " " + modelName);
    }
}
```

---

## 3. Polymorphism
**Definition:** Polymorphism means "many forms." It allows objects to be treated as instances of their parent class rather than their actual class. There are two types: **Compile-time** (Overloading) and **Runtime** (Overriding).

### A. Compile-time Polymorphism (Method Overloading)
Same method name, different parameters.

```java
public class Calculator {
    public int add(int a, int b) {
        return a + b;
    }

    public double add(double a, double b) {
        return a + b;
    }
}
```

### B. Runtime Polymorphism (Method Overriding)
Child class provides a specific implementation of a method already defined in the parent.

```java
class Animal {
    public void makeSound() {
        System.out.println("Some generic animal sound");
    }
}

class Dog extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Bark");
    }
}

class Cat extends Animal {
    @Override
    public void makeSound() {
        System.out.println("Meow");
    }
}

public class Main {
    public static void main(String[] args) {
        // Parent reference, Child object
        Animal myDog = new Dog();
        Animal myCat = new Cat();
        
        myDog.makeSound(); // Outputs: Bark
        myCat.makeSound(); // Outputs: Meow
    }
}
```

---

## 4. Abstraction
**Definition:** Hiding the implementation details and showing only the functionality to the user. This is achieved using `abstract` classes and `interfaces`.

### Difference from Encapsulation
* **Encapsulation** is about hiding *data* (information hiding).
* **Abstraction** is about hiding *complexity* (implementation hiding).

### Example (Using Interface)
The user knows the ATM handles `withdraw`, but doesn't need to know the complex logic behind how the bank processes it.

```java
public interface BankService {
    void withdraw(double amount);
    void checkBalance();
}

public class ATM implements BankService {
    
    @Override
    public void withdraw(double amount) {
        System.out.println("Connecting to bank server...");
        System.out.println("Validating PIN...");
        System.out.println("Dispensing $" + amount);
    }

    @Override
    public void checkBalance() {
        System.out.println("Your balance is $5000.");
    }
}

public class Main {
    public static void main(String[] args) {
        // We program to the interface, not the implementation
        BankService myService = new ATM();
        myService.withdraw(100);
    }
}
```

---

## 5. Interview Q&A (Senior/Mid-Level)
*Focus: Application, Trade-offs, and Java Internals*

### Q1: "Composition over Inheritance" is a common mantra. Why is Composition preferred, and when would you actually use Inheritance?
**Answer:**
* **The Problem with Inheritance:** Inheritance creates tight coupling between the parent and child. If you change the parent class, it creates a ripple effect breaking child classes (The Fragile Base Class problem). It also violates encapsulation because the subclass often relies on implementation details of the superclass.
* **Why Composition:** It provides flexibility. You can change behavior at runtime (dependency injection) and it allows for easier testing (mocking).
* **When to use Inheritance:** Only use Inheritance when there is a true "IS-A" relationship that is permanent. Use it for code reuse only when the behavior is extremely unlikely to change or when building frameworks where you provide a template (Template Method Pattern).

### Q2: Since Java 8 introduced `default` methods in Interfaces, is there any reason to still use Abstract Classes?
**Answer:**
Yes, Abstract Classes are still relevant for two main reasons:
1.  **State (Member Variables):** Interfaces cannot have instance variables (state); they can only have `public static final` constants. If you need to share a common state (like a `DBConnection` object) across subclasses, you need an Abstract Class.
2.  **Constructor Logic:** You cannot define constructors in an Interface. If you need to enforce initialization logic before a class is used, an Abstract Class is required.

### Q3: How does Java handle the "Diamond Problem" of Multiple Inheritance?
**Answer:**
Java does not support multiple inheritance with *Classes*, but it does with *Interfaces*.
* **The Scenario:** If Class A implements Interface B and Interface C, and both B and C have a default method `log()`, which one does A use?
* **The Resolution:** Java forces the compiler to throw an error if the conflict is not resolved manually. You must override the method in Class A and explicitly call the desired interface's method using `InterfaceName.super.methodName()`.

```java
@Override
public void log() {
    InterfaceB.super.log(); // Explicit resolution
}
```

### Q4: Can you change the return type of a method when Overriding it? (Covariant Return Types)
**Answer:**
Yes, but with a restriction.
* You cannot change the return type to a completely different type (e.g., `void` to `int`).
* However, allowed **Covariant Return Types**: The overriding method in the child class can return a **subtype** of the return type declared in the parent class.
* *Example:* If Parent returns `Number`, Child can return `Integer`. This is useful for chaining and factory patterns.

### Q5: How do you create a truly Immutable Class in Java?
**Answer:**
Merely making fields `final` is not enough. You must:
1.  Declare the class as `final` so it cannot be extended (preventing method overriding).
2.  Make all fields `private` and `final`.
3.  Do **not** provide Setters.
4.  **Deep Copy for Mutable Objects:** If the class contains a mutable object (like a `Date` or `List`), do not return the reference directly in the getter. Return a copy (clone) of the object, otherwise, the caller can modify the internal state.

### Q6: Explain the difference between Static Binding and Dynamic Binding in the context of OOP.
**Answer:**
* **Static Binding (Early Binding):** Happens at compile time. This applies to `static`, `private`, and `final` methods, as well as **Method Overloading**. The compiler knows exactly which method to call.
* **Dynamic Binding (Late Binding):** Happens at runtime. This applies to **Method Overriding**. The JVM looks at the actual object type (not the reference type) to determine which method to execute. This is the mechanism behind Polymorphism.

### Q7: Explain the rules of Exception Handling regarding Method Overriding.
**Answer:**
This is a strict rule in Java to ensure Liskov Substitution (LSP).
1.  **Unchecked Exceptions (Runtime):** The overriding method (Child) can throw *any* unchecked exception, regardless of what the parent throws.
2.  **Checked Exceptions:**
    * The child method **cannot** throw a broader checked exception than the parent (e.g., if Parent throws `FileNotFoundException`, Child cannot throw `IOException`).
    * The child method can throw the **same** exception, a **subclass** of the exception, or **no exception** at all.
    * *Reasoning:* If a client is holding a reference to the Parent, they expect to catch specific checked exceptions. If the Child throws a new, broader checked exception, the client's `try-catch` block won't handle it, breaking the contract.

### Q8: "Cloneable" is often considered broken in Java. What is the better alternative for copying objects?
**Answer:**
The `Cloneable` interface is problematic because it performs a shallow copy by default (references are copied, not the actual objects), and the `clone()` method is `protected`.
**The Better Alternative: Copy Constructors.**
Instead of `myObject.clone()`, you define a constructor that takes an instance of the class:

```java
public class User {
    private String name;
    private List<String> roles;

    // Standard Constructor
    public User(String name, List<String> roles) { ... }

    // Copy Constructor
    public User(User source) {
        this.name = source.name;
        // Perform Deep Copy manually for mutable fields
        this.roles = new ArrayList<>(source.roles);
    }
}
```
This gives you full control over Deep vs. Shallow copying and avoids the complex rules of `Object.clone()`.

### Q9: Does the Single Responsibility Principle (SRP) mean a class should only have one method?
**Answer:**
No. This is a common misconception.
* **SRP is about Cohesion.** A class can have 50 methods, as long as they all relate to the same responsibility or "actor."
* *Example:* A `UserAuthentication` class can have `login`, `logout`, `resetPassword`, and `validateToken`. These are four methods, but they serve **one actor** (the security system).
* SRP is violated if that same class also contains `saveUserToDatabase()` (Persistence responsibility) or `sendWelcomeEmail()` (Notification responsibility).

### Q10: With the rise of Annotations (since Java 5), are Marker Interfaces (like Serializable, Cloneable) obsolete?
**Answer:**
Mostly, yes, but not entirely.
* **Why Annotations are better:** They can carry metadata (parameters) and they don't pollute the class hierarchy (implementing an interface defines a type, annotations do not).
* **When Marker Interfaces are still used:** They are useful when you want to use **polymorphism** to enforce a check at compile-time.
    * *Example:* If you have a method `public void saveData(Serializable obj)`, the compiler ensures only `Serializable` objects are passed. With an annotation `@Persistable`, you would have to check explicitly at runtime using Reflection, which is slower and more error-prone.

### Q11: What is the relationship between the `hashCode()` and `equals()` contract and memory leaks?
**Answer:**
If you override `equals()`, you **must** override `hashCode()`.
* **The Scenario:** You insert objects into a `HashMap` or `HashSet`. These collections use the hash code to determine the "bucket" location.
* **The Bug:** If you implement `equals` but not `hashCode`, two objects that are logically "equal" will generate different hash codes. The Map will store them in different buckets.
* **The Consequence:** You might inadvertently add the "same" object to a Cache (Map) thousands of times because the Map thinks they are different keys. This causes a **memory leak** because the Garbage Collector cannot clean up these "duplicate" entries as they are still referenced by the Map.

### Q12: How does Dependency Injection (DI) support the Open/Closed Principle?
**Answer:**
DI allows you to supply the dependencies of a class from the outside (usually via a framework like Spring) rather than the class creating them itself (using `new`).
* If a class relies on an interface `PaymentProcessor`, and we inject a `CreditCardProcessor` today, the class works.
* If we want to extend the system to support `PayPalProcessor` tomorrow, we simply inject the new implementation.
* The original class code does **not** need to be modified (Closed for modification), but the behavior of the system has been extended (Open for extension).