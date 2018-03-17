package com.shannoncode.codecoop.misc;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Stuart Shannon
 */
public class Ten implements Expression
{
    public String interpret(long number)
    {
        return tens.getOrDefault(number, "");
    }

    private static Map<Long, String> tens = new HashMap<>();

    static
    {
        tens.put(2L, "twenty");
        tens.put(3L, "thirty");
        tens.put(4L, "forty");
        tens.put(5L, "fifty");
        tens.put(6L, "sixty");
        tens.put(7L, "seventy");
        tens.put(8L, "eighty");
        tens.put(9L, "ninety");
    }
}
