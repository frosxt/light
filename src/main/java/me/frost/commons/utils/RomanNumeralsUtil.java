package me.frost.commons.utils;

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