package com.sk.sortomatic;

import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author Sivakumar Kailasam
 */
public class SortyTest {

    private Sorty sorty;


    @Before
    public void setUp() throws Exception {
        sorty = new Sorty();
    }


    @Test
    public void givenEmptyCollectionReturnsAnEmptyCollection() throws Exception {

        List<BigInteger> nos = new ArrayList<BigInteger>();
        List<BigInteger> result = sorty.sortReverse(nos);

        assertNotNull(result);
        assertEquals(0, result.size());

    }


    @Test
    public void givenOneNumberInCollectionItReturnsIt() throws Exception {
        List<BigInteger> nos = Arrays.asList(BigInteger.ONE);
        List<BigInteger> result = sorty.sortReverse(nos);
        assertEquals(nos, result);
    }


    @Test
    public void givenTwoUnorderedNumbersInCollectionItOrdersIt() throws Exception {
        List<BigInteger> nos = Arrays.asList(BigInteger.ONE, BigInteger.TEN);
        List<BigInteger> expectedOutput = Arrays.asList(BigInteger.TEN, BigInteger.ONE);
        givenXUnorderedNumbersInCollectionItOrdersIt(nos, expectedOutput);
    }


    @Test
    public void givenFiveUnorderedNumbersInCollectionItOrdersIt() throws Exception {
        BigInteger bigInteger100 = BigInteger.valueOf(100L);
        BigInteger bigInteger300 = BigInteger.valueOf(300L);
        BigInteger bigInteger500 = BigInteger.valueOf(500L);

        List<BigInteger> nos = Arrays.asList(BigInteger.ONE, BigInteger.TEN, bigInteger100, bigInteger300, bigInteger500);
        List<BigInteger> expectedOutput = Arrays.asList(bigInteger500, bigInteger300, bigInteger100, BigInteger.TEN, BigInteger.ONE);
        givenXUnorderedNumbersInCollectionItOrdersIt(nos, expectedOutput);
    }


    private void givenXUnorderedNumbersInCollectionItOrdersIt(List<BigInteger> input, List<BigInteger> expectedOutput) {
        List<BigInteger> result = sorty.sortReverse(input);
        assertEquals(expectedOutput, result);
    }


}
