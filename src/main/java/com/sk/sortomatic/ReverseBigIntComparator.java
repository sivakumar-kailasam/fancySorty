package com.sk.sortomatic;

import java.math.BigInteger;
import java.util.Comparator;

/**
 * @author Sivakumar Kailasam
 */
public class ReverseBigIntComparator implements Comparator<BigInteger> {

    @Override
    public int compare(BigInteger firstNumber, BigInteger secondNumber) {
        return secondNumber.compareTo(firstNumber);
    }

}
