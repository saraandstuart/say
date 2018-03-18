package com.shannoncode.codecoop;

import com.shannoncode.codecoop.parser.ModulusParser;
import com.shannoncode.codecoop.parser.Parser;
import com.shannoncode.codecoop.validator.MinMaxValidator;
import com.shannoncode.codecoop.validator.Validator;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class SayTest
{
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    private Say say;

    @Before
    public void setup()
    {
        Validator validator = new MinMaxValidator(0L, 999_999_999_999L);

        Parser tenParser = new ModulusParser(10L, validator);
        Parser hundredParser = new ModulusParser(100L, validator);
        Parser thousandParser = new ModulusParser(1_000L, validator);
        Parser millionParser = new ModulusParser(1_000_000L, validator);
        Parser billionParser = new ModulusParser(1_000_000_000L, validator);

        say = new Say(validator,
                      tenParser,
                      hundredParser,
                      thousandParser,
                      millionParser,
                      billionParser);
    }

    @Test
    @Parameters(method = "parametersToSayInEnglish")
    public void inEnglish(long input, String expected)
    {
        //given

        //when
        String actual = say.inEnglish(input);

        //then
        assertThat(actual, is(expected));
    }

    @SuppressWarnings("unused")
    private Object[] parametersToSayInEnglish()
    {
        return new Object[] {
                new Object[] { 0L, "zero"},
                new Object[] { 1L, "one"},
                new Object[] { 14L, "fourteen"},
                new Object[] { 20L, "twenty"},
                new Object[] { 22L, "twenty-two"},
                new Object[] { 100L, "one hundred"},
                new Object[] { 123L, "one hundred twenty-three"},
                new Object[] { 53L, "fifty-three"},
                new Object[] { 1_000L, "one thousand"},
                new Object[] { 1_234L, "one thousand two hundred thirty-four"},
                new Object[] { 1_000_000L, "one million"},
                new Object[] { 1_002_345L, "one million two thousand three hundred forty-five"},
                new Object[] { 1_000_000_000L, "one billion"},
                new Object[] { 987_654_321_123L, "nine hundred eighty-seven billion six hundred fifty-four million three hundred twenty-one thousand one hundred twenty-three"}
        };
    }

    @Test
    @Parameters(method = "parametersToValidate")
    public void numbersOutOfRange(long input, String expectedMessage)
    {
        //given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(expectedMessage);

        //when
        say.inEnglish(input);

        //then
        //exception is thrown and validated as above
    }

    @SuppressWarnings("unused")
    private Object[] parametersToValidate()
    {
        return new Object[] {
                new Object[] { -1L, "numbers below 0 are not supported"},
                new Object[] { -2L, "numbers below 0 are not supported"},
                new Object[] { 1_000_000_000_000L, "numbers above 999999999999 are not supported"},
                new Object[] { 1_000_000_000_001L, "numbers above 999999999999 are not supported"}
        };
    }

}
