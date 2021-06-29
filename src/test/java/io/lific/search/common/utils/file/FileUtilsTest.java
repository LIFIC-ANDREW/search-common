package io.lific.search.common.utils.file;

import io.lific.search.common.utils.test.TestClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;


public class FileUtilsTest extends TestClass {

    private static final File testFile = getResourceFile("local-files/local-file-test1.txt");
    private static final File writeTestFile = getResourceFile("local-files/write_test.txt");
    private static final String testDirPath = testFile.getParentFile().getAbsolutePath();
    private static final String testFilePath = testFile.getAbsolutePath();
    private static final String testFileName = testFile.getName();
    private static final URL testFileURL = toURL(testFile);

    @BeforeAll
    static void setUp() throws Exception {
        if (writeTestFile.exists()) {
            if (!writeTestFile.delete()) {
                throw new IOException("failed to delete file : " + writeTestFile.getAbsolutePath());
            }
        }
        if (!writeTestFile.createNewFile()) {
            throw new IOException("failed to create file : " + writeTestFile.getAbsolutePath());
        }
    }

    @Test
    public void test() throws IOException {
        String fileContent = "abcdefg\n1234567890";

        List<String> testFileContentLines = new ArrayList<>(2);
        testFileContentLines.add("abcdefg");
        testFileContentLines.add("1234567890");

        List<String> writeTestFileContentLines = new ArrayList<>(2);
        writeTestFileContentLines.add("abcdefg");
        writeTestFileContentLines.add("1234567890");
        writeTestFileContentLines.add("hijklmnop");
        writeTestFileContentLines.add("0987654321");

        List<String> appendTestFileContentLines = new ArrayList<>(2);
        appendTestFileContentLines.add("hijklmnop");
        appendTestFileContentLines.add("0987654321");

        assertEquals(
                fileContent
                , FileUtils.getFileContent(testFilePath)
        );
        assertEquals(
                fileContent
                , FileUtils.getFileContent(testFilePath, StandardCharsets.UTF_8)
        );
        assertEquals(
                fileContent
                , FileUtils.getFileContent(testDirPath, testFileName)
        );
        assertEquals(
                fileContent
                , FileUtils.getFileContent(testDirPath, testFileName, StandardCharsets.UTF_8)
        );
        assertEquals(
                fileContent
                , FileUtils.getFileContent(new FileReader(testFile))
        );
        assertEquals(
                testDirPath
                , FileUtils.getDirectoryPath(testFilePath)
        );
        assertEquals(
                testFileName
                , FileUtils.getFileName(testFilePath)
        );
        assertThat(
                FileUtils.makeLinesToList(testFilePath)
                , is(testFileContentLines)
        );
        assertThat(
                FileUtils.makeLinesToList(testFilePath, StandardCharsets.UTF_8)
                , is(testFileContentLines)
        );
        assertEquals(
                testFileURL
                , FileUtils.toURL(testFilePath)
        );
        FileUtils.appendTextLine(writeTestFile.getAbsolutePath(), "abcdefg");
        FileUtils.appendTextLine(writeTestFile.getAbsolutePath(), "1234567890");
        assertThat(
                FileUtils.makeLinesToList(writeTestFile.getAbsolutePath())
                , is(testFileContentLines)
        );
        FileUtils.writeList(writeTestFile.getAbsolutePath(), appendTestFileContentLines, true);
        assertThat(
                FileUtils.makeLinesToList(writeTestFile.getAbsolutePath())
                , is(writeTestFileContentLines)
        );
        FileUtils.writeList(writeTestFile.getAbsolutePath(), writeTestFileContentLines, false);
        assertThat(
                FileUtils.makeLinesToList(writeTestFile.getAbsolutePath())
                , is(writeTestFileContentLines)
        );
    }

    @Test
    public void createDirectory() throws IOException {
        Path path = Paths.get(getTargetDirectory().getAbsolutePath(), "a", "b", "c");
        FileUtils.createDirectory(getTargetDirectory().getAbsolutePath(), "a", "b", "c");
        assertTrue(Files.exists(path));
        path = Paths.get(getTargetDirectory().getAbsolutePath(), "a", "b", "c");
        Files.delete(path);
        path = Paths.get(getTargetDirectory().getAbsolutePath(), "a", "b");
        Files.delete(path);
        path = Paths.get(getTargetDirectory().getAbsolutePath(), "a");
        Files.delete(path);
    }

    @AfterAll
    static void tearDown() throws Exception {
        if (writeTestFile.exists()) {
            if (!writeTestFile.delete()) {
                throw new IOException("failed to delete file : " + writeTestFile.getAbsolutePath());
            }
        }
    }

}
