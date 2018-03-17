package com.shannoncode.codecoop.validator;

/**
 * @author Stuart Shannon
 */
public class MinMaxValidator implements Validator
{
    private static final String MIN_EXCEPTION_MESSAGE = "numbers below %s are not supported";
    private static final String MAX_EXCEPTION_MESSAGE = "numbers above %s are not supported";

    private long min;
    private long max;


    public MinMaxValidator(long min, long max) {
        this.min = min;
        this.max = max;
    }

    public void validate(long input)
    {
        if (input < min) {
            throw new IllegalArgumentException(String.format(MIN_EXCEPTION_MESSAGE, min));
        }

        if (input > max) {
            throw new IllegalArgumentException(String.format(MAX_EXCEPTION_MESSAGE, max));
        }
    }

}
