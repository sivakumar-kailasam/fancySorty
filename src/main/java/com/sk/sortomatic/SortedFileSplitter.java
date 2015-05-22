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
public final class SortedFileSplitter {

    private static Logger logger = LoggerFactory.getLogger(SortedFileSplitter.class);

    private CollectionSorter collectionSorter;

    private long maxChunkSizeLimit;

    private File temporaryFolder;

    public SortedFileSplitter(String temporaryFolder, CollectionSorter collectionSorter, long maxChunkSizeLimit) {
        this.temporaryFolder = new File(temporaryFolder);
        if (!this.temporaryFolder.exists()) {
            logger.debug("Creating temporary folder {} since it doesn't exist", this.temporaryFolder.getAbsolutePath());
            this.temporaryFolder.mkdir();
        }
        this.collectionSorter = collectionSorter;
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

            List<BigInteger> sortedContent = collectionSorter.sortReverse(contentToBeSorted);

            File temporaryFile = createTempFileWithSortedContent(sortedContent);

            splitSortedFiles.add(temporaryFile);

            contentToBeSorted.clear();

        }

        return splitSortedFiles;
    }

    private File createTempFileWithSortedContent(List<BigInteger> sortedContent) throws IOException {
        File temporaryFile = File.createTempFile("sortomatic", ".tmp", this.temporaryFolder);
        temporaryFile.deleteOnExit();

        logger.debug("Created temp file {}", temporaryFile.getAbsolutePath());

        OutputStream outputStream = new FileOutputStream(temporaryFile);
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, Charset.defaultCharset()));

        for (BigInteger number : sortedContent) {
            bufferedWriter.write(number.toString());
            bufferedWriter.newLine();
        }

        bufferedWriter.close();
        return temporaryFile;
    }


}
