package com.shannoncode.codecoop.parser;

import com.shannoncode.codecoop.validator.Validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Parses the input number into groups based on the modulus supplied.  It uses recursion.
 *
 * @author Stuart Shannon
 */
public class ModulusParser implements Parser
{
    private Validator validator;

    public ModulusParser(Validator validator)
    {
        this.validator = validator;
    }

    public List<Long> parse(long input, long modulus)
    {
        validator.validate(input);

        List<Long> result = new ArrayList<>();

        parse(result, input, modulus);
        Collections.reverse(result);

        return result;
    }

    private void parse(List<Long> result, long input, long modulus)
    {
        long remainder = input % modulus;
        long quotient = input / modulus;

        result.add(remainder);

        if (quotient != 0)
        {
            parse(result, quotient, modulus);
        }
    }
}
