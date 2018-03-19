package com.shannoncode.codecoop;

import com.google.common.base.Strings;
import com.shannoncode.codecoop.interpreter.*;
import com.shannoncode.codecoop.parser.Parser;
import com.shannoncode.codecoop.validator.Validator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Stuart Shannon
 */
class Say
{
    private static final String HUNDRED = "hundred";
    private static final String THOUSAND = "thousand";
    private static final String MILLION = "million";
    private static final String BILLION = "billion";

    private static Map<Long, String> DIGITS = new HashMap<>();
    private static Map<Long, String> TEENS = new HashMap<>();
    private static Map<Long, String> TENS = new HashMap<>();

    static
    {
        DIGITS.put(1L, "one");
        DIGITS.put(2L, "two");
        DIGITS.put(3L, "three");
        DIGITS.put(4L, "four");
        DIGITS.put(5L, "five");
        DIGITS.put(6L, "six");
        DIGITS.put(7L, "seven");
        DIGITS.put(8L, "eight");
        DIGITS.put(9L, "nine");
        DIGITS.put(10L, "ten");

        TEENS.put(11L, "eleven");
        TEENS.put(12L, "twelve");
        TEENS.put(13L, "thirteen");
        TEENS.put(14L, "fourteen");
        TEENS.put(15L, "fifteen");
        TEENS.put(16L, "sixteen");
        TEENS.put(17L, "seventeen");
        TEENS.put(18L, "eighteen");
        TEENS.put(19L, "nineteen");

        TENS.put(2L, "twenty");
        TENS.put(3L, "thirty");
        TENS.put(4L, "forty");
        TENS.put(5L, "fifty");
        TENS.put(6L, "sixty");
        TENS.put(7L, "seventy");
        TENS.put(8L, "eighty");
        TENS.put(9L, "ninety");
    }

    private Validator validator;

    private Parser parser;

    private Expression digit;
    private Expression teen;
    private Expression ten;
    private Expression hundred;
    private Expression thousand;
    private Expression million;
    private Expression billion;


    Say(Validator validator, Parser parser)
    {
        this.validator = validator;

        this.parser = parser;

        this.digit = new SmallNumber(DIGITS);
        this.teen = new SmallNumber(TEENS);
        this.ten = new SmallNumber(TENS);

        this.hundred = new LargeNumber(HUNDRED);
        this.thousand = new LargeNumber(THOUSAND);
        this.million = new LargeNumber(MILLION);
        this.billion = new LargeNumber(BILLION);
    }

    String inEnglish(long n)
    {
        validator.validate(n);

        if (n == 0L)
        {
            return "zero";
        }

        return inEnglishNonZero(n);
    }

    private String inEnglishNonZero(long n)
    {
        if (n < 11L)
        {
            return template(n, "", 10L, digit);
        }
        else if (n < 20L)
        {
            return template(n, "", 100L, teen);
        }
        else if (n < 100L)
        {
            return template(n, "-", 10L, ten);
        }
        else if (n < 1_000L)
        {
            return template(n, " ", 100L, hundred);
        }
        else if (n < 1_000_000L)
        {
            return template(n, " ", 1_000L, thousand);
        }
        else if (n < 1_000_000_000L)
        {
            return template(n, " ", 1_000_000L, million);
        }
        else if (n < 1_000_000_000_000L)
        {
            return template(n, " ", 1_000_000_000L, billion);
        }

        return null;
    }

    private String template(long n,
                            String separator,
                            long modulus,
                            Expression expression)
    {
        List<Long> groups = parser.parse(n, modulus);

        long group1 = groups.get(0);
        String group1Interpreted = expression.interpret(group1);

        StringBuilder result = new StringBuilder(group1Interpreted);

        if (groups.size() == 2)
        {
            long group2 = groups.get(1);
            String group2Interpreted = inEnglishNonZero(group2);

            if (!Strings.isNullOrEmpty(group2Interpreted))
            {
                result.append(separator);
                result.append(group2Interpreted);
            }
        }

        return result.toString();
    }

    private class LargeNumber implements Expression
    {
        private String scaleWord;

        LargeNumber(String scaleWord)
        {
            this.scaleWord = scaleWord;
        }

        public String interpret(long number)
        {
            return inEnglishNonZero(number) + " " + scaleWord;
        }
    }

}
