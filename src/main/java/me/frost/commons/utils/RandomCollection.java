package me.frost.commons.utils;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public final class RandomCollection<E> {
    /**
     * Example usage
     * RandomCollection<String> randomCollection = new RandomCollection<>().add(40 - This is the chance, "dog").add(35, "cat").add(25, "horse");
     * Sysout(randomCollection.next());
     */

    private final NavigableMap<Double, E> map = new TreeMap<>();
    private final Random random;
    private double total = 0;

    public RandomCollection() {
        this(new Random());
    }

    public RandomCollection(Random random) {
        this.random = random;
    }

    public RandomCollection<E> add(double weight, E result) {
        if (weight <= 0) {
            return this;
        }
        total += weight;
        map.put(total, result);
        return this;
    }

    public E next() {
        double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}
