package com.aisa.poc;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ObjectComparator {

    /**
     * Compares two objects of the same type and returns a list of differences.
     * Each difference is represented as a String describing the field name and its values.
     */
    public static List<String> compareObjects(Object obj1, Object obj2) throws IllegalAccessException {
        List<String> differences = new ArrayList<>();

        // Basic null and type checks.
        if (obj1 == null && obj2 == null) {
            return differences;
        }
        if (obj1 == null || obj2 == null) {
            differences.add("One of the objects is null.");
            return differences;
        }
        if (!obj1.getClass().equals(obj2.getClass())) {
            differences.add("Objects are of different types: " +
                    obj1.getClass().getName() + " vs " + obj2.getClass().getName());
            return differences;
        }

        // Iterate through all declared fields.
        Field[] fields = obj1.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true); // Allow access to private fields.

            Object value1 = field.get(obj1);
            Object value2 = field.get(obj2);

            // Handle both null values.
            if (value1 == null && value2 == null) {
                continue;
            }

            // If one is null or they don't equal each other.
            if ((value1 == null && value2 != null) || (value1 != null && !value1.equals(value2))) {
                differences.add("Field '" + field.getName() + "' differs: " +
                        value1 + " vs " + value2);
            }
        }
        return differences;
    }

    // Example usage.
    public static void main(String[] args) throws IllegalAccessException {
        // Example class.
        class Person {
            String name;
            int age;
            Person(String name, int age) { this.name = name; this.age = age; }
        }

        Person person1 = new Person("Alice", 30);
        Person person2 = new Person("Alice", 35);

        List<String> diffs = compareObjects(person1, person2);
        if (diffs.isEmpty()) {
            System.out.println("Objects are equal.");
        } else {
            System.out.println("Differences found:");
            diffs.forEach(System.out::println);
        }
    }
}
