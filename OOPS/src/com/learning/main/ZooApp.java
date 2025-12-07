package com.learning.main;

/*  ZooProject/
    ├── src/
    │   └── com/
    │       └── learning/
    │           ├── main/
    │           │   └── ZooApp.java          // The entry point (Main Class)
    │           ├── model/
    │           │   ├── Animal.java          // Abstract Base Class
    │           │   ├── Lion.java            // Concrete Class 1
    │           │   └── Parrot.java          // Concrete Class 2
    │           └── interfaces/
    │               └── Behavior.java        // Interface


 */

import com.learning.model.Animal;
import com.learning.model.Lion;
import com.learning.model.Parrot;

import java.util.ArrayList;
import java.util.List;

public class ZooApp {
    public static void main(String[] args) {

        // create specific objects
        Lion simba = new Lion("Simba", 5);
        Parrot polly = new Parrot("Polly", 2, "Green");

        // POLYMORPHISM IN ACTION
        // We can treat both Lion and Parrot as just "Animal"
        List<Animal> zoo = new ArrayList<>();
        zoo.add(simba);
        zoo.add(polly);

        System.out.println("--- Welcome to the Zoo ---");

        // Loop through the zoo
        for (Animal animal : zoo) {
            System.out.println("\nChecking on: " + animal.getName());

            // 1. Common method (Inheritance)
            animal.displayInfo();

            // 2. Overridden method (Polymorphism decides which sound to make)
            animal.makeSound();

            // 3. Interface method
            animal.eat();
        }

        System.out.println("--- Method Overloading vs Overriding ---");

        // 1. Testing Overriding
        // Java looks at the object type (Lion) and sees it has its own logic for makeSounds()
        simba.makeSound();

        // This calls the version with NO arguments
        simba.eat();

        // 2. Testing Overloading
        // Java looks at the ARGUMENTS to decide which method to run

        // Matches eat(String)
        simba.eat("Zebra meat");

        // Matches eat(String, int)
        simba.eat("Buffalo steak", 10);
    }
}