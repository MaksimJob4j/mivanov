package ru.job4j.inout.zip;

import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.junit.Assert.*;

public class ZipTest {
    private final static String TMP_DIR = System.getProperty("java.io.tmpdir");
    private final static String PATH = TMP_DIR + "//TestFolder";
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
        dir21.mkdir();
        dir22.mkdir();

        try (FileOutputStream fos1 = new FileOutputStream(file1);
             FileOutputStream fos2 = new FileOutputStream(file2);
             FileOutputStream fos3 = new FileOutputStream(file3)) {
            for (int i = 0; i < 10000; i++) {
                fos1.write(i);
                fos2.write(new byte[]{(byte) i, (byte) i, (byte) i});
                fos3.write(new byte[]{(byte) i, (byte) i, (byte) i, (byte) i, (byte) i});
            }
        }
    }

    @Test
    public void zipTest() throws IOException {
        Zip zip = new Zip();

        zip.zipFiles(new String[]{"-d", PATH});
        assertTrue(new File("TestFolder.zip").exists());

        zip.zipFiles(new String[]{"-d", "wrong_PATH"});
        assertFalse(new File("wrong_PATH.zip").exists());

        zip.zipFiles(new String[]{"-d", PATH, "-o", TMP_DIR + "//test.zip"});
        assertTrue(new File(TMP_DIR + "//test.zip").exists());
        ZipInputStream zin = new ZipInputStream(new FileInputStream(TMP_DIR + "//test.zip"));
        ZipEntry entry;
        int fileCounter = 0;
        while ((entry = zin.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                fileCounter++;
            }
        }
        zin.close();
        assertEquals(fileCounter, 3);

        zip.zipFiles(new String[]{"-d", PATH, "-o", TMP_DIR + "//test.zip", "-e", "*.jpg"});
        assertTrue(new File(TMP_DIR + "//test.zip").exists());
        ZipInputStream zin2 = new ZipInputStream(new FileInputStream(TMP_DIR + "//test.zip"));
        ZipEntry entry2;
        int fileCounter2 = 0;
        while ((entry2 = zin2.getNextEntry()) != null) {
            if (!entry2.isDirectory()) {
                fileCounter2++;
                if (new File(entry2.getName()).getName().equals("FirstFile.txt")) {
                    FileInputStream firstInputStream = new FileInputStream(file1);
                    int i;
                    while ((i = zin2.read()) != -1) {
                        assertEquals(i, firstInputStream.read());
                    }
                    firstInputStream.close();
                }
                if (new File(entry2.getName()).getName().equals("ThirdFile.txt")) {
                    FileInputStream thirdInputStream = new FileInputStream(file3);
                    int i;
                    while ((i = zin2.read()) != -1) {
                        assertEquals(i, thirdInputStream.read());
                    }
                    thirdInputStream.close();
                }
                assertTrue(new File(entry2.getName()).getName().equals("FirstFile.txt")
                        || new File(entry2.getName()).getName().equals("ThirdFile.txt"));
                if (new File(entry2.getName()).getName().equals("ThirdFile.txt")) {
                    assertTrue(entry2.getName().contains("Level-1-1")
                            && entry2.getName().contains("Level-2-2"));
                }
                assertNotEquals("SecondFile.txt", new File(entry2.getName()).getName());
            }
        }
        zin2.close();
        assertEquals(fileCounter2, 2);

        zip.zipFiles(new String[]{"-d", PATH, "-o", TMP_DIR + "//test2.zip", "-e", "*.jpg", "-e", "*.txt"});
        assertFalse(new File(TMP_DIR + "//test2.zip").exists());
    }
}