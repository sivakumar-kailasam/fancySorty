package com.sk.sortomatic;

import java.math.BigInteger;
import java.util.Comparator;

/**
 * Comparator for {@link BigInteger} for reverse comparison of two numbers
 *
 * @author Sivakumar Kailasam
 */
public final class ReverseBigIntComparator implements Comparator<BigInteger> {

    @Override
    public int compare(BigInteger firstNumber, BigInteger secondNumber) {
        return secondNumber.compareTo(firstNumber);
    }

}
