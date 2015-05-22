package com.sk.sortomatic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Sivakumar Kailasam
 */
public final class CollectionSorter {


    public List<BigInteger> sortReverse(List<BigInteger> numbersToSort) {

        List<BigInteger> sortedNumbers = new ArrayList<BigInteger>(numbersToSort);

        Comparator<BigInteger> comparator = new ReverseBigIntComparator();

        Collections.sort(sortedNumbers, comparator);

        return sortedNumbers;

    }
}
