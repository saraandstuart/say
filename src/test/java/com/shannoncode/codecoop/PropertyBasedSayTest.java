package com.shannoncode.codecoop;

import com.pholser.junit.quickcheck.Property;
import com.pholser.junit.quickcheck.runner.JUnitQuickcheck;
import com.shannoncode.codecoop.parser.ModulusParser;
import com.shannoncode.codecoop.parser.Parser;
import com.shannoncode.codecoop.validator.MinMaxValidator;
import com.shannoncode.codecoop.validator.Validator;
import org.junit.Before;
import org.junit.runner.RunWith;

import static org.hamcrest.CoreMatchers.both;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assume.assumeThat;
import static org.junit.Assert.assertThat;

/**
 * Just an example of how to setup property-based test, however, it's not a good candidate.
 *
 * @author Stuart Shannon
 */
@RunWith(JUnitQuickcheck.class)
public class PropertyBasedSayTest
{
    private Say say;

    @Before
    public void setup()
    {
        Validator validator = new MinMaxValidator(0L, 999_999_999_999L);

        Parser parser = new ModulusParser(validator);

        say = new Say(validator, parser);
    }

    /**
     * Using int because it works "out of the box" with int, whereas there are some configuration issues with long.
     * I.e. it's not immediately apparent why long doesn't "just work".
     */
    @Property(trials = 25)
    public void inEnglish(int input)
    {
        //given
        long longInput = input;
        assumeThat(longInput, is(both(greaterThan(0L)).and(lessThan(999_999_999_999L))));

        //when
        String actual = say.inEnglish(longInput);

        //then
        assertThat(actual, is(instanceOf(String.class)));
    }
}
