package com.aisa.poc;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class ObjectComparatorExpFields{

    public static List<String> compareObjects(Object obj1, Object obj2, Set<String> ignoredFields) throws IllegalAccessException {
        List<String> differences = new ArrayList<>();

        if (obj1 == null && obj2 == null) {
            return differences;
        }
        if (obj1 == null || obj2 == null) {
            differences.add("One of the objects is null.");
            return differences;
        }
        if (!obj1.getClass().equals(obj2.getClass())) {
            differences.add("Objects are of different types: " + obj1.getClass().getName() + " vs " + obj2.getClass().getName());
            return differences;
        }

        Field[] fields = obj1.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);

            if (ignoredFields.contains(field.getName())) {
                continue;
            }

            Object value1 = field.get(obj1);
            Object value2 = field.get(obj2);

            if (value1 == null && value2 == null) continue;
            if (value1 == null || value2 == null || !areValuesEqual(value1, value2)) {
                differences.add("Field '" + field.getName() + "' of object1 differs: " + value1 +
                        " vs field '" + field.getName() + "' of object2: " + value2);
            }
        }
        return differences;
    }

    private static boolean areValuesEqual(Object value1, Object value2) throws IllegalAccessException {
        if (value1 == null || value2 == null) return value1 == value2;

        if (value1.getClass().isArray() && value2.getClass().isArray()) {
            return areArraysEqual(value1, value2);
        }

        if (value1 instanceof Enum && value2 instanceof Enum) {
            return value1.equals(value2);
        }

        if (value1 instanceof Collection && value2 instanceof Collection) {
            return areCollectionsEqual((Collection<?>) value1, (Collection<?>) value2);
        }

        if (value1 instanceof Map && value2 instanceof Map) {
            return areMapsEqual((Map<?, ?>) value1, (Map<?, ?>) value2);
        }

        if (isSupportedNonPrimitive(value1.getClass())) {
            return value1.equals(value2);
        }

        if (!value1.getClass().isPrimitive() && !value1.getClass().getName().startsWith("java.")) {
            return compareObjects(value1, value2, Collections.emptySet()).isEmpty();
        }

        return value1.equals(value2);
    }

    private static boolean areArraysEqual(Object array1, Object array2) {
        int length1 = Array.getLength(array1);
        int length2 = Array.getLength(array2);
        if (length1 != length2) return false;

        for (int i = 0; i < length1; i++) {
            Object element1 = Array.get(array1, i);
            Object element2 = Array.get(array2, i);
            if (!Objects.equals(element1, element2)) {
                return false;
            }
        }
        return true;
    }

    private static boolean areCollectionsEqual(Collection<?> col1, Collection<?> col2) {
        return col1.size() == col2.size() && col1.containsAll(col2);
    }

    private static boolean areMapsEqual(Map<?, ?> map1, Map<?, ?> map2) {
        return map1.equals(map2);
    }

    private static boolean isSupportedNonPrimitive(Class<?> clazz) {
        return clazz == Boolean.class || clazz == Integer.class || clazz == Float.class || clazz == Double.class ||
                clazz == Long.class || clazz == Short.class || clazz == Byte.class || clazz == Character.class ||
                clazz == BigDecimal.class || clazz == BigInteger.class ||
                clazz == UUID.class || clazz == LocalDate.class || clazz == LocalDateTime.class;
    }

    public static void main(String[] args) throws IllegalAccessException {
        enum Status { ACTIVE, INACTIVE }

        class Sample {
            int id;
            Boolean active;
            double balance;
            BigDecimal tax;
            LocalDate date;
            LocalDateTime timestamp;
            int[] numbers;
            Status status;
            List<String> logs;
            Map<String, Integer> metadata;

            Sample(int id, Boolean active, double balance, BigDecimal tax, LocalDate date, LocalDateTime timestamp,
                   int[] numbers, Status status, List<String> logs, Map<String, Integer> metadata) {
                this.id = id;
                this.active = active;
                this.balance = balance;
                this.tax = tax;
                this.date = date;
                this.timestamp = timestamp;
                this.numbers = numbers;
                this.status = status;
                this.logs = logs;
                this.metadata = metadata;
            }
        }

        Sample obj1 = new Sample(1, true, 1000.50, new BigDecimal("15.75"),
                LocalDate.of(2023, 1, 15), LocalDateTime.of(2023, 1, 15, 10, 30),
                new int[]{1, 2, 3}, Status.ACTIVE, List.of("log1", "log2"), Map.of("key1", 100));

        Sample obj2 = new Sample(1, false, 1000.50, new BigDecimal("16.00"),
                LocalDate.of(2023, 1, 16), LocalDateTime.of(2023, 1, 15, 11, 30),
                new int[]{1, 2, 4}, Status.INACTIVE, List.of("log1", "log3"), Map.of("key1", 200));

        Set<String> ignoredFields = new HashSet<>(List.of("balance", "timestamp"));
        List<String> differences = compareObjects(obj1, obj2, ignoredFields);
        if (differences.isEmpty()) {
            System.out.println("Objects are equal.");
        } else {
            System.out.println("Differences found:");
            differences.forEach(System.out::println);
        }
    }
}

