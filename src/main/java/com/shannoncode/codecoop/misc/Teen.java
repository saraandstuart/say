package com.shannoncode.codecoop.misc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stuart Shannon
 */
public class Teen implements Expression
{
    public String interpret(long number)
    {
        return teens.getOrDefault(number, "");
    }

    private static Map<Long, String> teens = new HashMap<>();

    static
    {
        teens.put(11L, "eleven");
        teens.put(12L, "twelve");
        teens.put(13L, "thirteen");
        teens.put(14L, "fourteen");
        teens.put(15L, "fifteen");
        teens.put(16L, "sixteen");
        teens.put(17L, "seventeen");
        teens.put(18L, "eighteen");
        teens.put(19L, "nineteen");
    }
}
