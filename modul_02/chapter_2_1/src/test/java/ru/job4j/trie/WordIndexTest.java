package ru.job4j.trie;

import org.junit.Test;

import java.io.*;
import java.util.Set;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class WordIndexTest {
    @Test
    public void wordIndexTest() throws Exception {
        String testString = "java jiva juva \n\r"
                + "jiva java\n\r"
                + "java";
        String fileName = "test.txt";
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF8"));
        writer.write(testString);
        writer.flush();
        writer.close();
        WordIndex wordIndex = new WordIndex();
        wordIndex.loadFile(fileName);
        Set<Integer> set = wordIndex.getIndexes4Word("java");
        int first = testString.indexOf("java", 0);
        assertThat(set.contains(first), is(true));
        int second = testString.indexOf("java", first + 1);
        assertThat(set.contains(second), is(true));
        int third = testString.indexOf("java", second + 1);
        assertThat(set.contains(third), is(true));
        assertThat(set.size(), is(3));
        assertThat(wordIndex.getIndexes4Word("jova") == null, is(true));
    }

    @Test
    public void wordIndexTest2() throws Exception {
        String testString = "мама мыла раму";
        assertThat(testString.indexOf("мыла"), is(5));
        String fileName = "test.txt";
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), "UTF8"));
        writer.write(testString);
        writer.flush();
        writer.close();
        WordIndex wordIndex = new WordIndex();
        wordIndex.loadFile(fileName);
        Set<Integer> set = wordIndex.getIndexes4Word("мыла");
        assertThat(set.contains(5), is(true));
        assertThat(set.size(), is(1));
    }
}