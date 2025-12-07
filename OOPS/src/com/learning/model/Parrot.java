package com.learning.model;
/*
Inheritance: extends Animal. The Lion inherits name and age from Animal.
Polymorphism (Overriding): The Lion provides its own specific version of makeSound and eat.
 */
public class Parrot extends Animal {

    private String featherColor;

    public Parrot(String name, int age, String featherColor) {
        super(name, age);
        this.featherColor = featherColor;
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Squawk!");
    }

    @Override
    public void eat() {
        System.out.println(getName() + " is pecking at seeds.");
    }

    @Override
    public void sleep() {
        System.out.println(getName() + " sleeps in the tree.");
    }

    // Unique method specific to Parrot
    public void fly() {
        System.out.println(getName() + " is flying with " + featherColor + " wings.");
    }
}