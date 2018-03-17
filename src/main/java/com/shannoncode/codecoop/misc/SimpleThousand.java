package com.shannoncode.codecoop.misc;

/**
 * @author Stuart Shannon
 */
public class SimpleThousand implements Expression
{
    private Expression simple;

    public SimpleThousand() {
        this(new Digit());
    }

    public SimpleThousand(Expression simple) {
        this.simple = simple;
    }

    public String interpret(long number)
    {
        return simple.interpret(number) + " " + "thousand";
    }

}
