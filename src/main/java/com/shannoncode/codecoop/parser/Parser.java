package com.shannoncode.codecoop.parser;

import java.util.List;

/**
 * @author Stuart Shannon
 */
public interface Parser
{
    List<Long> parse(long input, long modulus);
}
