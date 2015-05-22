package com.sk.sortomatic;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Sivakumar Kailasam
 */
public class ReverseBigIntComparatorTest {

    private ReverseBigIntComparator reverseBigIntComparator;

    @Before
    public void setUp() throws Exception {
        reverseBigIntComparator = new ReverseBigIntComparator();
    }


    private void assertReturnValueForTwoNumbers(BigInteger firstNumber, BigInteger secondNumber, int returnValue) {

        int result = reverseBigIntComparator.compare(firstNumber, secondNumber);
        assertThat(result).isEqualTo(returnValue);

    }


    @Test
    public void compare_TwoNumbersAreEqual_ReturnsZero() throws Exception {
        assertReturnValueForTwoNumbers(BigInteger.ONE, BigInteger.ONE, 0);
    }


    @Test
    public void compare_FirstNoIsSmaller_ReturnOne() throws Exception {
        assertReturnValueForTwoNumbers(BigInteger.ZERO, BigInteger.ONE, 1);
    }


    @Test
    public void compare_SecondNoIsSmaller_ReturnNegOne() throws Exception {
        assertReturnValueForTwoNumbers(BigInteger.ONE, BigInteger.ZERO, -1);
    }


}
