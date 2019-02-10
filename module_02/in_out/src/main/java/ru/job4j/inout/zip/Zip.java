package ru.job4j.inout.zip;

import com.google.devtools.common.options.OptionsParser;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.*;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Zip {

    private final int maxBufferLength = 1024 * 1024 * 512;

    private Collection<File> collectFiles(String directoryName, List<String> exclude) {
        File directory = new File(directoryName);
        IOFileFilter filter;
        if (exclude != null && exclude.size() > 0) {
            filter = FileFilterUtils.notFileFilter(new WildcardFileFilter(exclude));
        } else {
            filter = FileFilterUtils.trueFileFilter();
        }
        return FileUtils.listFiles(directory, filter, TrueFileFilter.INSTANCE);
    }

    private void putFilesToZIP(String directoryName, List<File> files, String zipName) {
        File dirFile = new File(directoryName);
        int dirNameLength = new File(directoryName).getPath().length();
        if (zipName == null || zipName.length() == 0) {
            zipName = dirFile.getName() + ".zip";
        }
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipName))) {
            for (File f: files) {
                try (FileInputStream fis = new FileInputStream(f)) {
                    ZipEntry entry = new ZipEntry(f.getPath().substring(dirNameLength + 1));
                    zos.putNextEntry(entry);
                    int length;
                    while ((length = fis.available()) > 0) {
                        byte[] buffer = new byte[Math.min(length, this.maxBufferLength)];
                        fis.read(buffer);
                        zos.write(buffer);
                    }
                    zos.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void zipFiles(String[] args) {
        OptionsParser parser = OptionsParser.newOptionsParser(Args.class);
        parser.parseAndExitUponError(args);
        Args parsedArgs = parser.getOptions(Args.class);
        if (parsedArgs.directory.isEmpty()) {
            printUsage(parser);
            return;
        }
        File dir = new File(parsedArgs.directory);
        if (dir.isDirectory()) {
            List<File> files = (List<File>) collectFiles(parsedArgs.directory, parsedArgs.exclude);
            if (files.size() > 0) {
                putFilesToZIP(parsedArgs.directory, files, parsedArgs.output);
            } else {
                System.out.println("There are no files for zip with these parameters!");
            }
        } else {
            System.out.printf("'%s' is not a directory or does not exist !!!%n", parsedArgs.directory);
        }
    }

    private void printUsage(OptionsParser parser) {
        System.out.println("Usage: java -jar zip.jar OPTIONS");
        System.out.println(parser.describeOptions(Collections.emptyMap(),
                OptionsParser.HelpVerbosity.LONG));
    }

    public static void main(String[] args) {
        new Zip().zipFiles(args);
    }
}
