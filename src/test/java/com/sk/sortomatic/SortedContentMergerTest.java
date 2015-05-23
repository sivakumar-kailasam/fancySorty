package com.sk.sortomatic;

import org.junit.Before;
import org.junit.Test;
import sun.nio.cs.StandardCharsets;

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


    @Before
    public void setUp() throws Exception {
        sortedContentMerger = new SortedContentMerger();
    }


    @Test
    public void writeSortedStreamCollectionToWriter_LargerNoOfRowsThanContent_SortsInCorrectOrder() throws Exception {
        checkOutputForNnos("11\n10\n9\n8\n7\n6\n5\n4\n3\n2\n1\n0\n", 15);
    }


    @Test
    public void writeSortedStreamCollectionToWriter_SmallerNoOfRows_ReturnsExpectedSmallNoOfRows() throws Exception {
        checkOutputForNnos("11\n10\n9\n8\n7\n", 5);
    }

    private void checkOutputForNnos(String resultContent, int noOfLinesToBeFound) throws Exception {
        BufferedReader firstBR = new BufferedReader(new StringReader("11\n9\n7\n"));
        BufferedReader secondBR = new BufferedReader(new StringReader("5\n3\n1\n"));
        BufferedReader thirdBR = new BufferedReader(new StringReader("10\n8\n6\n"));
        BufferedReader fourthBR = new BufferedReader(new StringReader("4\n2\n0\n"));

        List<BufferedReader> contentCollection = Arrays.asList(firstBR, secondBR, thirdBR, fourthBR);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        Charset charset = Charset.forName("UTF-8");
        OutputStreamWriter outputSW = new OutputStreamWriter(outputStream, charset);
        BufferedWriter bufferedFileWriter = new BufferedWriter(outputSW);

        sortedContentMerger.writeSortedStreamCollectionToWriter(contentCollection, bufferedFileWriter, noOfLinesToBeFound);

        String sortedContent = new String(outputStream.toByteArray(), charset);

        assertThat(sortedContent).isEqualTo(resultContent);
    }


}
