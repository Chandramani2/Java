# 🧵 Java Concurrency Control: The Deep Dive

> **The Problem:** CPU cores are fast. Memory is slow. Context switching is expensive.
> If you have a single variable `count = 0` and two threads increment it simultaneously, the result might be `1`, not `2`.

**Concurrency Control** is the art of managing access to shared mutable state to ensure **Safety** (Correctness) and **Liveness** (Progress).

---

## 1. The Java Memory Model (JMM) 🧠

Before writing code, you must understand how the JVM sees memory.



### Key Concepts:
1.  **Atomicity:** An operation is indivisible. (e.g., Reading a `long` in 32-bit Java is *not* atomic unless declared `volatile`).
2.  **Visibility:** Changes made by one thread are visible to others. (Solved by `volatile` or Locks).
3.  **Ordering:** The CPU/Compiler reorders instructions for performance. (Solved by **Happens-Before** relationships).

---

## 2. Evolution of Concurrency in Java 🧬

### Phase 1: The Classics (Intrinsic Locks) 🔒
The `synchronized` keyword uses the object's **Monitor Lock**.
* **Method Level:** Locks the instance (`this`) or class (`Class` object).
* **Block Level:** Locks a specific object.

```java
public synchronized void increment() {
    count++; 
}
// VS
public void increment() {
    synchronized(this) {
        count++;
    }
}
```

### Phase 2: `java.util.concurrent` (JUC) - Java 5 🛠️
Provides fine-grained control using **Lock Objects** and **Executors**.

* **ReentrantLock:** Manual locking. Allows `tryLock()` (non-blocking attempt) and Fairness policies.
* **ReadWriteLock:** Allows multiple readers but only one writer.
* **ExecutorService:** Decouples task submission (`Runnable`) from execution (Thread Pool).

### Phase 3: Asynchronous Programming - Java 8 ⚡
**CompletableFuture:** Pipeline processing without blocking the main thread.
```java
CompletableFuture.supplyAsync(() -> fetchOrder())
    .thenApply(order -> enrichOrder(order))
    .thenAccept(order -> save(order));
```

### Phase 4: Project Loom (Virtual Threads) - Java 21 🚀
The biggest shift in Java history.
* **Platform Threads (Old):** 1 Java Thread = 1 OS Thread (Heavy, expensive).
* **Virtual Threads (New):** M:N mapping. Millions of virtual threads run on a few OS threads.



---

## 3. Mechanisms for Thread Safety 🛡️

### A. Volatile Variable
Guarantees **Visibility** (reads bypass CPU cache and go to RAM) and prevents **Reordering**.
* *Limitation:* Does NOT guarantee Atomicity (e.g., `count++` is strictly unsafe with just volatile).

### B. CAS (Compare-And-Swap) & Atomics
Optimistic locking supported by hardware instructions.
* **Class:** `AtomicInteger`, `AtomicReference`.
* **Logic:** "Set value to `B` only if current value is `A`." If it fails, retry in a loop (Spinlock).
* **Pros:** No context switch overhead. Fast under low contention.

### C. Synchronization Aids
* **CountDownLatch:** Wait for N tasks to finish.
* **CyclicBarrier:** Wait for N threads to meet at a point before proceeding together.
* **Semaphore:** Limit access to N concurrent threads (Rate Limiting).

---

# 🎓 Senior Developer Interview Q&A (15-20 Questions)

These questions focus on internals, race conditions, and architectural trade-offs.

### 🔥 Section 1: Internals & Memory Model

**Q1: Explain the "Happens-Before" relationship.**
* **A:** It is the guarantee provided by the JMM that memory writes by one specific statement are visible to another specific statement.
    * *Example:* Releasing a lock *happens-before* acquiring the same lock. Writing to a `volatile` variable *happens-before* reading it.

**Q2: Why is `i++` not thread-safe even if `i` is volatile?**
* **A:** `i++` is a compound operation: (1) Read, (2) Increment, (3) Write. `volatile` guarantees step 1 and 3 are visible, but two threads can perform step 1 simultaneously, leading to a lost update. You need `AtomicInteger` or `synchronized`.

**Q3: What is "False Sharing" and how do you prevent it in Java?**
* **A:** It occurs when two distinct variables (used by different threads) sit on the same **CPU Cache Line** (usually 64 bytes). When Thread A updates `Var1`, the CPU invalidates the entire cache line, forcing Thread B (using `Var2`) to reload from RAM.
    * *Fix:* Use `@Contended` annotation (Java 8+) to pad the variable in memory.

**Q4: Can you implement a Singleton using Double-Checked Locking? Why is `volatile` required?**
* **A:**
    ```java
    if (instance == null) {
        synchronized(this) {
            if (instance == null) instance = new Singleton();
        }
    }
    ```
    * Without `volatile`, the JIT compiler might reorder the instruction `instance = new Singleton()`. It might publish the reference *before* the constructor finishes initializing fields. Another thread might see a non-null but partially constructed object. `volatile` prevents this instruction reordering.

