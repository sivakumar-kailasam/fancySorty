package com.sk.sortomatic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sivakumar Kailasam
 */
public class SortedFileSplitter {

    private static Logger logger = LoggerFactory.getLogger(SortedFileSplitter.class);

    private Sorty sorty;

    private long maxChunkSizeLimit;

    private File temporaryFolder;

    public SortedFileSplitter(String temporaryFolder, Sorty sorty, long maxChunkSizeLimit) {
        this.temporaryFolder = new File(temporaryFolder);
        if (!this.temporaryFolder.exists()) {
            this.temporaryFolder.mkdir();
        }
        this.sorty = sorty;
        this.maxChunkSizeLimit = maxChunkSizeLimit;
    }

    public List<File> splitSort(BufferedReader inputReader) throws IOException {

        List<File> splitSortedFiles = new ArrayList<File>();

        List<BigInteger> contentToBeSorted = new ArrayList<BigInteger>();
        String line = "";


        while (line != null) {

            long currentChunkSize = 0;

            while ((currentChunkSize < maxChunkSizeLimit) && ((line = inputReader.readLine()) != null)) {
                BigInteger noInTheLine = BigInteger.valueOf(Long.valueOf(line));
                contentToBeSorted.add(noInTheLine);
                currentChunkSize += line.length() * 2;
            }

            List<BigInteger> sortedContent = sorty.sortReverse(contentToBeSorted);

            File temporaryFile = File.createTempFile("sortomatic", ".tmp", this.temporaryFolder);
            temporaryFile.deleteOnExit();

            OutputStream outputStream = new FileOutputStream(temporaryFile);
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, Charset.defaultCharset()));

            for (BigInteger number : sortedContent) {
                bufferedWriter.write(number.toString());
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            splitSortedFiles.add(temporaryFile);
            contentToBeSorted.clear();

        }

        return splitSortedFiles;
    }


}
