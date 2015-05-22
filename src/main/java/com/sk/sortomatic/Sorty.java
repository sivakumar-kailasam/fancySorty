package com.sk.sortomatic;

import java.math.BigInteger;
import java.util.*;

/**
 * @author Sivakumar Kailasam
 */
public class Sorty {


    public List<BigInteger> sort(List<BigInteger> numbersToSort, boolean sortInReverse) {
        List<BigInteger> sortedNumbers = new ArrayList<BigInteger>(numbersToSort);


        Comparator<BigInteger> comparator = null;

        if(sortInReverse){
            comparator = new Comparator<BigInteger>() {
                @Override
                public int compare(BigInteger firstNumber, BigInteger secondNumber) {
                    return secondNumber.compareTo(firstNumber);
                }
            } ;
        }

        Collections.sort(sortedNumbers, comparator);

        return sortedNumbers;
    }

    public List<BigInteger> sortReverse(List<BigInteger> numbersToSort) {
        return sort(numbersToSort, true);
    }
}
