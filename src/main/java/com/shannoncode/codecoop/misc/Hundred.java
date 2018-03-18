package com.shannoncode.codecoop.misc;

/**
 * @author Stuart Shannon
 */
public class Hundred implements Expression
{
    private Expression expression;

    public Hundred() {
        this(new Digit());
    }

    public Hundred(Expression expression) {
        this.expression = expression;
    }

    public String interpret(long number)
    {
        return expression.interpret(number) + " " + "hundred";
    }
}
