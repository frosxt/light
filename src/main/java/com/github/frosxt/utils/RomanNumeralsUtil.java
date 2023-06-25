/*
 *     Light
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

package com.github.frosxt.utils;

import java.util.TreeMap;

public final class RomanNumeralsUtil {
    private static final TreeMap<Integer, String> map = new TreeMap<>();

    // Private constructor to prevent instantiation of utility class
    private RomanNumeralsUtil() {
        throw new UnsupportedOperationException("RomanNumeralsUtil is a utility class and cannot be instantiated!");
    }

    public static String convertToRomanNumeral(final int number) {
        return number == map.floorKey(number) ? map.get(number) : map.get(map.floorKey(number)) + convertToRomanNumeral(number - map.floorKey(number));
    }

    static {
        map.put(1000, "M");
        map.put(900, "CM");
        map.put(500, "D");
        map.put(400, "CD");
        map.put(100, "C");
        map.put(90, "XC");
        map.put(50, "L");
        map.put(40, "XL");
        map.put(10, "X");
        map.put(9, "IX");
        map.put(5, "V");
        map.put(4, "IV");
        map.put(1, "I");
    }
}