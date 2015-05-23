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


    private static Logger logger = LoggerFactory.getLogger(Sortomatic.class);


    private static final long MAX_CHUNK_SIZE = 20 * 1024 * 1024;


    private File inputFile;


    private File outputFile;


    private int noOfRowsToPresentInOutput;


    private File temporaryFolder;


    public static void main(String[] args) throws Exception {
        Sortomatic sortomatic = new Sortomatic();
        sortomatic.setupContextFromArgs(args);

        SortedFileSplitter sortedFileSplitter = new SortedFileSplitter(
                sortomatic.getTemporaryFolder(),
                new CollectionSorter(),
                MAX_CHUNK_SIZE
        );

        List<File> splitFiles = getSplitSortedFiles(sortomatic, sortedFileSplitter);

        sortomatic.mergeSortedContent(splitFiles);

        logger.debug("Generated output file can be found at {}", sortomatic.getOutputFile().getAbsolutePath());
    }


    private static List<File> getSplitSortedFiles(Sortomatic sortomatic, SortedFileSplitter sortedFileSplitter) throws IOException {
        FileInputStream fileInputStream = new FileInputStream(sortomatic.getInputFile());
        InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, Charset.forName("UTF-8"));
        BufferedReader inputBufferedReader = new BufferedReader(inputStreamReader);

        List<File> splitFiles = sortedFileSplitter.splitSort(inputBufferedReader);

        inputBufferedReader.close();
        return splitFiles;
    }


    private void mergeSortedContent(List<File> splitFiles) throws IOException {
        List<BufferedReader> bufferedReaders = new ArrayList<BufferedReader>();

        Charset defaultCharset = Charset.forName("UTF-8");
        for (File file : splitFiles) {
            bufferedReaders.add(new BufferedReader(new InputStreamReader(new FileInputStream(file), defaultCharset)));
        }

        OutputStreamWriter outputFileOutputSW = new OutputStreamWriter(new FileOutputStream(this.getOutputFile()), defaultCharset);
        BufferedWriter outputBW = new BufferedWriter(outputFileOutputSW);

        SortedContentMerger sortedContentMerger = new SortedContentMerger();
        sortedContentMerger.writeSortedStreamCollectionToWriter(bufferedReaders, outputBW, this.getNoOfRowsToPresentInOutput());
        outputBW.close();
    }


    protected void setupContextFromArgs(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        Options options = getCliOptions();
        CommandLine line = parser.parse(options, args);

        if (!line.hasOption("i") || !line.hasOption("n") || !line.hasOption("t") || !line.hasOption("o")) {
            throw new IllegalArgumentException("All arguments need to be passed");
        }

        setInputFile(line);
        setNoOfRowsToFindOut(line);
        setTemporaryFolder(line);
        this.outputFile = new File(line.getOptionValue("o"));

        Object[] logOpts = new Object[]{
                this.getInputFile(),
                this.getOutputFile(),
                this.getTemporaryFolder(),
                this.getNoOfRowsToPresentInOutput()
        };

        logger.debug("Sortomatic initialized with \n",
                " input file {} \n",
                " output file {} \n",
                " temp folder {} \n",
                " top N rows to be found {}",
                logOpts
        );
    }


    private Options getCliOptions() {
        Options options = new Options()
                .addOption(
                        Option.builder("i").longOpt("input-file").hasArg()
                                .desc("input file with full path").build()
                )
                .addOption(
                        Option.builder("o").longOpt("output-file").hasArg()
                                .desc("location where output file has to be generated").build()
                )
                .addOption(
                        Option.builder("n").longOpt("top-n-nos").hasArg()
                                .desc("top N numbers to be found").build()
                )
                .addOption(
                        Option.builder("t").longOpt("temp-folder").hasArg()
                                .desc("temporary folder to write temp files to").build()
                );
        return options;
    }


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


    private void setInputFile(CommandLine line) {
        this.inputFile = new File(line.getOptionValue("i"));
        if (!inputFile.exists() || !inputFile.isFile()) {
            throw new IllegalArgumentException("Pass a valid input file");
        }
    }


    private void setNoOfRowsToFindOut(CommandLine line) {
        this.noOfRowsToPresentInOutput = Integer.valueOf(line.getOptionValue("n"));
        if (noOfRowsToPresentInOutput <= 0) {
            throw new IllegalArgumentException("A positive number is expected");
        }
    }


    private void setTemporaryFolder(CommandLine line) {
        this.temporaryFolder = new File(line.getOptionValue("t"));
        if (!this.temporaryFolder.exists()) {
            logger.debug("Creating temporary folder {} since it doesn't exist", this.temporaryFolder.getAbsolutePath());
            this.temporaryFolder.mkdir();
            this.temporaryFolder.deleteOnExit();
        }
    }


}
