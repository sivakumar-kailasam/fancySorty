package com.sk.sortomatic;

import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Facilitator(or entry point class) for the sortomatic app
 *
 * @author Sivakumar Kailasam
 */
public class Sortomatic {


    private static final long MAX_CHUNK_SIZE = 10 * 1024 * 1024;


    private static Logger logger = LoggerFactory.getLogger(Sortomatic.class);


    private File inputFile;


    private File outputFile;


    private int noOfRowsToPresentInOutput;


    private File temporaryFolder;


    public File getTemporaryFolder() {
        return this.temporaryFolder;
    }

    public File getInputFile() {
        return this.inputFile;
    }


    public File getOutputFile() {
        return this.outputFile;
    }

    public int getNoOfRowsToPresentInOutput() {
        return this.noOfRowsToPresentInOutput;
    }


    public static void main(String[] args) throws Exception {

        Sortomatic sortomatic = new Sortomatic();

        sortomatic.setupContextFromArgs(args);

        Object[] logOpts = new Object[]{sortomatic.getInputFile(), sortomatic.getOutputFile(), sortomatic.getTemporaryFolder(), sortomatic.getNoOfRowsToPresentInOutput()};
        logger.debug("Sortomatic initialized with \n input file {} \n output file {} \n temp folder {} \n top N rows to be found {}",logOpts );

        CollectionSorter sorter = new CollectionSorter();
        SortedFileSplitter sortedFileSplitter = new SortedFileSplitter(sortomatic.getTemporaryFolder(), sorter, MAX_CHUNK_SIZE);

        BufferedReader inputBufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(sortomatic.getInputFile()), Charset.defaultCharset()));
        List<File> splitFiles = sortedFileSplitter.splitSort(inputBufferedReader);
        inputBufferedReader.close();


        List<BufferedReader> bufferedReaders = new ArrayList<BufferedReader>();

        for (File file : splitFiles) {
            bufferedReaders.add(new BufferedReader(new InputStreamReader(new FileInputStream(file), Charset.defaultCharset())));
        }

        BufferedWriter outputBR = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sortomatic.getOutputFile()), Charset.defaultCharset()));

        SortedContentMerger sortedContentMerger = new SortedContentMerger();
        sortedContentMerger.writeSortedStreamCollectionToWriter(bufferedReaders, outputBR, sortomatic.getNoOfRowsToPresentInOutput());
        outputBR.close();
    }


    protected void setupContextFromArgs(String[] args) throws ParseException {

        CommandLineParser parser = new DefaultParser();

        Options options = new Options();

        options.addOption(Option.builder("i").longOpt("input-file").hasArg().desc("input file with full path").build());
        options.addOption(Option.builder("o").longOpt("output-file").hasArg().desc("location where output file has to be generated").build());
        options.addOption(Option.builder("n").longOpt("top-n-nos").hasArg().desc("top N numbers to be found").build());
        options.addOption(Option.builder("t").longOpt("temp-folder").hasArg().desc("temporary folder to write temp files to").build());


        CommandLine line = parser.parse(options, args);

        if (!line.hasOption("i") || !line.hasOption("n") || !line.hasOption("t") || !line.hasOption("o")) {
            throw new IllegalArgumentException("All arguments need to be passed");
        }

        this.inputFile = new File(line.getOptionValue("i"));
        if (!inputFile.exists() || !inputFile.isFile()) {
            throw new IllegalArgumentException("Pass a valid input file");
        }

        this.outputFile = new File(line.getOptionValue("o"));

        this.noOfRowsToPresentInOutput = Integer.valueOf(line.getOptionValue("n"));
        if (noOfRowsToPresentInOutput <= 0) {
            throw new IllegalArgumentException("A positive number is expected");
        }

        this.temporaryFolder = new File(line.getOptionValue("t"));
        if (!this.temporaryFolder.exists()) {
            logger.debug("Creating temporary folder {} since it doesn't exist", this.temporaryFolder.getAbsolutePath());
            this.temporaryFolder.mkdir();
            this.temporaryFolder.deleteOnExit();
        }

    }

}
