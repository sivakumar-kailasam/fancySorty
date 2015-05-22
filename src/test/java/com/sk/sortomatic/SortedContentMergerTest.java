package com.sk.sortomatic;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Sivakumar Kailasam
 */
public class SortedContentMergerTest {


    private SortedContentMerger sortedContentMerger;

    private String systemTempDirectory = System.getProperty("java.io.tmpdir");


    @Before
    public void setUp() throws Exception {
        sortedContentMerger = new SortedContentMerger();
    }


    @Test
    public void sortsContentCollectionInCorrectOrder() throws Exception {
        checkOutputForNnos("11\n10\n9\n8\n7\n6\n5\n4\n3\n2\n1\n0\n", 15);
    }


    @Test
    public void sortsContentCollectionInCorrectOrderOnlyForGivenNNos() throws Exception {
        checkOutputForNnos("11\n10\n9\n8\n7\n", 5);
    }

    private void checkOutputForNnos(String resultContent,int noOfLinesToBeFound) throws Exception{

        BufferedReader firstBR = new BufferedReader(new StringReader("11\n9\n7\n"));
        BufferedReader secondBR =new BufferedReader(new StringReader("5\n3\n1\n"));
        BufferedReader thirdBR =  new BufferedReader(new StringReader("10\n8\n6\n"));
        BufferedReader fourthBR = new BufferedReader(new StringReader("4\n2\n0\n"));

        List<BufferedReader> contentCollection = Arrays.asList(firstBR, secondBR, thirdBR, fourthBR);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedWriter bufferedFileWriter = new BufferedWriter(new OutputStreamWriter(outputStream,
                Charset.defaultCharset()));

        sortedContentMerger.writeSortedStreamCollectionToWriter(contentCollection, bufferedFileWriter, noOfLinesToBeFound);

        String sortedContent = new String(outputStream.toByteArray(), Charset.defaultCharset());

        assertThat(sortedContent).isEqualTo(resultContent);

    }


}
