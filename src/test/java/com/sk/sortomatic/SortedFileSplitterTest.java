package com.sk.sortomatic;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.File;
import java.io.StringReader;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

/**
 * @author Sivakumar Kailasam
 */
public class SortedFileSplitterTest {

    private SortedFileSplitter sortedFileSplitter;
    
    @Before
    public void setUp() throws Exception {
        long testChunkSize = 10;
        sortedFileSplitter = new SortedFileSplitter("/tmp", new Sorty(), testChunkSize);
    }

    @Test
    public void whenReaderHasLessContentThanChunkLimitReturnOneSortedFile() throws Exception {
        BufferedReader inputReader = new BufferedReader(new StringReader("1\n3\n2\n"));
        List<File> result = sortedFileSplitter.splitSort(inputReader);
        assertEquals(1, result.size());
    }


    @Test
    public void whenReaderHasMoreContentThanChunkLimitReturnMoreThanOneFile() throws Exception {
        BufferedReader inputReader = new BufferedReader(new StringReader("1\n3\n2\n5\n7\n60\n454\n23\n20\n3453453\n345353453534"));
        List<File> result = sortedFileSplitter.splitSort(inputReader);
        assertThat(result.size()).isGreaterThan(1);
    }


}
