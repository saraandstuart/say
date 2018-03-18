package com.shannoncode.codecoop.interpreter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stuart Shannon
 */
public class Digit implements Expression
{
    public String interpret(long number)
    {
        return digits.getOrDefault(number, "");
    }

    private static Map<Long, String> digits = new HashMap<>();

    static
    {
        digits.put(1L, "one");
        digits.put(2L, "two");
        digits.put(3L, "three");
        digits.put(4L, "four");
        digits.put(5L, "five");
        digits.put(6L, "six");
        digits.put(7L, "seven");
        digits.put(8L, "eight");
        digits.put(9L, "nine");
        digits.put(10L, "ten");
    }
}
