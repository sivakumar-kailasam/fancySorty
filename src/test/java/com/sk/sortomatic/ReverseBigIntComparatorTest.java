package com.sk.sortomatic;

import org.fest.assertions.Assert;
import org.fest.assertions.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

/**
 * @author Sivakumar Kailasam
 */
public class ReverseBigIntComparatorTest {

    private ReverseBigIntComparator reverseBigIntComparator;

    @Before
    public void setUp() throws Exception {
        reverseBigIntComparator = new ReverseBigIntComparator();
    }

    @Test
    public void whenTwoNumbersAreEqualReturnsZero() throws Exception {

        int result = reverseBigIntComparator.compare(BigInteger.ONE, BigInteger.ONE);

        Assertions.assertThat(result).isEqualTo(0);

    }

    @Test
    public void whenFirstNoIsSmallerReturnOne() throws Exception {

        int result = reverseBigIntComparator.compare(BigInteger.ZERO, BigInteger.ONE);

        Assertions.assertThat(result).isEqualTo(1);

    }


    @Test
    public void whenSecondNoIsSmallerReturnNegOne() throws Exception {

        int result = reverseBigIntComparator.compare(BigInteger.ONE, BigInteger.ZERO);

        Assertions.assertThat(result).isEqualTo(-1);

    }
}
