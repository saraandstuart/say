package com.shannoncode.codecoop.misc;

/**
 * @author Stuart Shannon
 */
public class Hundred implements Expression
{
    private Expression simple;

    public Hundred() {
        this(new Digit());
    }

    public Hundred(Expression simple) {
        this.simple = simple;
    }

    public String interpret(long number)
    {
        return simple.interpret(number) + " " + "hundred";
    }
}
