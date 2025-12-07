package com.learning.model;

/*
Inheritance: extends Animal. The Lion inherits name and age from Animal.
Polymorphism (Overriding): The Lion provides its own specific version of makeSound and eat.
 */
public class Lion extends Animal {

    public Lion(String name, int age) {
        super(name, age);
    }

    // ==========================================
    // 1. METHOD OVERRIDING (Runtime Polymorphism)
    // ==========================================
    // This replaces the parent/interface version.
    // Signature MUST match exactly: eat()
    @Override
    public void eat() {
        System.out.println(getName() + " is hunting generic prey (Overridden Method).");
    }

    // Overriding the abstract method from Animal
    @Override
    public void makeSound() {
        System.out.println("ROAR! (Overridden Method)");
    }

    @Override
    public void sleep() {
        System.out.println("Sleeping in the shade.");
    }

    // ==========================================
    // 2. METHOD OVERLOADING (Compile-time Polymorphism)
    // ==========================================
    // Same method name 'eat', but different parameters.

    // Variant A: Takes a String
    public void eat(String specificFood) {
        System.out.println(getName() + " is eating " + specificFood + " (Overloaded Method 1).");
    }

    // Variant B: Takes a String AND an Integer
    public void eat(String specificFood, int quantityKg) {
        System.out.println(getName() + " ate " + quantityKg + "kg of " + specificFood + " (Overloaded Method 2).");
    }
}