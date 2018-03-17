package com.shannoncode.codecoop.validator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

/**
 * @author Stuart Shannon
 */
@RunWith(JUnitParamsRunner.class)
public class MinMaxValidatorTest
{
    @Rule
    public ExpectedException thrown= ExpectedException.none();

    private Validator validator;

    @Before
    public void setup()
    {
        validator = new MinMaxValidator(0L, 10L);
    }

    @Test
    @Parameters(method = "parametersToValidate")
    public void validate(long input, String expectedMessage)
    {
        //given
        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(expectedMessage);

        //when
        validator.validate(input);

        //then
        //exception is thrown and validated as above
    }


    @SuppressWarnings("unused")
    private Object[] parametersToValidate()
    {
        return new Object[] {
                new Object[] { -1L, "numbers below 0 are not supported"},
                new Object[] { -2L, "numbers below 0 are not supported"},
                new Object[] { 11L, "numbers above 10 are not supported"},
                new Object[] { 12L, "numbers above 10 are not supported"}
        };
    }
}
