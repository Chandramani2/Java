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

# Senior Interview Q&A: SOLID Principles
*Focus: Architecture, Trade-offs, and Refactoring Strategies*

---

### Q1: How do you practically identify a violation of the Single Responsibility Principle (SRP) in a legacy codebase? It's subjective, so where do you draw the line?
**Answer:**
SRP is indeed subjective, but as a senior engineer, I look for **Cohesion** vs. **Coupling**.
1.  **Cohesion:** Do the fields and methods in this class "live together"? If I change one field, do I need to update methods A and B, but method C ignores it completely? If yes, method C likely belongs elsewhere.
2.  **The "Actor" Rule:** I ask, "Who requests changes to this class?" If the CFO asks for a change (billing logic) and the CTO asks for a change (persistence logic) in the same class, SRP is violated.
3.  **God Classes:** A class with excessive imports from different layers (UI, DB, Utility) is a clear smell.
* **Strategy:** I don't split classes just to make them smaller. I split them to separate *axes of change*.

### Q2: The Open/Closed Principle (OCP) suggests we should never modify existing code. Isn't this "Over-Engineering"? How do you balance OCP with YAGNI (You Ain't Gonna Need It)?
**Answer:**
This is a critical trade-off.
* **The Cost of OCP:** Strictly adhering to OCP requires creating interfaces, factories, and strategies for *everything*. This bloats the codebase and makes navigation harder.
* **The Pragmatic Approach:** I apply OCP only where change is **likely**.
    * If a module is stable (e.g., a standard tax calculation that hasn't changed in 10 years), I don't need a complex Strategy pattern.
    * If a module is volatile (e.g., a payment gateway aggregator that adds new providers monthly), I absolutely enforce OCP.
* **Refactoring Rule:** "Fool me once, shame on you. Fool me twice..." -> I usually implement concrete classes first. The *second* time I have to modify that class for a similar reason, I refactor it to use OCP (Abstract/Interface).

### Q3: Explain Liskov Substitution Principle (LSP) in terms of "Design by Contract" (Pre-conditions and Post-conditions).
**Answer:**
LSP is often misunderstood as just "can it compile?" In reality, it's about behavioral guarantees.
1.  **Pre-conditions:** A subclass cannot require *more* than the parent.
    * *Example:* If the Parent accepts any `int`, the Child cannot throw an error for negative numbers. That strengthens the pre-condition, breaking the parent's contract.
2.  **Post-conditions:** A subclass cannot deliver *less* than the parent.
    * *Example:* If the Parent guarantees the return value is always non-null, the Child cannot return `null`.
3.  **Invariants:** Conditions that must remain true (e.g., "size must equal number of elements") must be preserved by the subclass.

### Q4: How does the Interface Segregation Principle (ISP) relate to Microservices or API Client design?
**Answer:**
ISP is vital in distributed systems to prevent "False Coupling."
* **Scenario:** Imagine a monolithic `UserAPI` that exposes `login()`, `generateReport()`, and `deleteUser()`.
* **The Problem:** A generic "Report Service" might import the client library for `UserAPI`. Even though it only needs `generateReport()`, it is now coupled to `deleteUser()`. If we change the signature of `deleteUser()`, we might break the build of the "Report Service" unnecessarily.
* **The Solution:** Break the API specifications into role-based interfaces (e.g., `AuthenticationService`, `ReportingService`). Clients only depend on the specific slice of the API they need, reducing the blast radius of changes.

### Q5: Is Dependency Injection (DI) the same as the Dependency Inversion Principle (DIP)?
**Answer:**
No, they are distinct but related.
* **Dependency Inversion (DIP):** The *Principle*. It states that high-level modules should not depend on low-level modules; both should depend on abstractions. It is a design guideline.
* **Dependency Injection (DI):** The *Pattern/Mechanism*. It is a technique to implement DIP. It involves passing dependencies (objects) into a class (via constructor or setter) rather than the class creating them.
* *Note:* You can do DI without DIP (injecting a concrete class instead of an interface). You can also do DIP without a DI framework (by manually passing factories).

### Q6: Can you give a real-world example where following SOLID might hurt application performance?
**Answer:**
Yes. Excessive abstraction introduces overhead.
1.  **DIP & OCP:** Heavily abstracting code leads to deep call stacks and virtual method calls. While the JVM is good at optimizing this, in ultra-low-latency systems (like High-Frequency Trading), the overhead of dynamic dispatch and pointer chasing through interfaces can be unacceptable.
2.  **Object Creation:** splitting one "God Object" into 10 smaller, single-responsibility objects increases heap allocation and Garbage Collection pressure.
* **Verdict:** For 99% of enterprise apps, the maintainability gain outweighs the nanosecond performance loss. But for critical loops, we might intentionally violate SOLID (e.g., using a concrete final class) for speed.

### Q7: What is the "Anemic Domain Model" and how does it relate to SRP?
**Answer:**
* **Definition:** An Anemic Domain Model is when your entity classes (e.g., `Order`, `Customer`) contain only data (getters/setters) and no logic, while all business logic sits in "Service" classes.
* **The Conflict:** Some argue this follows SRP (Data separated from Logic). However, OOP purists (and Domain-Driven Design practitioners) argue this is an anti-pattern because it breaks Encapsulation.
* **Senior View:** A "Rich Domain Model" is usually better. If an `Order` knows how to `validate()` itself or `calculateTotal()`, it creates higher cohesion. I prefer putting logic *inside* the entity if that logic deals purely with the entity's internal state, and using Services only for orchestration (orchestrating multiple entities).

### Q8: A common "code smell" is the heavy use of `instanceof` checks or large `switch` statements based on types. Which SOLID principle does this violate and how do you fix it?
**Answer:**
* **Violation:** This violates the **Open/Closed Principle (OCP)**. Every time you add a new type, you must modify the `switch` statement or the `if-else` chain.
* **Fix:** Replace the conditional logic with **Polymorphism**.
    * Move the logic inside the specific classes (e.g., each class implements a `process()` method).
    * Alternatively, use the **Visitor Pattern** if you cannot modify the classes but need to add operations to them.

### Q9: Does the standard Java `List` interface violate the Interface Segregation Principle (ISP) or Liskov Substitution Principle (LSP)?
**Answer:**
* **Yes, arguably both.**
* **Why:** The `List` interface contains methods like `add()`, `remove()`, and `set()`. However, `Arrays.asList()` or `List.of()` return **immutable** lists.
* **The Crash:** If you call `.add()` on a `List.of(...)`, it throws an `UnsupportedOperationException`.
* **Senior Insight:** This technically violates LSP (the subclass refuses to perform the parent's contract) and ISP (the interface is too "fat" for immutable lists). However, the Java architects accepted this trade-off to avoid an explosion of interfaces (like `MutableList`, `ImmutableList`, `ReadableList`) for the sake of usability.

### Q10: How does the "Law of Demeter" (Principle of Least Knowledge) relate to SOLID?
**Answer:**
* **Definition:** "Don't talk to strangers." A method `f` of class `C` should only call methods of `C`, objects created within `f`, or objects passed as arguments to `f`. Avoid chaining: `getA().getB().getC().doSomething()`.
* **Relation:** It strongly supports **Coupling** reduction and the **Dependency Inversion Principle**.
* **Why:** Chaining tightly couples your class to the internal structure of dependencies. If `A` changes how it stores `B`, your code breaks. Breaking chains usually involves writing wrapper methods, which improves Encapsulation.

### Q11: "DRY" (Don't Repeat Yourself) is a good practice, but can it conflict with the Single Responsibility Principle (SRP)?
**Answer:**
* **Yes, frequently.** This is known as **"Coincidental Duplication."**
* **Scenario:** Two modules (e.g., `Shipping` and `Invoicing`) happen to have identical validation logic for an address *right now*.
* **The Mistake:** You extract that logic into a shared `AddressValidator`.
* **The Conflict:** Later, `Shipping` needs to change validation rules (e.g., allows PO Boxes), but `Invoicing` does not. You now have to add conditional flags to the shared validator, coupling two unrelated business domains.
* **Rule of Thumb:** If two pieces of code look the same but change for different *business reasons*, do not merge them. A little duplication is better than the wrong abstraction.

### Q12: How does the traditional "3-Tier Architecture" (Controller -> Service -> Repository) often violate the Dependency Inversion Principle (DIP)?
**Answer:**
* **The Issue:** In a standard 3-tier app, the Controller depends on the Service (implementation), and the Service depends on the Repository (implementation). The flow of dependencies points **downwards** towards the database.
* **The Violation:** High-level policy (Business Logic) depends on low-level details (Database access).
* **The Fix (Clean/Hexagonal Architecture):** Invert the dependency. The Service Layer should define an *interface* (e.g., `UserRepositoryPort`). The persistence layer (DAO) should *implement* that interface. Now, the Database depends on the Business Logic, not the other way around.

### Q13: Is the Singleton Pattern a violation of SRP?
**Answer:**
* **Yes.**
* **Reason:** A Singleton class has two responsibilities:
    1.  Managing its own lifecycle (creation and ensuring only one instance exists).
    2.  Performing its actual business logic.
* **Consequence:** It makes unit testing difficult because the state is global and hard to reset between tests. In modern Java, we prefer **Dependency Injection** (Singleton Scope managed by Spring Container) over the manual `getInstance()` Singleton pattern.

### Q14: Explain the "Square-Rectangle Problem" in the context of the Liskov Substitution Principle (LSP).
**Answer:**
* **The Setup:** A `Square` "is a" `Rectangle` mathematically. So we make `Square extends Rectangle`.
* **The Code:** `Rectangle` has `setWidth()` and `setHeight()`.
* **The Break:** If you set the width of a `Square` to 5, the height *must* also become 5 to remain a square.
* **The Violation:** A client expecting a `Rectangle` might set width to 5 and height to 10, expecting the area to be 50. If the object is a `Square`, the area might end up being 100 (if the last setter wins) or 25. The behavior of the base class is changed.
* **Conclusion:** Inheritance is about **Behavior**, not just data or real-world analogies. `Square` should not extend `Rectangle` if the setters behave differently.

### Q15: How do "Feature Flags" or "Toggles" relate to the Open/Closed Principle?
**Answer:**
* **Relation:** They are a mechanism to achieve OCP at the *deployment* level rather than the *class* level.
* **Usage:** Instead of modifying existing code to release a feature, you wrap the new code path in a toggle. The code is "Open" to extension (new path) but the old code remains "Closed" and safe (protected by the `false` flag).
* **Risk:** Overuse leads to messy conditional logic (complexity), so flags should be short-lived.

### Q16: Why is "Field Injection" (using `@Autowired` directly on fields) considered bad practice in Spring, and how does it relate to Testability (SOLID)?
**Answer:**
* **The Practice:**
    ```java
    @Service
    public class UserService {
        @Autowired private UserRepository repo; // Field Injection
    }
    ```
* **The Problem:** It hides dependencies. You cannot instantiate `UserService` in a pure JUnit test without using reflection or a Spring Context wrapper, because there is no constructor to pass a Mock Repository.
* **The Fix:** Use **Constructor Injection**. It makes dependencies explicit (you can't create the object without them) and allows easy injection of Mocks for testing.

### Q17: Interfaces vs. Abstract Classes: How does "versioning" or API evolution affect your choice between them?
**Answer:**
* **Pre-Java 8:** Interfaces were hard to evolve. If you added a method to an interface, you broke every single class implementing it. Abstract classes were safer because you could add a concrete method with a default implementation.
* **Post-Java 8:** Interfaces now support `default` methods, which mitigates this issue significantly.
* **Senior View:** Prefer Interfaces for defining *Types/Capabilities* (e.g., `Runnable`, `Serializable`). Use Abstract Classes only for skeletal implementations to share code state (fields) among closely related classes.

### Q18: "Tell, Don't Ask" is a principle often cited alongside Encapsulation. What does it mean?
**Answer:**
* **Ask:** Asking an object for data and then acting on it.
    * *Bad:* `if (wallet.getBalance() > 50) { wallet.setBalance(wallet.getBalance() - 50); }`
    * This logic belongs *inside* the Wallet.
* **Tell:** Telling the object what to do.
    * *Good:* `wallet.withdraw(50);`
* **Benefit:** This keeps the data and the logic that manipulates that data together (Cohesion/Encapsulation).

### Q19: How do you handle "Optional" dependencies without violating the Dependency Inversion Principle?
**Answer:**
* **Scenario:** A Service uses a `NotificationSender`, but it should work even if no email provider is configured.
* **Solution:** Do not put `null` checks everywhere.
    1.  **Null Object Pattern:** Create a `ConsoleNotificationSender` or `NoOpNotificationSender` that implements the interface but does nothing. Inject this when no real provider exists.
    2.  **Java `Optional`:** Inject `Optional<NotificationSender>` in the constructor (though Null Object is usually cleaner for dependencies).

### Q20: In a massive legacy monolith that violates every SOLID principle, where do you start?
**Answer:**
* **Don't Rewrite Everything:** That causes "Paralysis by Analysis."
* **Strangler Fig Pattern:** Identify *one* specific domain (e.g., "User Profile"). Build a new, clean module/microservice for it. Route traffic to the new service slowly.
* **Boy Scout Rule:** Leave the code cleaner than you found it. If you touch a file to fix a bug, do a *small* refactor (extract one method, rename one variable).
* **Tests First:** You cannot refactor safely without tests. Write "Characterization Tests" (tests that lock in current behavior, even if it's buggy) before changing the structure.