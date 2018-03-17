package com.shannoncode.codecoop.parser;

import com.shannoncode.codecoop.validator.MinMaxValidator;
import com.shannoncode.codecoop.validator.Validator;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Stuart Shannon
 */
@RunWith(JUnitParamsRunner.class)
public class ModulusParserTest
{
    private Validator validator;

    @Before
    public void setup() {
        validator = new MinMaxValidator(0L, 999_999_999_999L);
    }

    @Test
    @Parameters(method = "parametersToParse")
    public void parse(long modulus, long input, List<Long> expected)
    {
        //given
        Parser parser = new ModulusParser(modulus, validator);

        //when
        List<Long> actual = parser.parse(input);

        //then
        assertThat(actual, is(expected));
    }

    @SuppressWarnings("unused")
    private Object[] parametersToParse()
    {
        return new Object[] {
                //Million parser
                new Object[] { 1_000_000L, 234_567_890L, Arrays.asList(234L, 567_890L)},

                //Thousand parser
                new Object[] { 1_000L, 0L, Collections.singletonList(0L)},
                new Object[] { 1_000L, 1_234_567_890L, Arrays.asList(1L, 234L, 567L, 890L)},
                new Object[] { 1_000L, 1_000L, Arrays.asList(1L, 0L)},
                new Object[] { 1_000L, 2_034L, Arrays.asList(2L, 34L)},

                //Hundred parser
                new Object[] { 100L, 1_234_567_890L, Arrays.asList(12L, 34L, 56L, 78L, 90L)},
                new Object[] { 100L, 100L, Arrays.asList(1L, 0L)},
                new Object[] { 100L, 200L, Arrays.asList(2L, 0L)},
                new Object[] { 100L, 1_000L, Arrays.asList(10L, 0L)},
                new Object[] { 100L, 2_034L, Arrays.asList(20L, 34L)},
                new Object[] { 100L, 234L, Arrays.asList(2L, 34L)},
                new Object[] { 100L, 34L, Collections.singletonList(34L)},
                new Object[] { 100L, 4L, Collections.singletonList(4L)},

                //Ten parser
                new Object[] { 10L, 1_234_567_890L, Arrays.asList(1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 0L)},
                new Object[] { 10L, 100L, Arrays.asList(1L, 0L, 0L)},
                new Object[] { 10L, 200L, Arrays.asList(2L, 0L, 0L)},
                new Object[] { 10L, 1_000L, Arrays.asList(1L, 0L, 0L, 0L)},
                new Object[] { 10L, 2_034L, Arrays.asList(2L, 0L, 3L, 4L)},
                new Object[] { 10L, 234L, Arrays.asList(2L, 3L, 4L)},
                new Object[] { 10L, 34L, Arrays.asList(3L, 4L)},
                new Object[] { 10L, 4L, Collections.singletonList(4L)}
        };
    }
}
