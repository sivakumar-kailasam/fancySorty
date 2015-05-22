package com.sk.sortomatic;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * <p>
 * Wrapper over {@link java.io.BufferedReader} to cache the
 * previously read line unless we explicitly want to move
 * to the next line in the reader.
 * <p/>
 * <p>
 * This is especially useful when used with  {@link java.util.PriorityQueue PriorityQueue}
 * where comparator will constantly try to get the first line of the file.
 * </p>
 *
 * @author Sivakumar Kailasam
 */
public final class CachedBufferedReader {


    private BufferedReader bufferedReader;


    private String cachedLine;


    public CachedBufferedReader(BufferedReader bufferedReader) throws IOException {
        this.bufferedReader = bufferedReader;
        cacheLine();
    }


    private void cacheLine() throws IOException {
        this.cachedLine = this.bufferedReader.readLine();
    }


    public String getCachedLine() {
        return this.cachedLine;
    }

    public String getLine() throws IOException {
        String lineToReturn = this.cachedLine.toString();
        this.cacheLine();
        return lineToReturn;
    }

    /**
     * @return if cachedBufferedReader has no more content
     */
    public Boolean isEmpty() {
        return this.cachedLine == null;
    }

    public void close() throws IOException {
        this.bufferedReader.close();
    }


}
