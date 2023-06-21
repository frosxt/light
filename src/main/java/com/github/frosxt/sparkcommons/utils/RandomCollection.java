/*
 *     SparkCommons
 *     Copyright (C) 2023 frosxt
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License in LICENSE.MD
 *     Please refer to that file for details.
 */

package com.github.frosxt.sparkcommons.utils;

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
