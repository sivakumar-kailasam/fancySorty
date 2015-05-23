package com.sk.sortomatic;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Sorts collections (duh!)
 *
 * @author Sivakumar Kailasam
 */
public final class CollectionSorter {


    public List<BigInteger> sortReverse(List<BigInteger> numbersToSort) {
        List<BigInteger> sortedNumbers = new ArrayList<BigInteger>(numbersToSort);
        Collections.sort(sortedNumbers, new ReverseBigIntComparator());
        return sortedNumbers;
    }

}
