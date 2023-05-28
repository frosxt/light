package me.frost.commons.utils;

import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

public final class RandomCollection<E> {
    private final NavigableMap<Double, E> map = new TreeMap<>();
    private final Random random;
    private double total = 0;

    public RandomCollection() {
        this(new Random());
    }

    public RandomCollection(final Random random) {
        this.random = random;
    }

    public RandomCollection<E> add(final double weight, final E result) {
        if (weight <= 0) {
            return this;
        }
        total += weight;
        map.put(total, result);
        return this;
    }

    public E next() {
        final double value = random.nextDouble() * total;
        return map.higherEntry(value).getValue();
    }
}
