package ru.job4j.inout;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.stream.Stream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class AnalizyTest {

    @Before
    public void createFile() throws IOException {
        File file = new File("test_status.log");
        if (!file.exists()) {
            file.createNewFile();
        }
        String ls = System.lineSeparator();
        String data = "200 10:56:01" + ls
                + "500 10:57:01" + ls
                + "400 10:58:01" + ls
                + "200 10:59:01" + ls
                + "500 11:01:02" + ls
                + "200 11:02:02" + ls;
        PrintWriter out = new PrintWriter(new FileOutputStream(file));
        out.print(data);
        out.flush();
        out.close();
    }

    @After
    public void deleteFile() {
        File in = new File("test_status.log");
        if (in.exists()) {
            in.delete();
        }
        File out = new File("test_unavailable.csv");
        if (out.exists()) {
            out.delete();
        }
    }

    @Test
    public void whenInvokeUnavailableThenWriteTarget() throws IOException {
        File unavailable = new File("test_unavailable.csv");
        if (unavailable.exists()) {
            unavailable.delete();
        }
        Analizy analizy = new Analizy();
        analizy.unavailable("test_status.log", "test_unavailable.csv");

        BufferedReader reader = new BufferedReader(new FileReader(unavailable));
        assertThat(reader.readLine(), is("10:57:01;10:59:01"));
        assertThat(reader.readLine(), is("11:01:02;11:02:02"));
        assertNull(reader.readLine());
        reader.close();

    }
}