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
    private static Map<Long, String> digits = new HashMap<>();
    private static Map<Long, String> teens = new HashMap<>();
    private static Map<Long, String> tens = new HashMap<>();

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

        teens.put(11L, "eleven");
        teens.put(12L, "twelve");
        teens.put(13L, "thirteen");
        teens.put(14L, "fourteen");
        teens.put(15L, "fifteen");
        teens.put(16L, "sixteen");
        teens.put(17L, "seventeen");
        teens.put(18L, "eighteen");
        teens.put(19L, "nineteen");

        tens.put(2L, "twenty");
        tens.put(3L, "thirty");
        tens.put(4L, "forty");
        tens.put(5L, "fifty");
        tens.put(6L, "sixty");
        tens.put(7L, "seventy");
        tens.put(8L, "eighty");
        tens.put(9L, "ninety");
    }

    private Validator validator;

    private Parser tenParser;
    private Parser hundredParser;
    private Parser thousandParser;
    private Parser millionParser;
    private Parser billionParser;

    private Expression digit;
    private Expression teen;
    private Expression ten;
    private Expression hundred;
    private Expression thousand;
    private Expression million;
    private Expression billion;


    Say(Validator validator,
        Parser tenParser,
        Parser hundredParser,
        Parser thousandParser,
        Parser millionParser,
        Parser billionParser)
    {
        this.validator = validator;

        this.tenParser = tenParser;
        this.hundredParser = hundredParser;
        this.thousandParser = thousandParser;
        this.millionParser = millionParser;
        this.billionParser = billionParser;

        this.digit = new SmallNumber(digits);
        this.teen = new SmallNumber(teens);
        this.ten = new SmallNumber(tens);
        this.hundred = new Hundred();
        this.thousand = new LargeNumber("thousand");
        this.million = new LargeNumber("million");
        this.billion = new LargeNumber("billion");
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
            return digit.interpret(n);
        }
        else if (n < 20L)
        {
            return teen.interpret(n);
        }
        else if (n < 100L)
        {
            return template(n, "-", tenParser, ten);
        }
        else if (n < 1_000L)
        {
            return template(n, " ", hundredParser, hundred);
        }
        else if (n < 1_000_000L)
        {
            return template(n, " ", thousandParser, thousand);
        }
        else if (n < 1_000_000_000L)
        {
            return template(n, " ", millionParser, million);
        }
        else if (n < 1_000_000_000_000L)
        {
            return template(n, " ", billionParser, billion);
        }

        return null;
    }

    private String template(long n,
                            String separator,
                            Parser parser,
                            Expression expression)
    {
        List<Long> groups = parser.parse(n);

        long group1 = groups.get(0);
        long group2 = groups.get(1);

        String group1Interpreted = expression.interpret(group1);
        String group2Interpreted = inEnglishNonZero(group2);

        if (!Strings.isNullOrEmpty(group2Interpreted))
        {
            group2Interpreted = separator + group2Interpreted;
        }

        return group1Interpreted + group2Interpreted;
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
