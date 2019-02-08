package ru.job4j.inout.search;

import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class SearchTest {

    private final static String PATH =  System.getProperty("java.io.tmpdir") + "//TestFolder";
    private static File dir1 = new File(PATH + "//Level-1-1");
    private static File dir2 = new File(PATH + "//Level-1-2");
    private static File file1 = new File(PATH + "//FirstFile.txt");
    private static File file2 = new File(PATH + "//SecondFile.jpg");
    private static File dir21 = new File(PATH + "//Level-1-1//Level-2-1");
    private static File dir22 = new File(PATH + "//Level-1-1//Level-2-2");
    private static File file3 = new File(PATH + "//Level-1-1//Level-2-2//ThirdFile.txt");

    @Before
    public void createFileSystem() throws IOException {
        new File(PATH).mkdir();
        dir1.mkdir();
        dir2.mkdir();
        file1.createNewFile();
        file2.createNewFile();
        dir21.mkdir();
        dir22.mkdir();
        file3.createNewFile();
    }

    @Test
    public void whenExtIsEmptyThenListIsEmpty() {
        Search search = new Search();
        assertThat(search.files(PATH, null).size(), is(0));
        assertThat(search.files(PATH, new ArrayList<>()).size(), is(0));
    }

    @Test
    public void whenThereAreNoFilesWithExtThenListIsEmpty() {
        Search search = new Search();
        assertThat(search.files(PATH, Arrays.asList("", "xml", "class")).size(), is(0));
    }

    @Test
    public void whenThereIsNoFolderInSystemThenListIsEmpty() {
        Search search = new Search();
        assertThat(search.files(PATH + "//wrongFolder", Arrays.asList("jpg", "txt")).size(), is(0));
    }

    @Test
    public void whenThereAreFilesWithExtThenFondIt() {
        Search search = new Search();
        assertThat(search.files(PATH, Arrays.asList("", "xml", "class", "txt")).size(), is(2));
        assertThat(search.files(PATH, Arrays.asList("", "xml", "class", "txt", "jpg")).size(), is(3));
        assertThat(search.files(PATH, Arrays.asList("", "xml", "class", "txt", "jpg")).size(), is(3));
        List<File> files = search.files(PATH, Arrays.asList("", "xml", "class", "txt"));
        assertTrue(files.contains(file1));
        assertFalse(files.contains(file2));
        assertTrue(files.contains(file3));
    }
}