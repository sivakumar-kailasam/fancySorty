package com.sk.sortomatic;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.charset.Charset;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author Sivakumar Kailasam
 */
public class SortomaticTest {


    private Sortomatic sortomatic;


    private File systemTempDirectory = new File(System.getProperty("java.io.tmpdir"));


    @Before
    public void setUp() throws Exception {
        sortomatic = new Sortomatic();
    }


    @Test(expected = IllegalArgumentException.class)
    public void setupContextFromArgs_InputFileIsNotPassed_throwsException() throws Exception {
        sortomatic.setupContextFromArgs(new String[]{"-n=10", "-o=/output.txt", "-t=/tmp"});
    }


    @Test(expected = IllegalArgumentException.class)
    public void setupContextFromArgs_MaxNnoIsNotPassed_throwsException() throws Exception {
        sortomatic.setupContextFromArgs(new String[]{"-i=/something", "-o=/output.txt", "-t=/tmp"});
    }


    @Test(expected = IllegalArgumentException.class)
    public void setupContextFromArgs_TempFolderPathIsNotPassed_throwsException() throws Exception {
        sortomatic.setupContextFromArgs(new String[]{"-i=/something", "-o=/output.txt", "-n=1"});
    }


    @Test(expected = IllegalArgumentException.class)
    public void setupContextFromArgs_InputFileIsNonExistent_throwsException() throws Exception {
        sortomatic.setupContextFromArgs(new String[]{"-i=/something.txt", "-o=/output.txt", "-n=10", "-t=/tmp"});
    }

    @Test(expected = IllegalArgumentException.class)
    public void setupContextFromArgs_OutputFileIsNotPassed_throwsException() throws Exception {
        sortomatic.setupContextFromArgs(new String[]{"-i=/something.txt", "-n=10", "-t=/tmp"});
    }


    @Test(expected = IllegalArgumentException.class)
    public void setupContextFromArgs_InputFileIsAFolder_throwsException() throws Exception {
        sortomatic.setupContextFromArgs(new String[]{
                "-i=" + systemTempDirectory.getAbsolutePath(),
                "-o=/output.txt",
                "-n=10",
                "-t=/tmp"
        });
    }


    @Test(expected = IllegalArgumentException.class)
    public void setupContextFromArgs_TopNRowCountIsZero_throwsException() throws Exception {
        File tempInputFile = generateEmptyInputFile();
        sortomatic.setupContextFromArgs(new String[]{
                "-i=" + tempInputFile.getAbsolutePath(),
                "-o=/output.txt",
                "-n=0",
                "-t=/tmp"
        });
    }


    @Test(expected = IllegalArgumentException.class)
    public void setupContextFromArgs_TopNRowCountIsNegNo_throwsException() throws Exception {
        File tempInputFile = generateEmptyInputFile();
        sortomatic.setupContextFromArgs(new String[]{
                "-i=" + tempInputFile.getAbsolutePath(),
                "-o=/output.txt",
                "-n=-1",
                "-t=/tmp"
        });
    }


    @Test
    public void setupContextFromArgs_TempFolderNonExistent_CreatesIt() throws Exception {

        File tempInputFile = generateEmptyInputFile();

        File testFolder = new File(systemTempDirectory, "kumar");
        String testFolderPath = testFolder.getAbsolutePath();
        if (testFolder.exists()) {
            testFolder.delete();
        }
        sortomatic.setupContextFromArgs(new String[]{
                "-i=" + tempInputFile.getAbsolutePath(),
                "-n=10",
                "-o=/output.txt",
                "-t=" + testFolderPath
        });
        assertThat(testFolder).exists();
        testFolder.delete();

    }


    private File generateEmptyInputFile() throws IOException {
        File tempInputFile = new File(systemTempDirectory.getAbsolutePath(), "siva.txt");
        tempInputFile.deleteOnExit();
        tempInputFile.createNewFile();
        return tempInputFile;
    }


    /**
     * This test is more of an integration scenario
     */
    @Test
    public void main_ValidContext_CreatesSortedOutput() throws Exception {
        File tempInputFile = generateEmptyInputFile();
        Charset charset = Charset.forName("UTF-8");
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(tempInputFile), charset));
        for (Long i = 0l; i <= 100; i++) {
            bw.write(i.toString());
            bw.newLine();
        }
        bw.close();

        File outputFile = new File(systemTempDirectory.getAbsolutePath(), "output.txt");
        outputFile.deleteOnExit();

        Sortomatic.main(new String[]{
                "-i" + tempInputFile.getAbsolutePath(),
                "-n=5",
                "-o=" + outputFile.getAbsolutePath(),
                "-t=" + systemTempDirectory.getAbsolutePath()
        });

        BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(outputFile), charset));

        String outputContent = "";

        String line = "";
        while (line != null && ((line = reader.readLine()) != null)) {
            outputContent += line + "\n";
        }

        assertThat(outputContent).isEqualTo("100\n99\n98\n97\n96\n");
    }


}
