package com.learning.model;

import com.learning.interfaces.Behavior;
/*
Encapsulation: We use private variables (name, age) and provide public getters to protect the data.

Abstract Class: We cannot create an object of type Animal directly; it is just a template for specific animals.
 */
// Abstract class implementing the interface
public abstract class Animal implements Behavior {

    // ENCAPSULATION: Private fields restricted to this class
    private String name;
    private int age;

    // Constructor
    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters (Allow read access to private data)
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Abstract method: Children MUST implement this specific logic
    public abstract void makeSound();

    // Concrete method: Common logic for all animals
    public void displayInfo() {
        System.out.println("Animal: " + this.name + " | Age: " + this.age);
    }
}