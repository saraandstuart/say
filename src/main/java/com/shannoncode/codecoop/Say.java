package com.shannoncode.codecoop;

import com.google.common.base.Strings;
import com.shannoncode.codecoop.misc.*;
import com.shannoncode.codecoop.parser.Parser;
import com.shannoncode.codecoop.validator.Validator;

import java.util.List;

/**
 * @author Stuart Shannon
 */
class Say
{
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

        this.digit = new Digit();
        this.teen = new Teen();
        this.ten = new Ten();
        this.hundred = new Hundred();
        this.thousand = new OneThousandAndLarger("thousand");
        this.million = new OneThousandAndLarger("million");
        this.billion = new OneThousandAndLarger("billion");
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

    private class OneThousandAndLarger implements Expression
    {
        private String scaleWord;

        OneThousandAndLarger(String scaleWord)
        {
            this.scaleWord = scaleWord;
        }

        public String interpret(long number)
        {
            return inEnglishNonZero(number) + " " + scaleWord;
        }
    }

}
