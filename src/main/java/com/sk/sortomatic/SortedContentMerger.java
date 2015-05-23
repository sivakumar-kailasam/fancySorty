package com.sk.sortomatic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

/**
 * Handles Merging of previously sorted content chunks to provide truly Sorted Content
 *
 * @author Sivakumar Kailasam
 */
public class SortedContentMerger {


    public void writeSortedStreamCollectionToWriter(List<BufferedReader> contentCollection, final BufferedWriter bufferedFileWriter, int topNLinesToFind) throws IOException {
        PriorityQueue<CachedBufferedReader> queueOfBuffers = getBufferedReaderPriorityQueue();

        for (BufferedReader br : contentCollection) {
            queueOfBuffers.add(new CachedBufferedReader(br));
        }

        int noOfLinesProcessed = 0;

        while (queueOfBuffers.size() > 0 && (noOfLinesProcessed < topNLinesToFind)) {

            CachedBufferedReader br = queueOfBuffers.poll();

            String line = br.getLine();
            bufferedFileWriter.write(line);
            bufferedFileWriter.newLine();

            if (br.isEmpty()) {
                br.close();
            } else {
                queueOfBuffers.add(br);
            }

            noOfLinesProcessed++;
        }

        bufferedFileWriter.close();
    }


    private PriorityQueue<CachedBufferedReader> getBufferedReaderPriorityQueue() {
        return new PriorityQueue<CachedBufferedReader>(10, new Comparator<CachedBufferedReader>() {
            @Override
            public int compare(CachedBufferedReader firstCBR, CachedBufferedReader secondCBR) {

                BigInteger topNoInFirstBR = new BigInteger(firstCBR.getCachedLine());
                BigInteger topNoInSecondBR = new BigInteger(secondCBR.getCachedLine());

                return new ReverseBigIntComparator().compare(topNoInFirstBR, topNoInSecondBR);

            }
        });
    }


}