**Q5: What is a "Spurious Wakeup"?**
* **A:** A thread waiting on a condition (via `wait()` or `Condition.await()`) might wake up without being notified. This is an OS-level quirk.
    * *Fix:* Always call `wait()` inside a `while` loop checking the condition, never an `if`.

---

### 🛠️ Section 2: Locking & CAS

**Q6: ReentrantLock vs. Synchronized: When to choose which?**
* **A:**
    * **Synchronized:** Simpler, cleaner (no `finally` block needed), implicitly handles exceptions, better JVM optimization (Lock Coarsening/Biased Locking).
    * **ReentrantLock:** Required if you need: Fairness (Queue order), `tryLock()` (timeout), or multiple Condition variables (e.g., `notEmpty`, `notFull`).

**Q7: Explain the ABA Problem in CAS operations.**
* **A:** Thread 1 reads value `A`. Thread 2 changes `A -> B` and then `B -> A`. Thread 1 checks, sees `A`, and thinks nothing changed, so it proceeds. This causes bugs in structures like Stacks.
    * *Fix:* Use `AtomicStampedReference` which pairs the value with a version number (`[A, 1] -> [B, 2] -> [A, 3]`).

**Q8: What is Lock Support `park()` and `unpark()`?**
* **A:** These are the low-level primitives used to build Locks. `park()` disables the current thread (taking it off the CPU scheduler) until a permit is available. It is more efficient than `wait/notify` because it doesn't require acquiring a monitor lock first.

---

### 🧵 Section 3: Thread Pools & Executors

**Q9: Why is `Executors.newCachedThreadPool()` dangerous in production?**
* **A:** It has no upper limit on the number of threads (`Integer.MAX_VALUE`). If tasks arrive faster than they are processed, it will spawn thousands of threads, causing OutOfMemoryError (OOM) or thrashing the OS with context switches. Use `ThreadPoolExecutor` with a bounded max pool size.

**Q10: Explain the difference between `shutdown()` and `shutdownNow()`.**
* **A:**
    * `shutdown()`: Stops accepting new tasks but lets currently running and *queued* tasks finish.
    * `shutdownNow()`: Attempts to stop running tasks (via interruption) and returns a list of *unstarted* tasks that were in the queue.

**Q11: How does the `ForkJoinPool` differ from a standard `ThreadPoolExecutor`?**
* **A:** It implements **Work Stealing**. Each worker thread has its own Deque. If a thread finishes its work, it "steals" a task from the *tail* of another thread's queue. This is optimized for recursive divide-and-conquer algorithms (like Parallel Streams).

**Q12: What happens if an unhandled exception is thrown inside a Thread Pool task?**
* **A:** The thread running the task dies. The pool will replace it with a new thread. However, the exception is swallowed unless you capture the `Future` returned by `submit()` and call `get()`, or override `afterExecute()` in the ThreadPool.

---

### 🚀 Section 4: Modern Java & Advanced Scenarios

**Q13: What is the main limitation of Java Virtual Threads (Project Loom)?**
* **A:** **Pinning.** If a virtual thread performs a blocking operation while holding a native `synchronized` block (or native JNI code), it "pins" the underlying OS carrier thread. The carrier cannot unmount the virtual thread, reducing scalability.
    * *Fix:* Use `ReentrantLock` instead of `synchronized` inside virtual threads for now.

**Q14: Explain `ThreadLocal` and the risk of Memory Leaks.**
* **A:** `ThreadLocal` stores data specific to a thread. In a Thread Pool (like Tomcat), threads are reused. If you set a `ThreadLocal` variable but fail to `remove()` it after the request, the value stays attached to the thread. This causes memory leaks and data pollution (User A seeing User B's data).

**Q15: How do you debug a Deadlock in production?**
* **A:**
    1.  **Thread Dump:** Trigger a dump (via `jstack` or VisualVM). Look for "Found one Java-level deadlock."
    2.  **Analysis:** Identify the cycle (Thread A holds Lock 1 wants Lock 2; Thread B holds Lock 2 wants Lock 1).
    3.  **Fix:** Enforce a strict ordering of lock acquisition (Always lock resource A before B).

**Q16: `CompletableFuture`: `thenApply` vs `thenCompose`?**
* **A:** Equivalent to `map` vs `flatMap` in Streams.
    * `thenApply`: Returns `CompletableFuture<U>` (Wraps the result).
    * `thenCompose`: Returns `CompletableFuture<U>` (Flattens nested futures). Used when the callback function itself returns a Future.

**Q17: What is a Livelock?**
* **A:** Threads are not blocked, but they are stuck in a loop changing state in response to each other without making progress. (e.g., Two people in a hallway stepping left/right simultaneously to let the other pass).

**Q18: Optimistic vs. Pessimistic Locking in Java?**
* **A:**
    * **Pessimistic:** `synchronized`, `ReentrantLock`. "I assume conflict will happen, so I lock everything." (High overhead).
    * **Optimistic:** `Atomic`, `StampedLock` (Optimistic Read). "I assume conflict won't happen. I'll read, check if data changed, and retry if it did." (Low overhead, better for reads).