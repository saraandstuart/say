package com.shannoncode.codecoop.interpreter;

import java.util.Map;

/**
 * @author Stuart Shannon
 */
public class SmallNumber implements Expression
{
    private Map<Long, String> map;

    public SmallNumber(Map<Long, String> map)
    {
        this.map = map;
    }

    public String interpret(long number)
    {
        return map.getOrDefault(number, "");
    }

}
